package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static gregtech.api.util.GT_OreDictUnificator.getAssociation;
import static gregtech.api.util.GT_ParallelHelper.addFluidsLong;
import static gregtech.api.util.GT_ParallelHelper.addItemsLong;
import static gregtech.api.util.GT_RecipeBuilder.BUCKETS;
import static gregtech.api.util.GT_RecipeBuilder.INGOTS;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.getUserEU;
import static net.minecraft.util.EnumChatFormatting.*;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_ForgeOfGods;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.*;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;

import gregtech.api.enums.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.objects.ItemData;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.*;

public class GT_MetaTileEntity_EM_MoltenModule extends GT_MetaTileEntity_EM_BaseModule {

    Parameters.Group.ParameterIn parallelParam;
    Parameters.Group.ParameterIn batchParam;
    private int solenoidCoilMetadata = 7;
    private long EUt = 0;
    private int currentParallel = 0;

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

    long wirelessEUt = 0;

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            private FluidStack[] meltableItems;

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GT_Recipe recipe) {
                maxParallel = (int) parallelParam.get();
                wirelessEUt = (long) recipe.mEUt * maxParallel;
                if (getUserEU(userUUID).compareTo(BigInteger.valueOf(wirelessEUt * recipe.mDuration)) < 0) {
                    return CheckRecipeResultRegistry.insufficientPower(wirelessEUt * recipe.mDuration);
                }

                meltableItems = new FluidStack[recipe.mOutputs.length];
                for (int i = 0; i < recipe.mOutputs.length; i++) {
                    ItemStack item = recipe.getOutput(i);
                    if (item == null) {
                        continue;
                    }
                    ItemData data = getAssociation(item);
                    Materials mat = data == null ? null : data.mMaterial.mMaterial;
                    if (mat != null) {
                        if (mat.mStandardMoltenFluid != null) {
                            meltableItems[i] = mat.getMolten(INGOTS);
                        } else if (mat.mFluid != null) {
                            meltableItems[i] = mat.getFluid(BUCKETS);
                        }
                    }
                }

                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Nonnull
            @Override
            protected GT_OverclockCalculator createOverclockCalculator(@Nonnull GT_Recipe recipe) {
                return super.createOverclockCalculator(recipe).setEUt(TierEU.MAX).setRecipeHeat(recipe.mSpecialValue)
                        .setHeatOC(true).setHeatDiscount(true)
                        .setMachineHeat(13501 + 100 * (GT_Utility.getTier(TierEU.MAX) - 2));

            }

            @NotNull
            @Override
            protected CheckRecipeResult onRecipeStart(@Nonnull GT_Recipe recipe) {
                if (!addEUToGlobalEnergyMap(userUUID, -calculatedEut * duration)) {
                    return CheckRecipeResultRegistry.insufficientPower(calculatedEut * duration);
                }
                currentParallel = calculatedParallels;
                EUt = calculatedEut;
                setCalculatedEut(0);
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Nonnull
            @Override
            protected GT_ParallelHelper createParallelHelper(@Nonnull GT_Recipe recipe) {
                return super.createParallelHelper(recipe).setCustomItemOutputCalculation(currentParallel -> {
                    ArrayList<ItemStack> outputItems = new ArrayList<>();
                    for (int i = 0; i < recipe.mOutputs.length; i++) {
                        ItemStack item = recipe.getOutput(i);
                        if (item == null || meltableItems[i] != null) {
                            continue;
                        }
                        ItemStack itemToAdd = item.copy();
                        addItemsLong(outputItems, itemToAdd, (long) item.stackSize * currentParallel);
                    }
                    return outputItems.toArray(new ItemStack[0]);
                }).setCustomFluidOutputCalculation(currentParallel -> {
                    ArrayList<FluidStack> fluids = new ArrayList<>();

                    for (int i = 0; i < recipe.mOutputs.length; i++) {
                        FluidStack fluid = meltableItems[i];
                        if (fluid == null) {
                            continue;
                        }
                        FluidStack fluidToAdd = fluid.copy();
                        long fluidAmount = (long) fluidToAdd.amount * recipe.mOutputs[i].stackSize * currentParallel;
                        addFluidsLong(fluids, fluidToAdd, fluidAmount);
                    }

                    for (int i = 0; i < recipe.mFluidOutputs.length; i++) {
                        FluidStack fluid = recipe.getFluidOutput(i);
                        if (fluid == null) {
                            continue;
                        }
                        FluidStack fluidToAdd = fluid.copy();
                        addFluidsLong(fluids, fluidToAdd, (long) fluidToAdd.amount * currentParallel);
                    }
                    return fluids.toArray(new FluidStack[0]);
                });
            }
        };
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        logic.setAvailableVoltage(Long.MAX_VALUE);
        logic.setAvailableAmperage(Integer.MAX_VALUE);
        logic.setAmperageOC(false);
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
        batchParam = param_4.makeInParameter(1, 1, BATCH_PARAM_NAME, BATCH_SIZE);
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
            .fromLimitsInclusiveOuterBoundary(p.get(), 1, 0, 128, 128);

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
        str.add("Currently using: " + RED + formatNumbers(EUt) + RESET + " EU/t");
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
