package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.loader.recipe.Godforge.exoticModulePlasmaFluidMap;
import static com.github.technus.tectech.loader.recipe.Godforge.exoticModulePlasmaItemMap;
import static com.github.technus.tectech.recipe.TecTechRecipeMaps.godforgeExoticMatterRecipes;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.util.TT_Utility.getRandomIntInRange;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static gregtech.api.util.GT_RecipeBuilder.INGOTS;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.getUserEU;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_ForgeOfGods;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.api.widget.IWidgetBuilder;
import com.gtnewhorizons.modularui.api.widget.Widget;
import com.gtnewhorizons.modularui.common.widget.ButtonWidget;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;

import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.gui.modularui.GT_UITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
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
    private int currentParallel = 0;
    private long wirelessEUt = 0;
    private long EUt = 0;
    private boolean recipeInProgress = false;
    private boolean magmatterCapable = false;
    private boolean magmatterMode = false;
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

                    if (numberOfFluids != 0) {
                        for (FluidStack fluidStack : randomizedFluidInput) {
                            fluidStack.amount = 1000 * getRandomIntInRange(1, 64);
                        }
                    }

                    if (numberOfItems != 0) {
                        for (ItemStack itemStack : randomizedItemInput) {
                            itemStack.stackSize = getRandomIntInRange(1, 64);
                        }
                    }

                    inputPlasmas = new ArrayList<>(Arrays.asList(convertItemToPlasma(randomizedItemInput, 1)));
                    inputPlasmas.addAll(Arrays.asList(convertFluidToPlasma(randomizedFluidInput, 1)));

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
                            dumpFluid(
                                    mOutputHatches,
                                    new FluidStack(fluidStack.getFluid(), fluidStack.amount / 1000),
                                    false);
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
                return super.createOverclockCalculator(recipe).setEUt(TierEU.MAX).setNoOverclock(true);
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
                    FluidStack pickedFluid = fluidEntryList.get(j).getKey();
                    // prevent duplicates
                    if (pickedFluids.contains(pickedFluid)) {
                        i--;
                        break;
                    }
                    pickedFluids.add(pickedFluid);
                    break;
                }
            }
        }

        return pickedFluids.toArray(new FluidStack[0]);

    }

    private ItemStack[] getRandomItemInputs(int numberOfItems) {
        int cumulativeWeight = 0;

        List<Map.Entry<ItemStack, Integer>> itemEntryList = new ArrayList<>(exoticModulePlasmaItemMap.entrySet());

        List<Integer> cumulativeWeights = new ArrayList<>();
        for (Map.Entry<ItemStack, Integer> entry : itemEntryList) {
            cumulativeWeight += entry.getValue();
            cumulativeWeights.add(cumulativeWeight);
        }

        List<ItemStack> pickedItems = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            int randomWeight = getRandomIntInRange(1, cumulativeWeight);
            // Find the corresponding ItemStack based on randomWeight
            for (int j = 0; j < cumulativeWeights.size(); j++) {
                if (randomWeight <= cumulativeWeights.get(j)) {
                    ItemStack pickedItem = itemEntryList.get(j).getKey();
                    // prevent duplicates
                    if (pickedItems.contains(pickedItem)) {
                        i--;
                        break;
                    }
                    pickedItems.add(pickedItem);
                    break;
                }
            }
        }
        return pickedItems.toArray(new ItemStack[0]);

    }

    private FluidStack[] convertItemToPlasma(ItemStack[] items, long multiplier) {
        List<FluidStack> plasmas = new ArrayList<>();

        for (ItemStack itemStack : items) {
            String dict = OreDictionary.getOreName(OreDictionary.getOreIDs(itemStack)[0]);
            // substring 8 because dustTiny is 8 characters long and there is no other possible oreDict
            String strippedOreDict = dict.substring(8);
            plasmas.add(
                    FluidRegistry.getFluidStack(
                            "plasma." + strippedOreDict.toLowerCase(),
                            (int) (INGOTS * multiplier * itemStack.stackSize)));
        }

        return plasmas.toArray(new FluidStack[0]);
    }

    private FluidStack[] convertFluidToPlasma(FluidStack[] fluids, long multiplier) {
        List<FluidStack> plasmas = new ArrayList<>();

        for (FluidStack fluidStack : fluids) {
            String[] fluidName = fluidStack.getUnlocalizedName().split("\\.");
            plasmas.add(
                    FluidRegistry.getFluidStack(
                            "plasma." + fluidName[fluidName.length - 1],
                            (int) (multiplier * fluidStack.amount)));
        }

        return plasmas.toArray(new FluidStack[0]);
    }

    private ArrayList<FluidStack> getFluidsStored() {
        return this.getStoredFluids();
    }

    @Override
    public boolean supportsSingleRecipeLocking() {
        return false;
    }

    @Override
    public boolean supportsBatchMode() {
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound NBT) {

        NBT.setBoolean("recipeInProgress", recipeInProgress);

        // Store damage values/stack sizes of input plasmas
        NBTTagCompound fluidStackListNBTTag = new NBTTagCompound();
        fluidStackListNBTTag.setLong("numberOfPlasmas", inputPlasmas.size());

        int indexFluids = 0;
        for (FluidStack fluidStack : inputPlasmas) {
            // Save fluid amount to NBT
            fluidStackListNBTTag.setLong(indexFluids + "fluidAmount", fluidStack.amount);

            // Save FluidStack to NBT
            NBT.setTag(indexFluids + "fluidStack", fluidStack.writeToNBT(new NBTTagCompound()));

            indexFluids++;
        }

        NBT.setTag("inputPlasmas", fluidStackListNBTTag);
        super.saveNBTData(NBT);
    }

    @Override
    public void loadNBTData(final NBTTagCompound NBT) {

        recipeInProgress = NBT.getBoolean("recipeInProgress");

        // Load damage values/fluid amounts of input plasmas and convert back to fluids
        NBTTagCompound tempFluidTag = NBT.getCompoundTag("inputPlasmas");

        // Iterate over all stored fluids
        for (int indexFluids = 0; indexFluids < tempFluidTag.getLong("numberOfPlasmas"); indexFluids++) {

            // Load fluid amount from NBT
            int fluidAmount = tempFluidTag.getInteger(indexFluids + "fluidAmount");

            // Load FluidStack from NBT
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(NBT.getCompoundTag(indexFluids + "fluidStack"));

            inputPlasmas.add(new FluidStack(fluidStack, fluidAmount));
        }
        super.loadNBTData(NBT);
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        if (magmatterCapable) {
            builder.widget(magmatterSwitch(builder));
        } else {
            builder.widget(magmatterSwitchLocked());
        }
    }

    protected ButtonWidget magmatterSwitch(IWidgetBuilder<?> builder) {
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> { magmatterMode = !magmatterMode; })
                .setPlayClickSoundResource(
                        () -> isAllowedToWork() ? SoundResource.GUI_BUTTON_UP.resourceLocation
                                : SoundResource.GUI_BUTTON_DOWN.resourceLocation)
                .setBackground(() -> {
                    if (magmatterMode) {
                        return new IDrawable[] { GT_UITextures.BUTTON_STANDARD_PRESSED,
                                GT_UITextures.OVERLAY_BUTTON_CHECKMARK };
                    } else {
                        return new IDrawable[] { GT_UITextures.BUTTON_STANDARD, GT_UITextures.OVERLAY_BUTTON_CROSS };
                    }
                }).attachSyncer(new FakeSyncWidget.BooleanSyncer(this::isAllowedToWork, val -> {
                    if (val) enableWorking();
                    else disableWorking();
                }), builder).addTooltip("Magmatter Mode").setTooltipShowUpDelay(TOOLTIP_DELAY).setPos(174, 91)
                .setSize(16, 16);
        return (ButtonWidget) button;
    }

    protected DrawableWidget magmatterSwitchLocked() {
        Widget icon = new DrawableWidget()
                .setBackground(GT_UITextures.BUTTON_STANDARD, GT_UITextures.OVERLAY_BUTTON_DISABLE)
                .addTooltip("Magmatter Mode Locked, missing upgrade").setTooltipShowUpDelay(TOOLTIP_DELAY)
                .setPos(174, 91).setSize(16, 16);
        return (DrawableWidget) icon;
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

}
