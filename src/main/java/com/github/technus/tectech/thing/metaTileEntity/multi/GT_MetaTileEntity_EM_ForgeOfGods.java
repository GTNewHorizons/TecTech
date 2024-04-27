package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.thing.casing.TT_Container_Casings.GodforgeCasings;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.forgeOfGodsRenderBlock;
import static com.github.technus.tectech.util.GodforgeMath.allowModuleConnection;
import static com.github.technus.tectech.util.GodforgeMath.calculateEnergyDiscountForModules;
import static com.github.technus.tectech.util.GodforgeMath.calculateFuelConsumption;
import static com.github.technus.tectech.util.GodforgeMath.calculateMaxFuelFactor;
import static com.github.technus.tectech.util.GodforgeMath.calculateMaxHeatForModules;
import static com.github.technus.tectech.util.GodforgeMath.calculateMaxParallelForModules;
import static com.github.technus.tectech.util.GodforgeMath.calculateProcessingVoltageForModules;
import static com.github.technus.tectech.util.GodforgeMath.calculateSpeedBonusForModules;
import static com.github.technus.tectech.util.GodforgeMath.queryMilestoneStats;
import static com.github.technus.tectech.util.GodforgeMath.setMiscModuleParameters;
import static com.github.technus.tectech.util.TT_Utility.toExponentForm;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static gregtech.api.enums.GT_HatchElement.InputBus;
import static gregtech.api.enums.GT_HatchElement.InputHatch;
import static gregtech.api.enums.GT_HatchElement.OutputBus;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.max;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.thing.block.GodforgeGlassBlock;
import com.github.technus.tectech.thing.block.TileForgeOfGods;
import com.github.technus.tectech.thing.gui.TecTechUITextures;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules.GT_MetaTileEntity_EM_BaseModule;
import com.github.technus.tectech.util.CommonValues;
import com.google.common.math.LongMath;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.api.widget.IWidgetBuilder;
import com.gtnewhorizons.modularui.api.widget.Widget;
import com.gtnewhorizons.modularui.common.widget.ButtonWidget;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.ExpandTab;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.FluidNameHolderWidget;
import com.gtnewhorizons.modularui.common.widget.MultiChildWidget;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import com.gtnewhorizons.modularui.common.widget.Scrollable;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import com.gtnewhorizons.modularui.common.widget.textfield.NumericWidget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GT_UITextures;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.util.GT_HatchElementBuilder;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.IGT_HatchAdder;

