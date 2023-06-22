package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.YELLOW;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_ForgeOfGods;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.*;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;

import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.IGlobalWirelessEnergy;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.util.*;

public class GT_MetaTileEntity_EM_SmeltingModule extends GT_MetaTileEntity_EM_BaseModule
        implements IGlobalWirelessEnergy {

    private int solenoidCoilMetadata = 7;
    private static long EUt = 0;
    private static long currentParallel = 0;
    Parameters.Group.ParameterIn parallelParam;

    public GT_MetaTileEntity_EM_SmeltingModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_SmeltingModule(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_SmeltingModule(mName) {};
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

        helper.build();

        if (helper.getCurrentParallel() == 0) {
            return false;
        }

        currentParallel = helper.getCurrentParallel();

        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;

        GT_OverclockCalculator calculator = new GT_OverclockCalculator().setRecipeEUt(tRecipe.mEUt).setEUt(tVoltage)
                .setDuration(tRecipe.mDuration).setAmperage(helper.getCurrentParallel())
                .setParallel(helper.getCurrentParallel()).calculate();

        EUt = -calculator.getConsumption();
        mMaxProgresstime = (int) Math.ceil(calculator.getDuration() * helper.getDurationMultiplier());

        if (!addEUToGlobalEnergyMap(userUUID, EUt * mMaxProgresstime)) {
            stopMachine();
            return false;
        }

        mOutputItems = helper.getItemOutputs();
        mOutputFluids = helper.getFluidOutputs();
        return true;
    }

    @Override
    protected void parametersInstantiation_EM() {
        super.parametersInstantiation_EM();
        Parameters.Group param_0 = parametrization.getGroup(0, false);
        parallelParam = param_0.makeInParameter(
                0,
                GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels(),
                PARALLEL_PARAM_NAME,
                PARALLEL_AMOUNT);
    }

    private static final INameFunction<GT_MetaTileEntity_EM_SmeltingModule> PARALLEL_PARAM_NAME = (base,
            p) -> translateToLocal("gt.blockmachines.multimachine.FOG.parallel");
    private static final IStatusFunction<GT_MetaTileEntity_EM_SmeltingModule> PARALLEL_AMOUNT = (base, p) -> LedStatus
            .fromLimitsInclusiveOuterBoundary(
                    p.get(),
                    1,
                    0,
                    GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels(),
                    GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels());

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
        tt.addMachineType("Smelting Module") // Machine Type:
                .addInfo("Controller block of the Smelting Module") // Controller
                .addInfo("Uses a Star to Smelt Metals").addSeparator().beginStructureBlock(1, 4, 2, false)
                .addEnergyHatch("Any Infinite Spacetime Casing", 1) // Energy Hatch: Any
                .addMaintenanceHatch("Any Infinite Spacetime Casing", 1) // Maintenance
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

}
