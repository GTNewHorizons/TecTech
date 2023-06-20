package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.Parameters;
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

public class GT_MetaTileEntity_EM_quark_gluon_plasma extends GT_MetaTileEntity_EM_BaseModule
        implements IGlobalWirelessEnergy {

    Parameters.Group.ParameterIn[] parallelParameter;
    private int solenoidCoilMetadata = -1;

    public GT_MetaTileEntity_EM_quark_gluon_plasma(int aID, String aName, String aNameRegional) {
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

    private boolean processRecipe(ItemStack[] aItemInputs, FluidStack[] aFluidInputs) {
        // Reset outputs and progress stats
        this.lEUt = 0;
        this.mMaxProgresstime = 0;
        this.mOutputItems = new ItemStack[] {};
        this.mOutputFluids = new FluidStack[] {};
        int maxParallel = (int) parallelParameter[0].get();

        long tVoltage = TierEU.MAX * (long) Math.pow(4, (solenoidCoilMetadata - 7));
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
        long tEnergy = maxParallel * tVoltage;

        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sBlastRecipes.findRecipe(
                getBaseMetaTileEntity(),
                false,
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

        GT_OverclockCalculator calculator = new GT_OverclockCalculator().setRecipeEUt(tRecipe.mEUt)
                .setEUt(gregtech.api.enums.GT_Values.V[tTier] * helper.getCurrentParallel())
                .setDuration(tRecipe.mDuration).setParallel((int) Math.floor(helper.getCurrentParallel())).calculate();

        lEUt = -calculator.getConsumption();
        mMaxProgresstime = (int) Math.ceil(calculator.getDuration() * helper.getDurationMultiplier());
        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        mOutputItems = helper.getItemOutputs();
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
        tt.addMachineType("Quark Gluon Plasma Module") // Machine Type:
                .addInfo("Controller block of the Quark Gluon Plasma Module") // Controller
                .addInfo("Uses a Star to to turn Items into Quark Gluon Plasma").addSeparator()
                .beginStructureBlock(1, 4, 2, false).addEnergyHatch("Any Infinite Spacetime Casing", 1) // Energy Hatch:
                                                                                                        // Any
                .addMaintenanceHatch("Any Infinite Spacetime Casing", 1) // Maintenance
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

}