public class GT_MetaTileEntity_EM_ForgeOfGods extends GT_MetaTileEntity_MultiblockBase_EM
        implements IConstructable, ISurvivalConstructable {

    private static Textures.BlockIcons.CustomIcon ScreenON;

    private int fuelConsumptionFactor = 1;
    private int selectedFuelType = 0;
    private int internalBattery = 0;
    private int maxBatteryCharge = 100;
    private int gravitonShardsAvailable = 0;
    private int gravitonShardsSpent = 0;
    private int ringAmount = 1;
    private long fuelConsumption = 0;
    private long totalRecipesProcessed = 0;
    private long totalFuelConsumed = 0;
    private float powerMilestonePercentage = 0;
    private float recipeMilestonePercentage = 0;
    private float fuelMilestonePercentage = 0;
    private BigInteger totalPowerConsumed = BigInteger.ZERO;
    private boolean batteryCharging = false;
    public ArrayList<GT_MetaTileEntity_EM_BaseModule> moduleHatches = new ArrayList<>();

    private static final int FUEL_CONFIG_WINDOW_ID = 9;
    private static final int UPGRADE_TREE_WINDOW_ID = 10;
    private static final int INDIVIDUAL_UPGRADE_WINDOW_ID = 11;
    private static final int BATTERY_CONFIG_WINDOW_ID = 12;
    private static final int MILESTONE_WINDOW_ID = 13;
    private static final int TEXTURE_INDEX = 960;
    private static final int[] FIRST_SPLIT_UPGRADES = new int[] { 12, 13, 14 };
    private static final int[] RING_UPGRADES = new int[] { 26, 29 };
    private static final long POWER_MILESTONE_CONSTANT = LongMath.pow(10, 15);
    private static final long RECIPE_MILESTONE_CONSTANT = LongMath.pow(10, 7);
    private static final long FUEL_MILESTONE_CONSTANT = 10_000;
    private static final double POWER_LOG_CONSTANT = Math.log(9);
    private static final double RECIPE_LOG_CONSTANT = Math.log(6);
    private static final double FUEL_LOG_CONSTANT = Math.log(3);
    protected static final String STRUCTURE_PIECE_MAIN = "main";
    protected static final String STRUCTURE_PIECE_SECOND_RING = "second_ring";
    protected static final String STRUCTURE_PIECE_THIRD_RING = "third_ring";
    private static final String TOOLTIP_BAR = EnumChatFormatting.BLUE + "--------------------------------------------";

    private boolean debugMode = true;

    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        int realBudget = elementBudget >= 1000 ? elementBudget : Math.min(1000, elementBudget * 5);
        // 1000 blocks max per placement.
        int built = survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 63, 20, 0, realBudget, env, false, true);
        if (isUpgradeActive(26) && stackSize.stackSize > 1) {
            built += survivialBuildPiece(
                    STRUCTURE_PIECE_SECOND_RING,
                    stackSize,
                    55,
                    10,
                    -73,
                    realBudget,
                    env,
                    false,
                    true);
        }
        if (isUpgradeActive(29) && stackSize.stackSize > 2) {
            built += survivialBuildPiece(
                    STRUCTURE_PIECE_THIRD_RING,
                    stackSize,
                    47,
                    12,
                    -82,
                    realBudget,
                    env,
                    false,
                    true);
        }
        return built;
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_EM_ForgeOfGods> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    public static final IStructureDefinition<GT_MetaTileEntity_EM_ForgeOfGods> STRUCTURE_DEFINITION = IStructureDefinition
            .<GT_MetaTileEntity_EM_ForgeOfGods>builder()
            .addShape(STRUCTURE_PIECE_MAIN, ForgeOfGodsStructureString.MAIN_STRUCTURE)
            .addShape(STRUCTURE_PIECE_SECOND_RING, ForgeOfGodsRingsStructureString.SECOND_RING)
            .addShape(STRUCTURE_PIECE_THIRD_RING, ForgeOfGodsRingsStructureString.THIRD_RING)
            .addElement(
                    'A',
                    buildHatchAdder(GT_MetaTileEntity_EM_ForgeOfGods.class).atLeast(InputHatch, InputBus, OutputBus)
                            .casingIndex(TEXTURE_INDEX + 1).dot(1).buildAndChain(GodforgeCasings, 1))
            .addElement('B', ofBlock(GodforgeCasings, 0)).addElement('C', ofBlock(GodforgeCasings, 1))
            .addElement('D', ofBlock(GodforgeCasings, 2)).addElement('E', ofBlock(GodforgeCasings, 3))
            .addElement('F', ofBlock(GodforgeCasings, 4)).addElement('G', ofBlock(GodforgeCasings, 5))
            .addElement('H', ofBlock(GodforgeGlassBlock.INSTANCE, 0)).addElement('I', ofBlock(GodforgeCasings, 7))
            .addElement(
                    'J',
                    GT_HatchElementBuilder.<GT_MetaTileEntity_EM_ForgeOfGods>builder().atLeast(moduleElement.Module)
                            .casingIndex(TEXTURE_INDEX).dot(3).buildAndChain(GodforgeCasings, 0))
            .addElement('K', ofBlock(GodforgeCasings, 6)).build();

    public GT_MetaTileEntity_EM_ForgeOfGods(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_ForgeOfGods(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_ForgeOfGods(mName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        ScreenON = new Textures.BlockIcons.CustomIcon("iconsets/GODFORGE_CONTROLLER");
        super.registerIcons(aBlockIconRegister);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(TEXTURE_INDEX + 1),
                    new TT_RenderedExtendedFacingTexture(ScreenON) };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(TEXTURE_INDEX + 1) };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM(STRUCTURE_PIECE_MAIN, 63, 14, 0, stackSize, hintsOnly);
        if (isUpgradeActive(26) && stackSize.stackSize > 1) {
            buildPiece(STRUCTURE_PIECE_SECOND_RING, stackSize, hintsOnly, 55, 10, -73);
        }
        if (isUpgradeActive(29) && stackSize.stackSize > 2) {
            buildPiece(STRUCTURE_PIECE_THIRD_RING, stackSize, hintsOnly, 47, 12, -82);
        }
    }

    private final ArrayList<FluidStack> validFuelList = new ArrayList<>() {

        {
            add(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1));
            add(MaterialsUEVplus.RawStarMatter.getFluid(1));
            add(MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter.getMolten(1));
        }
    };

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {

        moduleHatches.clear();

        // Check structure of multi.
        if (!structureCheck_EM(STRUCTURE_PIECE_MAIN, 63, 14, 0)) {
            return false;
        }

        // Check there is 1 input bus.
        if (mInputBusses.size() != 1) {
            return false;
        }

        // Make sure there are no energy hatches.

        if (mEnergyHatches.size() > 0) {
            return false;
        }

        if (mExoticEnergyHatches.size() > 0) {
            return false;
        }

        // Make sure there is 1 input hatch.
        if (mInputHatches.size() != 1) {
            return false;
        }

        if (isUpgradeActive(26) && checkPiece(STRUCTURE_PIECE_SECOND_RING, 55, 10, -73)) {
            ringAmount = 2;
            if (isUpgradeActive(29) && checkPiece(STRUCTURE_PIECE_THIRD_RING, 47, 12, -82)) {
                ringAmount = 3;
            }
        }

        mHardHammer = true;
        mSoftHammer = true;
        mScrewdriver = true;
        mCrowbar = true;
        mSolderingTool = true;
        mWrench = true;
        return true;
    }

    int ticker = 0;

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (getBaseMetaTileEntity().isAllowedToWork()) {
                ticker++;
                // Check and drain fuel
                if (ticker % (5 * SECONDS) == 0) {
                    ticker = 0;
                    // TODO: Fix NPE here
                    FluidStack fluidInHatch = mInputHatches.get(0).getFluid();
                    int maxModuleCount = 8;

                    if (upgrades[26]) {
                        maxModuleCount += 4;
                    }
                    if (upgrades[29]) {
                        maxModuleCount += 4;
                    }

                    determineMilestoneProgress();
                    if (!debugMode) {
                        determineGravitonShardAmount();
                    }

                    fuelConsumption = (long) calculateFuelConsumption(this) * 5 * (batteryCharging ? 2 : 1);
                    if (fluidInHatch != null && fluidInHatch.isFluidEqual(validFuelList.get(selectedFuelType))) {
                        FluidStack fluidNeeded = new FluidStack(
                                validFuelList.get(selectedFuelType),
                                (int) fuelConsumption);
                        FluidStack fluidReal = mInputHatches.get(0).drain(fluidNeeded.amount, true);
                        if (fluidReal == null || fluidReal.amount < fluidNeeded.amount) {
                            reduceBattery(fuelConsumptionFactor);
                        } else {
                            totalFuelConsumed += getFuelFactor();
                            if (batteryCharging) {
                                increaseBattery(fuelConsumptionFactor);
                            }
                        }
                    } else {
                        reduceBattery(fuelConsumptionFactor);
                    }
                    // Do module calculations and checks
                    if (moduleHatches.size() > 0 && internalBattery > 0 && moduleHatches.size() <= maxModuleCount) {
                        for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                            if (allowModuleConnection(module, this)) {
                                module.connect();
                                calculateMaxHeatForModules(module, this);
                                calculateSpeedBonusForModules(module, this);
                                calculateMaxParallelForModules(module, this);
                                calculateEnergyDiscountForModules(module, this);
                                setMiscModuleParameters(module, this);
                                queryMilestoneStats(module, this);
                                if (!upgrades[28]) {
                                    calculateProcessingVoltageForModules(module, this);
                                }
                            } else {
                                module.disconnect();
                            }
                        }
                    } else if (moduleHatches.size() > maxModuleCount) {
                        for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                            module.disconnect();
                        }
                    }
                }
            } else {
                if (moduleHatches.size() > 0) {
                    for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                        module.disconnect();
                    }
                }
            }
            if (mEfficiency < 0) mEfficiency = 0;
            fixAllMaintenance();
        }
    }

    public boolean addModuleToMachineList(IGregTechTileEntity tileEntity, int baseCasingIndex) {
        if (tileEntity == null) {
            return false;
        }
        IMetaTileEntity metaTileEntity = tileEntity.getMetaTileEntity();
        if (metaTileEntity == null) {
            return false;
        }
        if (metaTileEntity instanceof GT_MetaTileEntity_EM_BaseModule) {
            return moduleHatches.add((GT_MetaTileEntity_EM_BaseModule) metaTileEntity);
        }
        return false;
    }

    public enum moduleElement implements IHatchElement<GT_MetaTileEntity_EM_ForgeOfGods> {

        Module(GT_MetaTileEntity_EM_ForgeOfGods::addModuleToMachineList, GT_MetaTileEntity_EM_BaseModule.class) {

            @Override
            public long count(GT_MetaTileEntity_EM_ForgeOfGods tileEntity) {
                return tileEntity.moduleHatches.size();
            }
        };

        private final List<Class<? extends IMetaTileEntity>> mteClasses;
        private final IGT_HatchAdder<GT_MetaTileEntity_EM_ForgeOfGods> adder;

        @SafeVarargs
        moduleElement(IGT_HatchAdder<GT_MetaTileEntity_EM_ForgeOfGods> adder,
                Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
            this.adder = adder;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        public IGT_HatchAdder<? super GT_MetaTileEntity_EM_ForgeOfGods> adder() {
            return adder;
        }
    }

    private void createRenderBlock() {

        IGregTechTileEntity gregTechTileEntity = this.getBaseMetaTileEntity();

        int x = gregTechTileEntity.getXCoord();
        int y = gregTechTileEntity.getYCoord();
        int z = gregTechTileEntity.getZCoord();

        double xOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetX;
        double zOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetZ;
        double yOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetY;

        this.getBaseMetaTileEntity().getWorld()
                .setBlock((int) (x + xOffset), (int) (y + yOffset), (int) (z + zOffset), Blocks.air);
        this.getBaseMetaTileEntity().getWorld()
                .setBlock((int) (x + xOffset), (int) (y + yOffset), (int) (z + zOffset), forgeOfGodsRenderBlock);
        TileForgeOfGods rendererTileEntity = (TileForgeOfGods) this.getBaseMetaTileEntity().getWorld()
                .getTileEntity((int) (x + xOffset), (int) (y + yOffset), (int) (z + zOffset));

        rendererTileEntity.setRenderSize(20);
        rendererTileEntity.setRenderRotationSpeed(5);
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> str = new ArrayList<>(Arrays.asList(super.getInfoData()));
        str.add(TOOLTIP_BAR);
        str.add("Number of Rings: " + EnumChatFormatting.GOLD + ringAmount);
        str.add("Total Upgrades Unlocked: " + EnumChatFormatting.GOLD + getTotalActiveUpgrades());
        str.add("Connected Modules: " + EnumChatFormatting.GOLD + moduleHatches.size());
        str.add(TOOLTIP_BAR);
        return str.toArray(new String[0]);
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        createRenderBlock();
    }

    @Override
    public void onRemoval() {
        if (moduleHatches != null && moduleHatches.size() > 0) {
            for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                module.disconnect();
            }
        }
        super.onRemoval();
    }

    protected void fixAllMaintenance() {
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        if (doesBindPlayerInventory()) {
            builder.widget(
                    new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 4)
                            .setSize(190, 85));
        } else {
            builder.widget(
                    new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE_NO_INVENTORY).setPos(4, 4)
                            .setSize(190, 171));
        }
        buildContext.addSyncedWindow(UPGRADE_TREE_WINDOW_ID, this::createUpgradeTreeWindow);
        buildContext.addSyncedWindow(INDIVIDUAL_UPGRADE_WINDOW_ID, this::createIndividualUpgradeWindow);
        buildContext.addSyncedWindow(FUEL_CONFIG_WINDOW_ID, this::createFuelConfigWindow);
        buildContext.addSyncedWindow(BATTERY_CONFIG_WINDOW_ID, this::createBatteryWindow);
        buildContext.addSyncedWindow(MILESTONE_WINDOW_ID, this::createMilestoneWindow);
        builder.widget(
                new ButtonWidget().setOnClick(
                        (clickData, widget) -> {
                            if (!widget.isClient()) widget.getContext().openSyncedWindow(UPGRADE_TREE_WINDOW_ID);
                        }).setSize(16, 16).setBackground(() -> {
                            List<UITexture> button = new ArrayList<>();
                            button.add(TecTechUITextures.BUTTON_CELESTIAL_32x32);
                            button.add(TecTechUITextures.OVERLAY_BUTTON_ARROW_BLUE_UP);
                            return button.toArray(new IDrawable[0]);
                        }).addTooltip("Path of Celestial Transcendence").setPos(174, 167)
                        .setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_HEAT_SINK_SMALL).setPos(174, 183)
                                .setSize(16, 6))
                .widget(new ButtonWidget().setOnClick((clickData, widget) -> {
                    if (!widget.isClient()) {
                        widget.getContext().openSyncedWindow(FUEL_CONFIG_WINDOW_ID);
                    }
                }).setSize(16, 16).setBackground(() -> {
                    List<UITexture> button = new ArrayList<>();
                    button.add(TecTechUITextures.BUTTON_CELESTIAL_32x32);
                    button.add(TecTechUITextures.OVERLAY_BUTTON_HEAT_ON);
                    return button.toArray(new IDrawable[0]);
                }).addTooltip(translateToLocal("fog.button.fuelconfig.tooltip")).setPos(174, 110)
                        .setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        TextWidget.dynamicText(this::storedFuel).setDefaultColor(EnumChatFormatting.WHITE).setPos(6, 8)
                                .setSize(74, 34))
                .widget(createPowerSwitchButton()).widget(createBatteryButton(builder))
                .widget(new FakeSyncWidget.BooleanSyncer(() -> getBaseMetaTileEntity().isAllowedToWork(), val -> {
                    if (val) {
                        getBaseMetaTileEntity().enableWorking();
                    } else {
                        getBaseMetaTileEntity().disableWorking();
                    }
                })).widget(new ButtonWidget().setOnClick((clickData, widget) -> {
                    if (!widget.isClient()) {
                        checkMachine_EM(this.getBaseMetaTileEntity(), null);
                    }
                }).setSize(16, 16).setBackground(() -> {
                    List<UITexture> button = new ArrayList<>();
                    button.add(TecTechUITextures.BUTTON_CELESTIAL_32x32);
                    button.add(TecTechUITextures.OVERLAY_CYCLIC_BLUE);
                    return button.toArray(new IDrawable[0]);
                }).addTooltip(translateToLocal("fog.button.structurecheck.tooltip")).setPos(8, 91)
                        .setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(new ButtonWidget().setOnClick((clickData, widget) -> {
                    if (!widget.isClient()) {
                        widget.getContext().openSyncedWindow(MILESTONE_WINDOW_ID);
                    }
                }).setSize(16, 16).setBackground(() -> {
                    List<UITexture> button = new ArrayList<>();
                    button.add(TecTechUITextures.BUTTON_CELESTIAL_32x32);
                    button.add(TecTechUITextures.OVERLAY_BUTTON_FLAG);
                    return button.toArray(new IDrawable[0]);
                }).addTooltip(translateToLocal("fog.button.milestones.tooltip")).setTooltipShowUpDelay(TOOLTIP_DELAY)
                        .setPos(174, 91));
    }

    @Override
    public void addGregTechLogo(ModularWindow.Builder builder) {
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_GODFORGE_LOGO).setSize(18, 18)
                        .setPos(172, 67));
    }

    @Override
    protected ButtonWidget createPowerSwitchButton() {
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
            TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
            if (getBaseMetaTileEntity().isAllowedToWork()) {
                getBaseMetaTileEntity().disableWorking();
            } else {
                getBaseMetaTileEntity().enableWorking();
            }
        }).setPlayClickSound(false).setBackground(() -> {
            List<UITexture> ret = new ArrayList<>();
            ret.add(TecTechUITextures.BUTTON_CELESTIAL_32x32);
            if (getBaseMetaTileEntity().isAllowedToWork()) {
                ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_ON);
            } else {
                ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_DISABLED);
            }
            return ret.toArray(new IDrawable[0]);
        }).setPos(174, doesBindPlayerInventory() ? 148 : 172).setSize(16, 16);
        button.addTooltip("Power Switch").setTooltipShowUpDelay(TOOLTIP_DELAY);
        return (ButtonWidget) button;
    }

    protected Widget createBatteryButton(IWidgetBuilder<?> builder) {
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
            TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
            if (clickData.mouseButton == 0) {
                batteryCharging = !batteryCharging;
            } else if (clickData.mouseButton == 1 && !widget.isClient() && upgrades[8]) {
                widget.getContext().openSyncedWindow(BATTERY_CONFIG_WINDOW_ID);
            }
        }).setPlayClickSound(false).setBackground(() -> {
            List<UITexture> ret = new ArrayList<>();
            ret.add(TecTechUITextures.BUTTON_CELESTIAL_32x32);
            if (batteryCharging) {
                ret.add(TecTechUITextures.OVERLAY_BUTTON_BATTERY_ON);
            } else {
                ret.add(TecTechUITextures.OVERLAY_BUTTON_BATTERY_OFF);
            }
            return ret.toArray(new IDrawable[0]);
        }).setPos(174, 129).setSize(16, 16);
        button.addTooltip(translateToLocal("fog.button.battery.tooltip.01"))
                .addTooltip(EnumChatFormatting.GRAY + translateToLocal("fog.button.battery.tooltip.02"))
                .setTooltipShowUpDelay(TOOLTIP_DELAY).attachSyncer(
                        new FakeSyncWidget.BooleanSyncer(() -> batteryCharging, val -> batteryCharging = val),
                        builder);
        return button;
    }

    protected ModularWindow createBatteryWindow(final EntityPlayer player) {
        final int WIDTH = 78;
        final int HEIGHT = 52;
        final int PARENT_WIDTH = getGUIWidth();
        final int PARENT_HEIGHT = getGUIHeight();
        ModularWindow.Builder builder = ModularWindow.builder(WIDTH, HEIGHT);
        builder.setBackground(GT_UITextures.BACKGROUND_SINGLEBLOCK_DEFAULT);
        builder.setGuiTint(getGUIColorization());
        builder.setDraggable(true);
        builder.setPos(
                (size, window) -> Alignment.Center.getAlignedPos(size, new Size(PARENT_WIDTH, PARENT_HEIGHT)).add(
                        Alignment.BottomRight
                                .getAlignedPos(new Size(PARENT_WIDTH, PARENT_HEIGHT), new Size(WIDTH, HEIGHT))
                                .add(WIDTH - 3, 0).subtract(0, 10)));
        builder.widget(
                TextWidget.localised("gt.blockmachines.multimachine.FOG.batteryinfo").setPos(3, 4).setSize(74, 20))
                .widget(
                        new NumericWidget().setSetter(val -> maxBatteryCharge = (int) val)
                                .setGetter(() -> maxBatteryCharge).setBounds(1, Integer.MAX_VALUE).setDefaultValue(100)
                                .setScrollValues(1, 4, 64).setTextAlignment(Alignment.Center)
                                .setTextColor(Color.WHITE.normal).setSize(70, 18).setPos(4, 25)
                                .setBackground(GT_UITextures.BACKGROUND_TEXT_FIELD));
        return builder.build();
    }

    protected ModularWindow createFuelConfigWindow(final EntityPlayer player) {
        final int WIDTH = 78;
        final int HEIGHT = 130;
        final int PARENT_WIDTH = getGUIWidth();
        final int PARENT_HEIGHT = getGUIHeight();
        ModularWindow.Builder builder = ModularWindow.builder(WIDTH, HEIGHT);
        builder.setBackground(GT_UITextures.BACKGROUND_SINGLEBLOCK_DEFAULT);
        builder.setGuiTint(getGUIColorization());
        builder.setDraggable(true);
        builder.setPos(
                (size, window) -> Alignment.Center.getAlignedPos(size, new Size(PARENT_WIDTH, PARENT_HEIGHT)).add(
                        Alignment.TopRight.getAlignedPos(new Size(PARENT_WIDTH, PARENT_HEIGHT), new Size(WIDTH, HEIGHT))
                                .add(WIDTH - 3, 0)));
        builder.widget(
                TextWidget.localised("gt.blockmachines.multimachine.FOG.fuelconsumption").setPos(3, 2).setSize(74, 34))
                .widget(
                        new NumericWidget().setSetter(val -> fuelConsumptionFactor = (int) val)
                                .setGetter(() -> fuelConsumptionFactor).setBounds(1, calculateMaxFuelFactor(this))
                                .setDefaultValue(1).setScrollValues(1, 4, 64).setTextAlignment(Alignment.Center)
                                .setTextColor(Color.WHITE.normal).setSize(70, 18).setPos(4, 35)
                                .setBackground(GT_UITextures.BACKGROUND_TEXT_FIELD))
                .widget(
                        new DrawableWidget().setDrawable(ModularUITextures.ICON_INFO).setPos(64, 24).setSize(10, 10)
                                .addTooltip(translateToLocal("gt.blockmachines.multimachine.FOG.fuelinfo.0"))
                                .addTooltip(translateToLocal("gt.blockmachines.multimachine.FOG.fuelinfo.1"))
                                .addTooltip(translateToLocal("gt.blockmachines.multimachine.FOG.fuelinfo.2"))
                                .addTooltip(translateToLocal("gt.blockmachines.multimachine.FOG.fuelinfo.3"))
                                .addTooltip(translateToLocal("gt.blockmachines.multimachine.FOG.fuelinfo.4"))
                                .setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        TextWidget.localised("gt.blockmachines.multimachine.FOG.fueltype").setPos(3, 57)
                                .setSize(74, 24))
                .widget(
                        TextWidget.localised("gt.blockmachines.multimachine.FOG.fuelusage").setPos(3, 100)
                                .setSize(74, 20))
                .widget(TextWidget.dynamicText(this::fuelUsage).setPos(3, 115).setSize(74, 15))
                .widget(
                        new MultiChildWidget().addChild(
                                new FluidNameHolderWidget(
                                        () -> MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1)
                                                .getUnlocalizedName().substring(6),
                                        (String) -> MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1)
                                                .getUnlocalizedName()) {

                                    @Override
                                    public void buildTooltip(List<Text> tooltip) {
                                        FluidStack fluid = createFluidStack();
                                        addFluidNameInfo(tooltip, fluid);
                                        addAdditionalFluidInfo(tooltip, fluid);
                                    }
                                }.setTooltipShowUpDelay(TOOLTIP_DELAY).setPos(1, 1).setSize(16, 16))
                                .addChild(new ButtonWidget().setOnClick((clickData, widget) -> {
                                    TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                                    selectedFuelType = 0;
                                }).setBackground(() -> {
                                    if (selectedFuelType == 0) {
                                        return new IDrawable[] { TecTechUITextures.SLOT_OUTLINE_GREEN };
                                    } else {
                                        return new IDrawable[] {};
                                    }
                                }).setSize(18, 18).attachSyncer(
                                        new FakeSyncWidget.IntegerSyncer(this::getFuelType, this::setFuelType),
                                        builder))

                                .setPos(6, 82).setSize(18, 18))
                .widget(
                        new MultiChildWidget().addChild(
                                new FluidNameHolderWidget(
                                        () -> MaterialsUEVplus.RawStarMatter.getFluid(1).getUnlocalizedName()
                                                .substring(6),
                                        (String) -> MaterialsUEVplus.RawStarMatter.getFluid(1).getUnlocalizedName()) {

                                    @Override
                                    public void buildTooltip(List<Text> tooltip) {
                                        FluidStack fluid = createFluidStack();
                                        addFluidNameInfo(tooltip, fluid);
                                        addAdditionalFluidInfo(tooltip, fluid);
                                    }
                                }.setTooltipShowUpDelay(TOOLTIP_DELAY).setPos(1, 1).setSize(16, 16))
                                .addChild(new ButtonWidget().setOnClick((clickData, widget) -> {
                                    TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                                    selectedFuelType = 1;
                                }).setBackground(() -> {
                                    if (selectedFuelType == 1) {
                                        return new IDrawable[] { TecTechUITextures.SLOT_OUTLINE_GREEN };
                                    } else {
                                        return new IDrawable[] {};
                                    }
                                }).setSize(18, 18)).setPos(29, 82).setSize(18, 18).attachSyncer(
                                        new FakeSyncWidget.IntegerSyncer(this::getFuelType, this::setFuelType),
                                        builder))
                .widget(
                        new MultiChildWidget().addChild(
                                new FluidNameHolderWidget(
                                        () -> MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter.getMolten(1)
                                                .getUnlocalizedName().substring(6),
                                        (String) -> MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter
                                                .getMolten(1).getUnlocalizedName()) {

                                    @Override
                                    public void buildTooltip(List<Text> tooltip) {
                                        FluidStack fluid = createFluidStack();
                                        addFluidNameInfo(tooltip, fluid);
                                        addAdditionalFluidInfo(tooltip, fluid);
                                    }
                                }.setTooltipShowUpDelay(TOOLTIP_DELAY).setPos(1, 1).setSize(16, 16))
                                .addChild(new ButtonWidget().setOnClick((clickData, widget) -> {
                                    TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                                    selectedFuelType = 2;
                                }).setBackground(() -> {
                                    if (selectedFuelType == 2) {
                                        return new IDrawable[] { TecTechUITextures.SLOT_OUTLINE_GREEN };
                                    } else {
                                        return new IDrawable[] {};
                                    }
                                }).setSize(18, 18)).setPos(52, 82).setSize(18, 18).attachSyncer(
                                        new FakeSyncWidget.IntegerSyncer(this::getFuelType, this::setFuelType),
                                        builder));

        return builder.build();
    }

    private int[] milestoneProgress = new int[] { 0, 0, 0, 4 };

    protected ModularWindow createMilestoneWindow(final EntityPlayer player) {
        final int WIDTH = 400;
        final int HEIGHT = 300;
        ModularWindow.Builder builder = ModularWindow.builder(WIDTH, HEIGHT);
        builder.setBackground(GT_UITextures.BACKGROUND_SINGLEBLOCK_DEFAULT);
        builder.setGuiTint(getGUIColorization());
        builder.setDraggable(true);
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_GODFORGE_MILESTONE_CHARGE).setPos(62, 24)
                        .setSize(80, 100));
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_GODFORGE_MILESTONE_CONVERSION)
                        .setPos(263, 25).setSize(70, 98));
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_GODFORGE_MILESTONE_CATALYST).setPos(52, 169)
                        .setSize(100, 100));
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_GODFORGE_MILESTONE_COMPOSITION)
                        .setPos(248, 169).setSize(100, 100));
        builder.widget(
                TextWidget.localised("gt.blockmachines.multimachine.FOG.powermilestone").setPos(77, 45)
                        .setSize(50, 30));
        builder.widget(
                TextWidget.localised("gt.blockmachines.multimachine.FOG.recipemilestone").setPos(268, 45)
                        .setSize(60, 30));
        builder.widget(
                TextWidget.localised("gt.blockmachines.multimachine.FOG.fuelmilestone").setPos(77, 190)
                        .setSize(50, 30));
        builder.widget(
                TextWidget.localised("gt.blockmachines.multimachine.FOG.purchasablemilestone").setPos(268, 190)
                        .setSize(60, 30));
        builder.widget(
                new ProgressBar().setProgress(() -> powerMilestonePercentage).setDirection(ProgressBar.Direction.RIGHT)
                        .setTexture(TecTechUITextures.PROGRESSBAR_GODFORGE_MILESTONE_RED, 130).setSynced(true, false)
                        .setSize(130, 7).setPos(37, 70).addTooltip(milestoneProgressText(1, false))
                        .setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        new ProgressBar().setProgress(() -> recipeMilestonePercentage)
                                .setDirection(ProgressBar.Direction.RIGHT)
                                .setTexture(TecTechUITextures.PROGRESSBAR_GODFORGE_MILESTONE_PURPLE, 130)
                                .setSynced(true, false).setSize(130, 7).setPos(233, 70)
                                .addTooltip(milestoneProgressText(2, false)).setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        new ProgressBar().setProgress(() -> fuelMilestonePercentage)
                                .setDirection(ProgressBar.Direction.RIGHT)
                                .setTexture(TecTechUITextures.PROGRESSBAR_GODFORGE_MILESTONE_BLUE, 130)
                                .setSynced(true, false).setSize(130, 7).setPos(37, 215)
                                .addTooltip(milestoneProgressText(3, false)).setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        new ProgressBar().setProgress(() -> milestoneProgress[3] / 7f)
                                .setDirection(ProgressBar.Direction.RIGHT)
                                .setTexture(TecTechUITextures.PROGRESSBAR_GODFORGE_MILESTONE_RAINBOW, 130)
                                .setSynced(true, false).setSize(130, 7).setPos(233, 215)
                                .addTooltip(milestoneProgressText(4, false)).setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        TextWidget.dynamicText(() -> milestoneProgressText(1, true)).setTextAlignment(Alignment.Center)
                                .setScale(0.7f).setMaxWidth(90).setDefaultColor(EnumChatFormatting.DARK_GRAY)
                                .setPos(150, 85)
                                .addTooltip(translateToLocal("gt.blockmachines.multimachine.FOG.milestoneprogress"))
                                .setTooltipShowUpDelay(TOOLTIP_DELAY))
                .widget(
                        new ExpandTab().setNormalTexture(ModularUITextures.ARROW_DOWN.withFixedSize(14, 14, 3, 3))
                                .widget(
                                        new DrawableWidget().setDrawable(ModularUITextures.ARROW_UP).setSize(14, 14)
                                                .setPos(3, 3))
                                .widget(
                                        TextWidget.dynamicText(() -> milestoneProgressText(1, false)).setScale(0.5f)
                                                .setSize(100, 20).setPos(5, 20))
                                .setExpandedSize(130, 130).setSize(20, 20).setPos(37, 75)
                                .setBackground(TecTechUITextures.BACKGROUND_GLOW_ORANGE))
                .widget(ButtonWidget.closeWindowButton(true).setPos(384, 4));
        return builder.build();
    }

    private int currentUpgradeID = 0;
    private int currentColorCode = 0;
    private int gravitonShardCost = 0;
    private int[] prereqUpgrades = new int[] {};
    private int[] followupUpgrades = new int[] {};
    private boolean allPrereqRequired = false;
    private boolean isUpradeSplitStart = false;
    private boolean[] upgrades = new boolean[31];

    protected ModularWindow createUpgradeTreeWindow(final EntityPlayer player) {
        final Scrollable scrollable = new Scrollable().setVerticalScroll();
        final int PARENT_WIDTH = 300;
        final int PARENT_HEIGHT = 1000;
        ModularWindow.Builder builder = ModularWindow.builder(PARENT_WIDTH, PARENT_HEIGHT);
        scrollable.widget(
                createUpgradeBox(0, 0, new int[] {}, false, new int[] { 1 }, false, 0, new Pos2d(126, 56), scrollable))
                .widget(
                        createUpgradeBox(
                                1,
                                0,
                                new int[] { 0 },
                                false,
                                new int[] { 2, 3 },
                                false,
                                1,
                                new Pos2d(126, 116),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                2,
                                0,
                                new int[] { 1 },
                                false,
                                new int[] { 4, 5 },
                                false,
                                1,
                                new Pos2d(96, 176),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                3,
                                0,
                                new int[] { 1 },
                                false,
                                new int[] { 5, 6 },
                                false,
                                1,
                                new Pos2d(156, 176),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                4,
                                0,
                                new int[] { 2 },
                                false,
                                new int[] { 8 },
                                false,
                                1,
                                new Pos2d(66, 236),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                5,
                                0,
                                new int[] { 2, 3 },
                                false,
                                new int[] { 7 },
                                false,
                                1,
                                new Pos2d(126, 236),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                6,
                                0,
                                new int[] { 3 },
                                false,
                                new int[] { 10 },
                                false,
                                1,
                                new Pos2d(186, 236),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                7,
                                0,
                                new int[] { 5 },
                                false,
                                new int[] { 8, 9, 10 },
                                false,
                                2,
                                new Pos2d(126, 296),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                8,
                                0,
                                new int[] { 4, 7 },
                                true,
                                new int[] { 11 },
                                false,
                                2,
                                new Pos2d(56, 356),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                9,
                                0,
                                new int[] { 7 },
                                false,
                                new int[] {},
                                false,
                                2,
                                new Pos2d(126, 356),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                10,
                                0,
                                new int[] { 6, 7 },
                                true,
                                new int[] { 11 },
                                false,
                                2,
                                new Pos2d(196, 356),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                11,
                                0,
                                new int[] { 8, 10 },
                                false,
                                new int[] { 12, 13, 14 },
                                false,
                                2,
                                new Pos2d(126, 416),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                12,
                                1,
                                new int[] { 11 },
                                false,
                                new int[] { 17 },
                                true,
                                3,
                                new Pos2d(66, 476),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                13,
                                2,
                                new int[] { 11 },
                                false,
                                new int[] { 18 },
                                true,
                                3,
                                new Pos2d(126, 476),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                14,
                                3,
                                new int[] { 11 },
                                false,
                                new int[] { 15, 19 },
                                true,
                                3,
                                new Pos2d(186, 476),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                15,
                                3,
                                new int[] { 14 },
                                false,
                                new int[] {},
                                false,
                                4,
                                new Pos2d(246, 496),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                16,
                                1,
                                new int[] { 17 },
                                false,
                                new int[] {},
                                false,
                                4,
                                new Pos2d(6, 556),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                17,
                                1,
                                new int[] { 12 },
                                false,
                                new int[] { 16, 20 },
                                false,
                                3,
                                new Pos2d(66, 536),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                18,
                                2,
                                new int[] { 13 },
                                false,
                                new int[] { 21 },
                                false,
                                3,
                                new Pos2d(126, 536),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                19,
                                3,
                                new int[] { 14 },
                                false,
                                new int[] { 22 },
                                false,
                                3,
                                new Pos2d(186, 536),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                20,
                                1,
                                new int[] { 17 },
                                false,
                                new int[] { 23 },
                                false,
                                3,
                                new Pos2d(66, 596),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                21,
                                2,
                                new int[] { 18 },
                                false,
                                new int[] { 23 },
                                false,
                                3,
                                new Pos2d(126, 596),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                22,
                                3,
                                new int[] { 19 },
                                false,
                                new int[] { 23 },
                                false,
                                3,
                                new Pos2d(186, 596),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                23,
                                0,
                                new int[] { 20, 21, 22 },
                                false,
                                new int[] { 24 },
                                false,
                                4,
                                new Pos2d(126, 656),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                24,
                                0,
                                new int[] { 23 },
                                false,
                                new int[] { 25 },
                                false,
                                5,
                                new Pos2d(126, 718),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                25,
                                0,
                                new int[] { 24 },
                                false,
                                new int[] { 26 },
                                false,
                                6,
                                new Pos2d(36, 758),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                26,
                                0,
                                new int[] { 25 },
                                false,
                                new int[] { 27 },
                                false,
                                7,
                                new Pos2d(36, 848),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                27,
                                0,
                                new int[] { 26 },
                                false,
                                new int[] { 28 },
                                false,
                                8,
                                new Pos2d(126, 888),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                28,
                                0,
                                new int[] { 27 },
                                false,
                                new int[] { 29 },
                                false,
                                9,
                                new Pos2d(216, 848),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                29,
                                0,
                                new int[] { 28 },
                                false,
                                new int[] { 30 },
                                false,
                                10,
                                new Pos2d(216, 758),
                                scrollable))
                .widget(
                        createUpgradeBox(
                                30,
                                0,
                                new int[] { 29 },
                                false,
                                new int[] {},
                                false,
                                12,
                                new Pos2d(126, 798),
                                scrollable))
                .widget(new TextWidget("").setPos(0, 1000));

        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_STAR).setPos(0, 350).setSize(300, 300))
                .widget(scrollable.setSize(292, 292).setPos(4, 354))
                .widget(ButtonWidget.closeWindowButton(true).setPos(282, 354));
        if (debugMode) {
            builder.widget(
                    new MultiChildWidget()
                            .addChild(
                                    new ButtonWidget().setOnClick((clickData, widget) -> upgrades = new boolean[31])
                                            .setSize(40, 15).setBackground(GT_UITextures.BUTTON_STANDARD)
                                            .addTooltip(translateToLocal("fog.debug.resetbutton.tooltip"))
                                            .setTooltipShowUpDelay(TOOLTIP_DELAY))
                            .addChild(
                                    new TextWidget(translateToLocal("fog.debug.resetbutton.text"))
                                            .setTextAlignment(Alignment.Center).setScale(0.57f).setMaxWidth(36)
                                            .setPos(3, 3))
                            .addChild(
                                    new NumericWidget().setSetter(val -> gravitonShardsAvailable = (int) val)
                                            .setGetter(() -> gravitonShardsAvailable).setBounds(0, 112)
                                            .setDefaultValue(0).setScrollValues(1, 4, 64)
                                            .setTextAlignment(Alignment.Center).setTextColor(Color.WHITE.normal)
                                            .setSize(25, 18).setPos(4, 16)
                                            .addTooltip(translateToLocal("fog.debug.gravitonshardsetter.tooltip"))
                                            .setTooltipShowUpDelay(TOOLTIP_DELAY)
                                            .setBackground(GT_UITextures.BACKGROUND_TEXT_FIELD))
                            .setPos(4, 354));

        }
        return builder.build();
    }

    protected ModularWindow createIndividualUpgradeWindow(final EntityPlayer player) {
        UITexture background = switch (currentColorCode) {
            case 1 -> TecTechUITextures.BACKGROUND_GLOW_PURPLE;
            case 2 -> TecTechUITextures.BACKGROUND_GLOW_ORANGE;
            case 3 -> TecTechUITextures.BACKGROUND_GLOW_GREEN;
            default -> TecTechUITextures.BACKGROUND_GLOW_BLUE;
        };
        new TextWidget();
        ModularWindow.Builder builder = ModularWindow.builder(200, 200).setBackground(background)
                .widget(ButtonWidget.closeWindowButton(true).setPos(185, 3))
                .widget(
                        new MultiChildWidget()
                                .addChild(
                                        new TextWidget(translateToLocal("fog.upgrade.text." + (currentUpgradeID)))
                                                .setTextAlignment(Alignment.Center).setMaxWidth(185)
                                                .setDefaultColor(0x9c9c9c).setPos(9, 35))
                                .addChild(
                                        new TextWidget(translateToLocal("fog.upgrade.lore." + (currentUpgradeID)))
                                                .setTextAlignment(Alignment.Center).setMaxWidth(185)
                                                .setDefaultColor(0x9c9c9c).setPos(9, 110))
                                .addChild(
                                        new TextWidget(
                                                translateToLocal("gt.blockmachines.multimachine.FOG.shardcost") + " "
                                                        + EnumChatFormatting.BLUE
                                                        + gravitonShardCost).setTextAlignment(Alignment.Center)
                                                                .setScale(0.7f).setMaxWidth(70)
                                                                .setDefaultColor(0x9c9c9c).setPos(7, 178))
                                .addChild(
                                        new TextWidget(
                                                translateToLocal("gt.blockmachines.multimachine.FOG.availableshards"))
                                                        .setTextAlignment(Alignment.Center).setScale(0.7f)
                                                        .setMaxWidth(90).setDefaultColor(0x9c9c9c).setPos(113, 178))
                                .addChild(
                                        TextWidget.dynamicText(this::gravitonShardAmount)
                                                .setTextAlignment(Alignment.Center).setScale(0.7f).setMaxWidth(90)
                                                .setDefaultColor(0x9c9c9c).setPos(173, 185)))
                .setSize(200, 200)

                .widget(new MultiChildWidget().addChild(new ButtonWidget().setOnClick((clickData, widget) -> {
                    int unlockedPrereqUpgrades = 0;
                    int unlockedFollowupUpgrades = 0;
                    int unlockedSplitUpgrades = 0;
                    if (!upgrades[currentUpgradeID]) {
                        for (int prereqUpgrade : prereqUpgrades) {
                            if (upgrades[prereqUpgrade]) {
                                unlockedPrereqUpgrades++;
                            }
                        }
                        if (allPrereqRequired) {
                            if (unlockedPrereqUpgrades == prereqUpgrades.length
                                    && gravitonShardsAvailable >= gravitonShardCost) {
                                gravitonShardsAvailable -= gravitonShardCost;
                                gravitonShardsSpent += gravitonShardCost;
                                upgrades[currentUpgradeID] = true;
                            }
                        } else if (unlockedPrereqUpgrades > 0 || prereqUpgrades.length == 0) {
                            if (isUpradeSplitStart) {
                                for (int splitUpgrade : FIRST_SPLIT_UPGRADES) {
                                    if (upgrades[splitUpgrade]) {
                                        unlockedSplitUpgrades++;
                                    }
                                }
                                unlockedSplitUpgrades -= (ringAmount - 1);
                            }
                            if (unlockedSplitUpgrades <= 0 && gravitonShardsAvailable >= gravitonShardCost) {
                                gravitonShardsAvailable -= gravitonShardCost;
                                gravitonShardsSpent += gravitonShardCost;
                                upgrades[currentUpgradeID] = true;
                            }
                        }
                    } else {
                        for (int followupUpgrade : followupUpgrades) {
                            if (upgrades[followupUpgrade]) {
                                unlockedFollowupUpgrades++;
                            }
                        }
                        if (unlockedFollowupUpgrades == 0) {
                            gravitonShardsAvailable += gravitonShardCost;
                            gravitonShardsSpent -= gravitonShardCost;
                            upgrades[currentUpgradeID] = false;
                        }
                    }
                }).setSize(40, 15).setBackground(() -> {
                    if (upgrades[currentUpgradeID]) {
                        return new IDrawable[] { GT_UITextures.BUTTON_STANDARD_PRESSED };
                    } else {
                        return new IDrawable[] { GT_UITextures.BUTTON_STANDARD };
                    }
                }).addTooltip(translateToLocal("fog.upgrade.confirm")).setTooltipShowUpDelay(TOOLTIP_DELAY))
                        .addChild(
                                new TextWidget(translateToLocal("fog.upgrade.confirm"))
                                        .setTextAlignment(Alignment.Center).setScale(0.7f).setMaxWidth(36).setPos(3, 5))
                        .setPos(79, 177));
        return builder.build();
    }

    /**
     * @param upgradeID               ID of the upgrade
     * @param colorCode               Number deciding which colored background to use, 0 for blue, 1 for purple, 2 for
     *                                orange and 3 for green
     * @param prerequisiteUpgradeIDs  IDs of the prior upgrades directly connected to the current one
     * @param requireAllPrerequisites Decides how many connected prerequisite upgrades have to be unlocked to be able to
     *                                unlock this one. True means ALL, False means AT LEAST ONE
     * @param followingUpgradeIDs     IDs of the following upgrades directly connected to the current one
     * @param isStartOfSplit          Whether this upgrade is one of the initial split upgrades
     * @param shardCost               How many graviton shards are needed to unlock this upgrade
     * @param pos                     Position of the upgrade inside the scrollableWidget
     */
    private Widget createUpgradeBox(int upgradeID, int colorCode, int[] prerequisiteUpgradeIDs,
            boolean requireAllPrerequisites, int[] followingUpgradeIDs, boolean isStartOfSplit, int shardCost,
            Pos2d pos, IWidgetBuilder<?> builder) {
        return new MultiChildWidget().addChild(new ButtonWidget().setOnClick((clickData, widget) -> {
            currentUpgradeID = upgradeID;
            currentColorCode = colorCode;
            gravitonShardCost = shardCost;
            prereqUpgrades = prerequisiteUpgradeIDs;
            allPrereqRequired = requireAllPrerequisites;
            followupUpgrades = followingUpgradeIDs;
            isUpradeSplitStart = isStartOfSplit;
            if (!widget.isClient()) widget.getContext().openSyncedWindow(INDIVIDUAL_UPGRADE_WINDOW_ID);
        }).setSize(40, 15).setBackground(() -> {
            if (upgrades[upgradeID]) {
                return new IDrawable[] { GT_UITextures.BUTTON_STANDARD_PRESSED };
            } else {
                return new IDrawable[] { GT_UITextures.BUTTON_STANDARD };
            }
        }).addTooltip(translateToLocal("fog.upgrade.tt." + upgradeID)).setTooltipShowUpDelay(TOOLTIP_DELAY))
                .addChild(
                        new TextWidget(translateToLocal("fog.upgrade.tt." + upgradeID))
                                .setTextAlignment(Alignment.Center).setScale(0.57f).setMaxWidth(36).setPos(3, 3))
                .setPos(pos).attachSyncer(
                        new FakeSyncWidget.BooleanSyncer(() -> upgrades[upgradeID], val -> upgrades[upgradeID] = val),
                        builder);
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Material Manipulator") // Machine Type:
                .addInfo("Controller block for the Forge of Gods") // Controller
                .addInfo("Uses a Star to to manipulate metals").addSeparator().beginStructureBlock(1, 4, 2, false)
                .addStructureInfo("Output bus/hatch has to be the ME variant")
                .addStructureInfo("Dot 2 of Input Hatch is the Fuel Input Hatch")
                .addInputHatch("Any Infinite Spacetime Casing", 1).addInputHatch("Any Infinite Spacetime Casing", 2) // Fuel
                                                                                                                     // Input
                                                                                                                     // Hatch
                .addInputBus("Any Infinite Spacetime Casing", 1).addOutputBus("Any Infinite Spacetime Casing", 1)
                .addOutputHatch("Any Infinite Spacetime Casing", 1).toolTipFinisher(CommonValues.GODFORGE_MARK);
        return tt;
    }

    @Override
    public boolean energyFlowOnRunningTick(ItemStack aStack, boolean allowProduction) {
        return true;
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return new String[] { "Forge of Gods multiblock" };
    }

    public int getFuelType() {
        return selectedFuelType;
    }

    private void setFuelType(int fuelType) {
        selectedFuelType = fuelType;
    }

    public int getFuelFactor() {
        return fuelConsumptionFactor;
    }

    public boolean isUpgradeActive(int upgradeID) {
        return upgrades[upgradeID];
    }

    public int getTotalActiveUpgrades() {
        int totalUpgrades = 0;
        for (boolean upgrade : upgrades) {
            if (upgrade) {
                totalUpgrades++;
            }
        }
        return totalUpgrades;
    }

    private Text fuelUsage() {
        return new Text(fuelConsumption + " L/5s");
    }

    private Text gravitonShardAmount() {
        EnumChatFormatting enoughGravitonShards = EnumChatFormatting.RED;
        if (gravitonShardsAvailable >= gravitonShardCost) {
            enoughGravitonShards = EnumChatFormatting.GREEN;
        }
        return new Text(enoughGravitonShards + Integer.toString(gravitonShardsAvailable));

    }

    private Text storedFuel() {
        return new Text(
                translateToLocal("gt.blockmachines.multimachine.FOG.storedfuel") + " "
                        + internalBattery
                        + "/"
                        + maxBatteryCharge);
    }

    private void determineMilestoneProgress() {
        if (milestoneProgress[0] < 7) {
            powerMilestonePercentage = (float) max(
                    (log((totalPowerConsumed.divide(BigInteger.valueOf(POWER_MILESTONE_CONSTANT))).longValue())
                            / POWER_LOG_CONSTANT + 1),
                    0) / 7;
            milestoneProgress[0] = (int) floor(powerMilestonePercentage * 7);
        } else {
            powerMilestonePercentage = 1;
        }
        if (milestoneProgress[1] < 7) {
            recipeMilestonePercentage = (float) max(
                    (log(totalRecipesProcessed * 1f / RECIPE_MILESTONE_CONSTANT) / RECIPE_LOG_CONSTANT + 1),
                    0) / 7;
            milestoneProgress[1] = (int) floor(recipeMilestonePercentage * 7);
        } else {
            recipeMilestonePercentage = 1;
        }
        if (milestoneProgress[2] < 7) {
            fuelMilestonePercentage = (float) max(
                    (log(totalFuelConsumed * 1f / FUEL_MILESTONE_CONSTANT) / FUEL_LOG_CONSTANT + 1),
                    0) / 7;
            milestoneProgress[2] = (int) floor(fuelMilestonePercentage * 7);
        } else {
            fuelMilestonePercentage = 1;
        }
    }

    private void determineGravitonShardAmount() {
        int sum = 0;
        for (int progress : milestoneProgress) {
            progress = Math.min(progress, 7);
            sum += progress * (progress + 1) / 2;
        }
        gravitonShardsAvailable = sum - gravitonShardsSpent;
    }

    private Text milestoneProgressText(int milestoneID, boolean formatting) {
        long min;
        long max;
        BigInteger bigMin;
        BigInteger bigMax;
        Text done = new Text(translateToLocal("gt.blockmachines.multimachine.FOG.milestonecomplete"));
        String suffix;
        switch (milestoneID) {
            case 1:
                if (milestoneProgress[0] < 7) {
                    suffix = "EU";
                    bigMin = totalPowerConsumed;
                    bigMax = BigInteger.valueOf(LongMath.pow(9, milestoneProgress[0] + 1))
                            .multiply(BigInteger.valueOf(LongMath.pow(10, 15)));
                    if (formatting && (bigMin.compareTo(BigInteger.valueOf(1_000L)) > 0)) {
                        return new Text(
                                translateToLocal("gt.blockmachines.multimachine.FOG.progress") + ": "
                                        + toExponentForm(bigMin)
                                        + "/"
                                        + toExponentForm(bigMax)
                                        + " "
                                        + suffix);
                    } else {
                        return new Text(
                                translateToLocal("gt.blockmachines.multimachine.FOG.progress") + ": "
                                        + bigMin
                                        + "/"
                                        + bigMax
                                        + " "
                                        + suffix);
                    }
                } else {
                    return done;
                }
            case 2:
                if (milestoneProgress[1] < 7) {
                    suffix = translateToLocal("gt.blockmachines.multimachine.FOG.recipes");
                    min = totalRecipesProcessed;
                    max = LongMath.pow(6, milestoneProgress[1] + 1) * LongMath.pow(10, 7);
                    break;
                } else {
                    return done;
                }
            case 3:
                if (milestoneProgress[2] < 7) {
                    suffix = translateToLocal("gt.blockmachines.multimachine.FOG.fuel");
                    min = totalFuelConsumed;
                    max = LongMath.pow(3, milestoneProgress[2] + 1) * LongMath.pow(10, 4);
                    break;
                } else {
                    return done;
                }
            case 4:
                if (milestoneProgress[3] < 7) {
                    suffix = translateToLocal("gt.blockmachines.multimachine.FOG.extensions");
                    min = milestoneProgress[3];
                    max = 7;
                    break;
                } else {
                    return done;
                }
            default:
                return new Text("Error");
        }
        if (formatting) {
            return new Text(
                    translateToLocal("gt.blockmachines.multimachine.FOG.progress") + ": "
                            + formatNumbers(min)
                            + "/"
                            + formatNumbers(max)
                            + " "
                            + suffix);
        } else {
            return new Text(
                    translateToLocal(
                            "gt.blockmachines.multimachine.FOG.progress") + ": " + min + "/" + max + " " + suffix);
        }
    }

    private void increaseBattery(int amount) {
        if ((internalBattery + amount) <= maxBatteryCharge) {
            internalBattery += amount;
        } else {
            batteryCharging = false;
        }
    }

    public void reduceBattery(int amount) {
        if (internalBattery - amount <= 0) {
            internalBattery = 0;
            if (moduleHatches.size() > 0) {
                for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                    module.disconnect();
                }
            }
        } else {
            internalBattery -= amount;
            totalFuelConsumed += amount;
        }

    }

    public int getBatteryCharge() {
        return internalBattery;
    }

    public int getMaxBatteryCharge() {
        return maxBatteryCharge;
    }

    public void addTotalPowerConsumed(BigInteger amount) {
        totalPowerConsumed = totalPowerConsumed.add(amount);
    }

    public void addTotalRecipesProcessed(long amount) {
        totalRecipesProcessed += amount;
    }

    @Override
    protected void setHatchRecipeMap(GT_MetaTileEntity_Hatch_Input hatch) {}

    @Override
    public void setItemNBT(NBTTagCompound NBT) {
        NBT.setInteger("selectedFuelType", selectedFuelType);
        NBT.setInteger("fuelConsumptionFactor", fuelConsumptionFactor);
        NBT.setInteger("internalBattery", internalBattery);
        NBT.setBoolean("batteryCharging", batteryCharging);
        NBT.setInteger("batterySize", maxBatteryCharge);
        NBT.setInteger("gravitonShardsAvailable", gravitonShardsAvailable);
        NBT.setInteger("gravitonShardsSpent", gravitonShardsSpent);
        NBT.setByteArray("totalPowerConsumed", totalPowerConsumed.toByteArray());
        NBT.setLong("totalRecipesProcessed", totalRecipesProcessed);
        NBT.setLong("totalFuelConsumed", totalFuelConsumed);

        // Store booleanArray of all upgrades
        NBTTagCompound upgradeBooleanArrayNBTTag = new NBTTagCompound();

        int upgradeIndex = 0;
        for (Boolean upgrade : upgrades) {
            upgradeBooleanArrayNBTTag.setBoolean("upgrade" + upgradeIndex, upgrade);
            upgradeIndex++;
        }

        NBT.setTag("upgrades", upgradeBooleanArrayNBTTag);
        super.saveNBTData(NBT);
    }

    @Override
    public void saveNBTData(NBTTagCompound NBT) {
        NBT.setInteger("selectedFuelType", selectedFuelType);
        NBT.setInteger("fuelConsumptionFactor", fuelConsumptionFactor);
        NBT.setInteger("internalBattery", internalBattery);
        NBT.setBoolean("batteryCharging", batteryCharging);
        NBT.setInteger("batterySize", maxBatteryCharge);
        NBT.setInteger("gravitonShardsAvailable", gravitonShardsAvailable);
        NBT.setInteger("gravitonShardsSpent", gravitonShardsSpent);
        NBT.setByteArray("totalPowerConsumed", totalPowerConsumed.toByteArray());
        NBT.setLong("totalRecipesProcessed", totalRecipesProcessed);
        NBT.setLong("totalFuelConsumed", totalFuelConsumed);

        // Store booleanArray of all upgrades
        NBTTagCompound upgradeBooleanArrayNBTTag = new NBTTagCompound();

        int upgradeIndex = 0;
        for (Boolean upgrade : upgrades) {
            upgradeBooleanArrayNBTTag.setBoolean("upgrade" + upgradeIndex, upgrade);
            upgradeIndex++;
        }

        NBT.setTag("upgrades", upgradeBooleanArrayNBTTag);
        super.saveNBTData(NBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound NBT) {
        selectedFuelType = NBT.getInteger("selectedFuelType");
        fuelConsumptionFactor = NBT.getInteger("fuelConsumptionFactor");
        internalBattery = NBT.getInteger("internalBattery");
        batteryCharging = NBT.getBoolean("batteryCharging");
        maxBatteryCharge = NBT.getInteger("batterySize");
        gravitonShardsAvailable = NBT.getInteger("gravitonShardsAvailable");
        gravitonShardsSpent = NBT.getInteger("gravitonShardsSpent");
        totalPowerConsumed = new BigInteger(NBT.getByteArray("totalPowerConsumed"));
        totalRecipesProcessed = NBT.getLong("totalRecipesProcessed");
        totalFuelConsumed = NBT.getLong("totalFuelConsumed");

        NBTTagCompound tempBooleanTag = NBT.getCompoundTag("upgrades");

        for (int upgradeIndex = 0; upgradeIndex < 31; upgradeIndex++) {
            boolean upgrade = tempBooleanTag.getBoolean("upgrade" + upgradeIndex);
            upgrades[upgradeIndex] = upgrade;
        }

        super.loadNBTData(NBT);
    }
}
