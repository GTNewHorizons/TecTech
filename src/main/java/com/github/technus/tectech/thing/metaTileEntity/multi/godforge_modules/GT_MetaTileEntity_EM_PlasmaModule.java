package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.recipe.TecTechRecipeMaps.godforgePlasmaRecipes;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.getUserEU;
import static net.minecraft.util.EnumChatFormatting.*;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.minecraftforge.common.util.ForgeDirection;

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
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.*;

public class GT_MetaTileEntity_EM_PlasmaModule extends GT_MetaTileEntity_EM_BaseModule {

    Parameters.Group.ParameterIn parallelParam;
    private int solenoidCoilMetadata = 7;
    private long EUt = 0;
    private int currentParallel = 0;

    public GT_MetaTileEntity_EM_PlasmaModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_PlasmaModule(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_PlasmaModule(mName);
    }

    long wirelessEUt = 0;

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GT_Recipe recipe) {
                maxParallel = (int) parallelParam.get();
                wirelessEUt = (long) recipe.mEUt * maxParallel;
                if (getUserEU(userUUID).compareTo(BigInteger.valueOf(wirelessEUt * recipe.mDuration)) < 0) {
                    return CheckRecipeResultRegistry.insufficientPower(wirelessEUt * recipe.mDuration);
                }
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @NotNull
            @Override
            protected CheckRecipeResult onRecipeStart(@Nonnull GT_Recipe recipe) {
                wirelessEUt = (long) recipe.mEUt * maxParallel;
                if (!addEUToGlobalEnergyMap(userUUID, -calculatedEut * duration)) {
                    return CheckRecipeResultRegistry.insufficientPower(wirelessEUt * recipe.mDuration);
                }
                currentParallel = calculatedParallels;
                EUt = calculatedEut;
                setCalculatedEut(0);
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Nonnull
            @Override
            protected GT_OverclockCalculator createOverclockCalculator(@Nonnull GT_Recipe recipe) {
                return GT_OverclockCalculator.ofNoOverclock(recipe);
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
        Parameters.Group param_3 = parametrization.getGroup(0, false);
        parallelParam = param_3.makeInParameter(
                0,
                GT_MetaTileEntity_EM_ForgeOfGods.getMaxParallels(),
                PARALLEL_PARAM_NAME,
                PARALLEL_AMOUNT);
    }

    private static final INameFunction<GT_MetaTileEntity_EM_PlasmaModule> PARALLEL_PARAM_NAME = (base,
            p) -> translateToLocal("gt.blockmachines.multimachine.FOG.parallel");
    private static final IStatusFunction<GT_MetaTileEntity_EM_PlasmaModule> PARALLEL_AMOUNT = (base, p) -> LedStatus
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
    public RecipeMap<?> getRecipeMap() {
        return godforgePlasmaRecipes;
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
        tt.addMachineType("Plasma Module") // Machine Type:
                .addInfo("Controller block of the Plasma Module") // Controller
                .addInfo("Uses a Star to to turn Metals into Plasma").addSeparator().beginStructureBlock(1, 4, 2, false)
                .addEnergyHatch("Any Infinite Spacetime Casing", 1) // Energy Hatch: Any
                .addMaintenanceHatch("Any Infinite Spacetime Casing", 1) // Maintenance
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

}