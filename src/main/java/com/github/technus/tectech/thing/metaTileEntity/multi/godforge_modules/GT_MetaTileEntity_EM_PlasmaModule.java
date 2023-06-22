package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static gregtech.api.util.GT_OreDictUnificator.getAssociation;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.Parameters;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.IGlobalWirelessEnergy;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.ItemData;
import gregtech.api.util.*;

public class GT_MetaTileEntity_EM_PlasmaModule extends GT_MetaTileEntity_EM_BaseModule
        implements IGlobalWirelessEnergy {

    Parameters.Group.ParameterIn[] parallelParameter;
    private int solenoidCoilMetadata = -1;
    private int currentParallel = 0;

    public GT_MetaTileEntity_EM_PlasmaModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_BaseModule(mName) {};
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
        this.mMaxProgresstime = 0;
        this.mOutputItems = new ItemStack[] {};
        this.mOutputFluids = new FluidStack[] {};
        int maxParallel = (int) parallelParameter[0].get();

        long tVoltage = TierEU.MAX * (long) Math.pow(4, (solenoidCoilMetadata - 7));
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
        long tEnergy = maxParallel * tVoltage;

        GT_Recipe tRecipe = this.getRecipeMap().findRecipe(
                getBaseMetaTileEntity(),
                false,
                gregtech.api.enums.GT_Values.V[tTier],
                aFluidInputs,
                aItemInputs);

        GT_ParallelHelper helper = new GT_ParallelHelper().setRecipe(tRecipe).setItemInputs(aItemInputs)
                .setFluidInputs(aFluidInputs).setAvailableEUt(tEnergy).setMaxParallel(maxParallel).enableConsumption()
                .enableOutputCalculation();

        helper.build();

        if (helper.getCurrentParallel() == 0) {
            return false;
        }

        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        GT_OverclockCalculator calculator = new GT_OverclockCalculator().setRecipeEUt(tRecipe.mEUt).setEUt(tVoltage)
                .setDuration(tRecipe.mDuration).setAmperage(helper.getCurrentParallel())
                .setParallel(helper.getCurrentParallel()).calculate();

        long EUt = -calculator.getConsumption();
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
            FluidStack foundPlasma = tryConvertItemStackToFluidMaterial(tRecipe.getOutput(i));
            ItemData data = getAssociation(tRecipe.getOutput(i));
            Materials mat = data == null ? null : data.mMaterial.mMaterial;
            if (i < tRecipe.mOutputs.length) {
                if (foundPlasma != null) {
                    FluidOutputs.add(foundPlasma);
                } else if (mat.getPlasma(0) != null) {
                    FluidOutputs.add(mat.getPlasma(tRecipe.getOutput(i).stackSize * 144L * currentParallel));
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
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Plasma Module") // Machine Type:
                .addInfo("Controller block of the Plasma Module") // Controller
                .addInfo("Uses a Star to to turn Metals into Plasma").addSeparator().beginStructureBlock(1, 4, 2, false)
                .addEnergyHatch("Any Infinite Spacetime Casing", 1) // Energy Hatch: Any
                .addMaintenanceHatch("Any Infinite Spacetime Casing", 1) // Maintenance
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

}
