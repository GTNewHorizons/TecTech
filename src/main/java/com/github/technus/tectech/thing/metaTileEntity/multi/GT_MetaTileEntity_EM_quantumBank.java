package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.recipe.TT_recipeAdder.nullItem;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.textureOffset;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;
import static com.github.technus.tectech.util.CommonValues.V;
import static com.github.technus.tectech.util.TT_Utility.readItemStackFromNBT;
import static com.github.technus.tectech.util.TT_Utility.writeItemStackToNBT;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlocksTiered;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockUnlocalizedName;

import static gregtech.api.enums.GT_HatchElement.InputHatch;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static gregtech.api.util.GT_StructureUtility.ofFrame;

import com.github.technus.tectech.thing.casing.TT_Container_Casings;
import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.common.internal.wrapper.BaseSlot;
import gregtech.api.util.GT_Utility;
import static net.minecraft.util.StatCollector.translateToLocal;


import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.thing.gui.TecTechUITextures;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.forge.ItemStackHandler;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.api.widget.Widget;
import com.gtnewhorizons.modularui.common.widget.ButtonWidget;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.Scrollable;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import gregtech.common.gui.modularui.widget.CheckRecipeResultSyncer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;


import com.github.technus.tectech.Reference;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;

import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GT_MetaTileEntity_EM_quantumBank extends GT_MetaTileEntity_MultiblockBase_EM
        implements ISurvivalConstructable {

    //Workaround to stupid superclass having 3 slots.
    ItemStack[] invStorage = new ItemStack[600];
    ItemStackHandler invHandler = new ItemStackHandler(invStorage);
    private int unlockedSlots = 144; //Minimum quantity possible
    private int slotCount = 0; //For GUI to show total used so far
    private int storageTier = -1; //funny metaData

    private ArrayList<String> friendsList = new ArrayList();
    protected static int FRIENDSLIST_WINDOW_ID = 592;



    // region structure
    private static final String[] description = new String[] {
            EnumChatFormatting.AQUA + translateToLocal("tt.keyphrase.Hint_Details") + ":",
            translateToLocal("gt.blockmachines.multimachine.em.quantumbank.hint.0"),
    };

    private static final IStructureDefinition<GT_MetaTileEntity_EM_quantumBank> STRUCTURE_DEFINITION = IStructureDefinition
            .<GT_MetaTileEntity_EM_quantumBank>builder()
            .addShape(
                    "main",
                    transpose(
                            new String[][]{
                                    {"DGGGGGGGD","DE     ED","DE     ED","DE     ED","DGGGGGGGD"},
                                    {"DE     ED","DA     AD","DABFFFBAD","DA     AD","DE     ED"},
                                    {"D~     ED","DABFFFBAD","DACCCCCAD","DABFFFBAD","DE     ED"},
                                    {"DE     ED","DA     AD","DABFFFBAD","DA     AD","DE     ED"},
                                    {"DGGGGGGGD","DE     ED","DE     ED","DE     ED","DGGGGGGGD"}
                            }))
            .addElement('A', ofBlock(GregTech_API.sBlockCasings1, 12))
            .addElement('B', ofBlock(GregTech_API.sBlockCasings1, 13))
            .addElement(
                    'C',
                    ofBlocksTiered(
                            (block, meta) -> block == TT_Container_Casings.SpacetimeCompressionFieldGenerators ? meta
                                    : null,
                            ImmutableList.of(
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 0),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 1),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 2),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 3),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 4),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 5),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 6),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 7),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 8)),
                            -1,
                            (t, meta) -> t.storageTier = meta,
                            t -> t.storageTier))
            .addElement('D', ofBlock(sBlockCasingsTT, 2))
            .addElement('F', ofBlockUnlocalizedName("tectech","tile.quantumGlass",0))
            .addElement('G', ofFrame(MaterialsUEVplus.TranscendentMetal))
            .addElement('E',
                    buildHatchAdder(GT_MetaTileEntity_EM_quantumBank.class)
                            .atLeast(GT_MetaTileEntity_EM_quantumBank.HatchElement.InputData,InputHatch)
                            .casingIndex(textureOffset + 3).dot(1).buildAndChain(ofBlock(sBlockCasingsTT, 3)))
            .build();
    // endregion

    public GT_MetaTileEntity_EM_quantumBank(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        turnOffMaintenance();
    }

    public GT_MetaTileEntity_EM_quantumBank(String aName) {
        super(aName);
        turnOffMaintenance();
    }
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_quantumBank(mName);
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        storageTier = -1;

        if (!structureCheck_EM("main", 1, 2, 0)){
            return false;
        }
        unlockedSlots = 144 + (storageTier * 54);
        return true;
    }

    public void saveNBTData(NBTTagCompound aNBT){
        super.saveNBTData(aNBT);
        for (int i = 0; i < unlockedSlots; i++) {
            if(invStorage[i] == null) {break;} //stop us from wasting CPU time looping through nothing
                aNBT.setTag("invHandler."+ i, writeItemStackToNBT(invStorage[i]));
        }
    }
    public void loadNBTData(NBTTagCompound aNBT){
        super.loadNBTData(aNBT);
        for (int i = 0; i < invStorage.length; i++){
            if (readItemStackFromNBT(aNBT.getCompoundTag("invHandler."+i)) == null) {break;} //stop us from wasting CPU time looping through nothing
            invStorage[i] = readItemStackFromNBT(aNBT.getCompoundTag("invHandler."+i));
        }
    }
    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.name"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.0"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.1"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.2"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.3"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.4"))
                .addSeparator().beginStructureBlock(9, 5, 5, false)
                .addOtherStructurePart(
                        translateToLocal("gt.blockmachines.hatch.datain.tier.07.name"),
                        translateToLocal("tt.keyword.Structure.AnyHighPowerCasing1D"), 1) // Data Bank Slave Connector: Any Computer Casing
                .addInputHatch("Any High Power Casing with 1 dot",1) //Unsure why it won't use lang, maybe wrong func call?
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }




    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
                                 int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.casingTexturePages[texturePage][3],
                    new TT_RenderedExtendedFacingTexture(
                            aActive ? GT_MetaTileEntity_MultiblockBase_EM.ScreenON
                                    : GT_MetaTileEntity_MultiblockBase_EM.ScreenOFF) };
        }
        return new ITexture[] { Textures.BlockIcons.casingTexturePages[texturePage][3] };
    }

    public static final ResourceLocation activitySound = new ResourceLocation(Reference.MODID + ":fx_hi_freq");

    @Override
    @SideOnly(Side.CLIENT)
    protected ResourceLocation getActivitySound() {
        return activitySound;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM("main", 1, 2, 0, stackSize, hintsOnly);
    }
    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, IItemSource source, EntityPlayerMP actor) {
        if (mMachine) return -1;
        return survivialBuildPiece("main", stackSize, 1, 2, 0, elementBudget, source, actor, false, true);
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_EM_quantumBank> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return description;
    }

    @Override
    public boolean isPowerPassButtonEnabled() {
        return false;
    }

    @Override
    public boolean isSafeVoidButtonEnabled() {
        return false;
    }

    @Override
    public boolean isAllowedToWorkButtonEnabled() {
        return true;
    }
    @Override
    @NotNull
    protected CheckRecipeResult checkProcessing_EM() {
        mEUt = 0;
        mMaxProgresstime = 20;
        mEfficiencyIncrease = 10000;
        return SimpleCheckRecipeResult.ofSuccess("providing_data");
    }

    public void onFirstTick_EM(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick_EM(aBaseMetaTileEntity);
        if (aBaseMetaTileEntity.isServerSide()){
            if (!hasMaintenanceChecks) turnOffMaintenance();
            if (!mMachine) {
                aBaseMetaTileEntity.disableWorking();
            }
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.hasInventoryBeenModified()){
            updateSlots();
            slotCount = getUsedSlotCount();
        }
    }
    private int getUsedSlotCount(){
        int slotCounter = 0;
        for (int i = 0; i < unlockedSlots; i++) {
            if(invStorage[i] == null) {break;} //stop us from wasting CPU time looping through nothing
            slotCounter++;
        }
        return slotCounter;
    }

    public void updateSlots() {
        for (int i = 0; i < unlockedSlots; i++)
            if (invStorage[i] != null && invStorage[i].stackSize <= 0) invStorage[i] = null;
        fillStacksIntoFirstSlots();
    }
    /*
    We currently have to iterate through ALL slots in case someone places an item far down.
    Might try to find more optimized way later
     */
    protected void fillStacksIntoFirstSlots() {
        final int L = invStorage.length;
        HashMap<GT_Utility.ItemId, Integer> slots = new HashMap<>(L);
        HashMap<GT_Utility.ItemId, ItemStack> stacks = new HashMap<>(L);
        List<GT_Utility.ItemId> order = new ArrayList<>(L);
        List<Integer> validSlots = new ArrayList<>(L);
        for (int i = 0; i < L; i++) {
            validSlots.add(i);
            ItemStack s = invStorage[i];
            if (s == null) continue;
            GT_Utility.ItemId sID = GT_Utility.ItemId.createNoCopy(s);
            slots.merge(sID, s.stackSize, Integer::sum);
            if (!stacks.containsKey(sID)) stacks.put(sID, s);
            order.add(sID);
            invStorage[i] = null;
        }
        int slotindex = 0;
        for (GT_Utility.ItemId sID : order) {
            int toSet = slots.get(sID);
            if (toSet == 0) continue;
            int slot = validSlots.get(slotindex);
            slotindex++;
            invStorage[slot] = stacks.get(sID)
                    .copy();
            toSet = Math.min(toSet, invStorage[slot].getMaxStackSize());
            invStorage[slot].stackSize = toSet;
            slots.merge(sID, toSet, (a, b) -> a - b);
        }
    }

    @Override
    public void addGregTechLogo(ModularWindow.Builder builder) { //Moving this stupid ass logo in the way
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_TECTECH_LOGO_DARK).setSize(18, 18)
                        .setPos(173, 184));
    }

    @Override
    protected ButtonWidget createPowerSwitchButton() { //We have to override this because .setPos on our inherited clashes since its predefined :fail:
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
            if (isAllowedToWorkButtonEnabled()) {
                TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                if (getBaseMetaTileEntity().isAllowedToWork()) {
                    getBaseMetaTileEntity().disableWorking();
                } else {
                    getBaseMetaTileEntity().enableWorking();
                }
            }
        }).setPlayClickSound(false).setBackground(() -> {
            List<UITexture> ret = new ArrayList<>();
            ret.add(TecTechUITextures.BUTTON_STANDARD_16x16);
            if (!isAllowedToWorkButtonEnabled()) {
                ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_DISABLED);
            } else {
                if (getBaseMetaTileEntity().isAllowedToWork()) {
                    ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_ON);
                } else {
                    ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_OFF);
                }
            }
            return ret.toArray(new IDrawable[0]);
        }).setPos(174,242).setSize(16, 16);
        if (isAllowedToWorkButtonEnabled()) {
            button.addTooltip("Power Switch").setTooltipShowUpDelay(TOOLTIP_DELAY);
        }
        return (ButtonWidget) button;
    }

    protected ModularWindow createFriendsConfigWindow(final EntityPlayer player){
        return ModularWindow.builder(158, 180)
                .setBackground(TecTechUITextures.BACKGROUND_SCREEN_BLUE)

                .build();
    }

    protected ButtonWidget createFriendsButton() {
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
                    TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                    if (!widget.isClient()) {
                        widget.getContext().openSyncedWindow(FRIENDSLIST_WINDOW_ID);
                    }
                }).setPlayClickSound(false)
                .setBackground(TecTechUITextures.BUTTON_STANDARD_16x16, TecTechUITextures.OVERLAY_BUTTON_FRIEND_MENU)
                .setPos(174, 221).setSize(16, 16);
        button.addTooltip("Add/Remove Friends").setTooltipShowUpDelay(TOOLTIP_DELAY);
        return (ButtonWidget) button;
    }

    @Override
    public void bindPlayerInventoryUI(ModularWindow.Builder builder, UIBuildContext buildContext) {
        builder.bindPlayerInventory(
                buildContext.getPlayer(),
                new Pos2d(7, 183), //Inventory POS (PLAYERS)
                this.getGUITextureSet()
                        .getItemSlot());
    }
    private DynamicPositionedColumn drawTexts(DynamicPositionedColumn screenElements){
        super.drawTexts(screenElements, null);
        screenElements.setSynced(false);
        screenElements.widget(new FakeSyncWidget.IntegerSyncer(() -> slotCount, val -> slotCount = val)); // Sync our values
        screenElements.widget(
                TextWidget.dynamicString(() -> ("Current Capacity: " + slotCount + "/" + unlockedSlots))
                        .setSynced(false)
                        .setTextAlignment(Alignment.CenterLeft)
                        .setDefaultColor(COLOR_TEXT_WHITE.get())
                        .setEnabled(true));
        return screenElements;
    }
    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        final DynamicPositionedColumn screenElements = new DynamicPositionedColumn();

        builder.widget(new FakeSyncWidget.IntegerSyncer(() -> unlockedSlots, val -> unlockedSlots = val)); // Sync our value


        builder.setSize(new Size(200, 265));
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 4)
                        .setSize(190, 91));
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 98)
                        .setSize(190, 81));
        drawTexts(screenElements);
        builder.widget(screenElements.setPos(7, 8));

        Widget powerSwitchButton = createPowerSwitchButton();
        builder.widget(powerSwitchButton)
                .widget(new FakeSyncWidget.BooleanSyncer(() -> isAllowedToWork(), val -> {
                    if (val) enableWorking();
                    else disableWorking();
                }));

        final Scrollable scrollable = new Scrollable().setVerticalScroll();
        for (int row = 0; row * 10 < unlockedSlots - 1; row++) {
            int columnsToMake = Math.min(unlockedSlots - row * 10, 10);
            for (int column = 0; column < columnsToMake; column++) {
                scrollable.widget(
                        new SlotWidget(invHandler, row * 10 + column).setPos(column * 18, row * 18)
                                .setSize(18, 18));
            }
        }

        builder.widget(scrollable.setSize(18 * 10 + 4, 18 * 4).setPos(7, 103));

        builder.widget(createFriendsButton());
        buildContext.addSyncedWindow(FRIENDSLIST_WINDOW_ID, this::createFriendsConfigWindow);

    }
}


