package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static gregtech.api.util.GT_OreDictUnificator.getAssociation;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static net.minecraft.util.EnumChatFormatting.*;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_ForgeOfGods;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.*;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;

import gregtech.api.enums.*;
import gregtech.api.interfaces.IGlobalWirelessEnergy;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.ItemData;
import gregtech.api.util.*;

public class GT_MetaTileEntity_EM_MoltenModule extends GT_MetaTileEntity_EM_BaseModule
        implements IGlobalWirelessEnergy {

    Parameters.Group.ParameterIn parallelParam;
    Parameters.Group.ParameterIn batchParam;
    private int solenoidCoilMetadata = 7;
    private static long EUt = 0;
    private static int currentParallel = 0;

    public GT_MetaTileEntity_EM_MoltenModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_MoltenModule(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_MoltenModule(mName);
    }

    @Override
    public boolean checkRecipe_EM(ItemStack aStack) {
        ItemStack[] tInputs;
        FluidStack[] tFluids = this.getStoredFluids().toArray(new FluidStack[0]);

        if (inputSeparation) {
            ArrayList<ItemStack> tInputList = new ArrayList<>();
            for (GT_MetaTileEntity_Hatch_InputBus tHatch : mInputBusses) {
                IGregTechTileEntity tInputBus = tHatch.getBaseMetaTileEntity();
                for (int i = tInputBus.getSizeInventory() - 1; i >= 0; i--) {
                    if (tInputBus.getStackInSlot(i) != null) tInputList.add(tInputBus.getStackInSlot(i));
                }
                tInputs = tInputList.toArray(new ItemStack[0]);

                if (processRecipe(tInputs, tFluids)) return true;
                else tInputList.clear();
            }
        } else {
            tInputs = getStoredInputs().toArray(new ItemStack[0]);
            return processRecipe(tInputs, tFluids);
        }
        return false;
    }

    public boolean processRecipe(ItemStack[] aItemInputs, FluidStack[] aFluidInputs) {
        // Reset outputs and progress stats
        this.lEUt = 0;
        EUt = 0;
        currentParallel = 0;
        this.mMaxProgresstime = 0;
        this.mOutputItems = new ItemStack[] {};
        this.mOutputFluids = new FluidStack[] {};
        int maxParallel = (int) parallelParam.get();

        long tVoltage = TierEU.MAX * (long) Math.pow(4, (solenoidCoilMetadata - 7));
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
        long tEnergy = maxParallel * tVoltage;

        GT_Recipe tRecipe = this.getRecipeMap().findRecipe(
                getBaseMetaTileEntity(),
                false,
                gregtech.api.enums.GT_Values.V[tTier],
                aFluidInputs,
                aItemInputs);

        if (tRecipe == null) {
            return false;
        }

        GT_ParallelHelper helper = new GT_ParallelHelper().setRecipe(tRecipe).setItemInputs(aItemInputs)
                .setFluidInputs(aFluidInputs).setAvailableEUt(tEnergy).setMaxParallel(maxParallel).enableConsumption()
                .enableOutputCalculation();

        helper.enableBatchMode((int) batchParam.get());

        helper.build();

        if (helper.getCurrentParallel() == 0) {
            return false;
        }

        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        GT_OverclockCalculator calculator = new GT_OverclockCalculator().setRecipeEUt(tRecipe.mEUt).setEUt(tVoltage)
                .setDuration(tRecipe.mDuration).setAmperage(helper.getCurrentParallel())
                .setParallel(helper.getCurrentParallel()).calculate();

        EUt = (long) (-calculator.getConsumption() / helper.getDurationMultiplier());
        mMaxProgresstime = (int) Math.ceil(calculator.getDuration() * helper.getDurationMultiplier());

        if (!addEUToGlobalEnergyMap(userUUID, EUt * mMaxProgresstime)) {
            stopMachine();
            return false;
        }

        mOutputItems = helper.getItemOutputs();
        mOutputFluids = helper.getFluidOutputs();

        ArrayList<ItemStack> ItemOutputs = new ArrayList<>();
        ArrayList<FluidStack> FluidOutputs = new ArrayList<>();
        currentParallel = helper.getCurrentParallel();
        for (int i = 0; i < mOutputItems.length; i++) {
            FluidStack foundFluid = tryConvertItemStackToFluidMaterial(tRecipe.getOutput(i));
            ItemData data = getAssociation(tRecipe.getOutput(i));
            Materials mat = data == null ? null : data.mMaterial.mMaterial;
            if (i < tRecipe.mOutputs.length) {
                if (foundFluid != null) {
                    FluidOutputs.add(foundFluid);
                } else if (mat.getMolten(0) != null) {
                    FluidOutputs.add(mat.getMolten(tRecipe.getOutput(i).stackSize * 144L * currentParallel));
                } else if (mat.getFluid(0) != null) {
                    FluidOutputs.add(mat.getFluid(tRecipe.getOutput(i).stackSize * 1000L * currentParallel));
                } else {
                    ItemStack aItem = tRecipe.getOutput(i);
                    ItemOutputs.add(GT_Utility.copyAmountUnsafe((long) aItem.stackSize * currentParallel, aItem));
                }
                for (int j = 0; j < mOutputFluids.length; j++) {
                    FluidOutputs.add(tRecipe.getFluidOutput(j));
                }
            } else {
                FluidStack aFluid = tRecipe.getFluidOutput(i - tRecipe.mOutputs.length);
                FluidOutputs.add(new FluidStack(aFluid, aFluid.amount * currentParallel));
            }
        }
        mOutputItems = ItemOutputs.toArray(new ItemStack[0]);
        mOutputFluids = FluidOutputs.toArray(new FluidStack[0]);
        updateSlots();
        return true;
    }

    @Override
    protected void parametersInstantiation_EM() {
        super.parametersInstantiation_EM();
        Parameters.Group param_1 = parametrization.getGroup(0, false);
        parallelParam = param_1.makeInParameter(
                0,
                GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels(),
                PARALLEL_PARAM_NAME,
                PARALLEL_AMOUNT);
        Parameters.Group param_4 = parametrization.getGroup(0, false);
        batchParam = param_4.makeInParameter(
                1,
                1,
                BATCH_PARAM_NAME,
                BATCH_SIZE);
    }

    private static final INameFunction<GT_MetaTileEntity_EM_MoltenModule> PARALLEL_PARAM_NAME = (base,
            p) -> translateToLocal("gt.blockmachines.multimachine.FOG.parallel");
    private static final IStatusFunction<GT_MetaTileEntity_EM_MoltenModule> PARALLEL_AMOUNT = (base, p) -> LedStatus
            .fromLimitsInclusiveOuterBoundary(
                    p.get(),
                    1,
                    0,
                    GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels(),
                    GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels());
    private static final INameFunction<GT_MetaTileEntity_EM_MoltenModule> BATCH_PARAM_NAME = (base,
                                                                                                 p) -> translateToLocal("gt.blockmachines.multimachine.FOG.batch");
    private static final IStatusFunction<GT_MetaTileEntity_EM_MoltenModule> BATCH_SIZE = (base, p) -> LedStatus
            .fromLimitsInclusiveOuterBoundary(
                    p.get(),
                    1,
                    0,
                    128,
                    128);

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(texturePage << 7),
                    new TT_RenderedExtendedFacingTexture(
                            aActive ? GT_MetaTileEntity_MultiblockBase_EM.ScreenON
                                    : GT_MetaTileEntity_MultiblockBase_EM.ScreenOFF) };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(texturePage << 7) };
    }

    @Nullable
    public static FluidStack tryConvertItemStackToFluidMaterial(ItemStack input) {
        ArrayList<String> oreDicts = new ArrayList<>();
        for (int id : OreDictionary.getOreIDs(input)) {
            oreDicts.add(OreDictionary.getOreName(id));
        }

        for (String dict : oreDicts) {
            OrePrefixes orePrefix;
            try {
                orePrefix = OrePrefixes.valueOf(findBestPrefix(dict));
            } catch (Exception e) {
                continue;
            }

            String strippedOreDict = dict.substring(orePrefix.toString().length());

            // Prevents things like AnyCopper or AnyIron from messing the search up.
            if (strippedOreDict.contains("Any")) continue;
        }
        return null;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> str = new ArrayList<>();
        str.add(
                "Progress: " + GREEN
                        + GT_Utility.formatNumbers(mProgresstime / 20)
                        + RESET
                        + " s / "
                        + YELLOW
                        + GT_Utility.formatNumbers(mMaxProgresstime / 20)
                        + RESET
                        + " s");
        str.add("Currently using: " + RED + formatNumbers(-EUt) + RESET + " EU/t");
        str.add(YELLOW + "Max Parallel: " + RESET + formatNumbers(GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels()));
        str.add(YELLOW + "Current Parallel: " + RESET + formatNumbers(currentParallel));
        return str.toArray(new String[0]);
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Molten Module") // Machine Type:
                .addInfo("Controller block of the Molten Module") // Controller
                .addInfo("Uses a Star to to melt Metals").addSeparator().beginStructureBlock(1, 4, 2, false)
                .addEnergyHatch("Any Infinite Spacetime Casing", 1) // Energy Hatch: Any
                .addMaintenanceHatch("Any Infinite Spacetime Casing", 1) // Maintenance
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

}
