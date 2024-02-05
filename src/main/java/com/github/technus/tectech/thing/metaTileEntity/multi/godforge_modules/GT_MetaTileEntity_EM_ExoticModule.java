package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.loader.recipe.Godforge.exoticModulePlasmaFluidMap;
import static com.github.technus.tectech.loader.recipe.Godforge.exoticModulePlasmaItemMap;
import static com.github.technus.tectech.recipe.TecTechRecipeMaps.godforgeExoticMatterRecipes;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.util.TT_Utility.getRandomIntInRange;
import static gregtech.api.util.GT_OreDictUnificator.getAssociation;
import static gregtech.api.util.GT_RecipeBuilder.INGOTS;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.getUserEU;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;

import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.objects.ItemData;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import gregtech.api.util.*;

public class GT_MetaTileEntity_EM_ExoticModule extends GT_MetaTileEntity_EM_BaseModule {

    private int solenoidCoilMetadata = -1;
    private int numberOfFluids = 0;
    private int numberOfItems = 0;
    private int inputAmount = 0;
    private int currentParallel = 0;
    private long wirelessEUt = 0;
    private long EUt = 0;
    private boolean recipeInProgress = false;
    private FluidStack[] randomizedFluidInput = new FluidStack[] {};
    private ItemStack[] randomizedItemInput = new ItemStack[] {};
    List<FluidStack> inputPlasmas = new ArrayList<>();
    private GT_Recipe plasmaRecipe = null;
    private static RecipeMap<RecipeMapBackend> tempRecipeMap = null;
    private static final RecipeMap<RecipeMapBackend> emptyRecipeMap = RecipeMapBuilder.of("hey").maxIO(0, 0, 7, 2)
            .build();
    private static final int NUMBER_OF_INPUTS = 7;

    public GT_MetaTileEntity_EM_ExoticModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_ExoticModule(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_ExoticModule(mName);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected Stream<GT_Recipe> findRecipeMatches(@Nullable RecipeMap<?> map) {
                if (!recipeInProgress) {
                    tempRecipeMap = emptyRecipeMap;
                    numberOfFluids = getRandomIntInRange(0, NUMBER_OF_INPUTS);
                    numberOfItems = NUMBER_OF_INPUTS - numberOfFluids;
                    randomizedFluidInput = getRandomFluidInputs(numberOfFluids);
                    randomizedItemInput = getRandomItemInputs(numberOfItems);
                    inputPlasmas = new ArrayList<>(Arrays.asList(convertToPlasma(randomizedItemInput, 1)));
                    inputPlasmas.addAll(Arrays.asList(randomizedFluidInput));

                    if (numberOfFluids != 0) {
                        for (FluidStack fluidStack : randomizedFluidInput) {
                            fluidStack.amount = 1000;
                        }
                    }

                    plasmaRecipe = new GT_Recipe(
                            false,
                            null,
                            null,
                            null,
                            null,
                            inputPlasmas.toArray(new FluidStack[0]),
                            new FluidStack[] { MaterialsUEVplus.PrimordialMatter.getFluid(1000) },
                            10 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            0);

                    tempRecipeMap.add(plasmaRecipe);
                }
                return tempRecipeMap.getAllRecipes().parallelStream();
            }

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GT_Recipe recipe) {
                if (!recipeInProgress) {
                    maxParallel = 1;
                    wirelessEUt = (long) recipe.mEUt * maxParallel;
                    if (getUserEU(userUUID).compareTo(BigInteger.valueOf(wirelessEUt * recipe.mDuration)) < 0) {
                        tempRecipeMap = emptyRecipeMap;
                        return CheckRecipeResultRegistry.insufficientPower(wirelessEUt * recipe.mDuration);
                    }

                    if (numberOfFluids != 0) {
                        for (FluidStack fluidStack : randomizedFluidInput) {
                            dumpFluid(mOutputHatches, new FluidStack(fluidStack.getFluid(), 1), false);
                        }
                    }

                    if (numberOfItems != 0) {
                        for (ItemStack itemStack : randomizedItemInput) {
                            addOutput(itemStack);
                        }
                    }

                    recipeInProgress = true;
                }
                if (getFluidsStored().containsAll(inputPlasmas)) {
                    return CheckRecipeResultRegistry.SUCCESSFUL;
                }
                return SimpleCheckRecipeResult.ofFailure("waiting_for_inputs");
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
                tempRecipeMap = emptyRecipeMap;
                recipeInProgress = false;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Nonnull
            @Override
            protected GT_OverclockCalculator createOverclockCalculator(@Nonnull GT_Recipe recipe) {
                return super.createOverclockCalculator(recipe).setEUt(TierEU.MAX);
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
        return godforgeExoticMatterRecipes;
    }

    private FluidStack[] getRandomFluidInputs(int numberOfFluids) {
        int cumulativeWeight = 0;

        List<Map.Entry<FluidStack, Integer>> fluidEntryList = new ArrayList<>(exoticModulePlasmaFluidMap.entrySet());

        List<Integer> cumulativeWeights = new ArrayList<>();
        for (Map.Entry<FluidStack, Integer> entry : fluidEntryList) {
            cumulativeWeight += entry.getValue();
            cumulativeWeights.add(cumulativeWeight);
        }

        List<FluidStack> pickedFluids = new ArrayList<>();
        for (int i = 0; i < numberOfFluids; i++) {
            int randomWeight = getRandomIntInRange(1, cumulativeWeight);
            // Find the corresponding FluidStack based on randomWeight
            for (int j = 0; j < cumulativeWeights.size(); j++) {
                if (randomWeight <= cumulativeWeights.get(j)) {
                    pickedFluids.add(fluidEntryList.get(j).getKey());
                    break;
                }
            }
        }

        return pickedFluids.toArray(new FluidStack[0]);

    }

    private ItemStack[] getRandomItemInputs(int numberOfItems) {
        int cumulativeWeight = 0;

        List<Map.Entry<ItemStack, Integer>> ItemEntryList = new ArrayList<>(exoticModulePlasmaItemMap.entrySet());

        List<Integer> cumulativeWeights = new ArrayList<>();
        for (Map.Entry<ItemStack, Integer> entry : ItemEntryList) {
            cumulativeWeight += entry.getValue();
            cumulativeWeights.add(cumulativeWeight);
        }

        List<ItemStack> pickedItems = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            int randomWeight = getRandomIntInRange(1, cumulativeWeight);
            // Find the corresponding ItemStack based on randomWeight
            for (int j = 0; j < cumulativeWeights.size(); j++) {
                if (randomWeight <= cumulativeWeights.get(j)) {
                    pickedItems.add(ItemEntryList.get(j).getKey());
                    break;
                }
            }
        }
        return pickedItems.toArray(new ItemStack[0]);

    }

    private FluidStack[] convertToPlasma(ItemStack[] items, long multiplier) {
        List<FluidStack> plasmas = new ArrayList<>();

        for (ItemStack itemStack : items) {
            ItemData data = getAssociation(itemStack);
            Materials mat = data == null ? null : data.mMaterial.mMaterial;
            plasmas.add(mat.getPlasma(INGOTS * multiplier));
        }

        return plasmas.toArray(new FluidStack[0]);
    }

    private ArrayList<FluidStack> getFluidsStored() {
        return this.getStoredFluids();
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
