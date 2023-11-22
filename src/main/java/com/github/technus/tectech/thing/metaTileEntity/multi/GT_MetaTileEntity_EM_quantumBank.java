package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.textureOffset;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;
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
import com.github.technus.tectech.util.SyncArrayListPacket;
import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.math.CrossAxisAlignment;
import com.gtnewhorizons.modularui.api.math.MainAxisAlignment;
import com.gtnewhorizons.modularui.common.widget.Row;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
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


import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GT_MetaTileEntity_EM_quantumBank extends GT_MetaTileEntity_MultiblockBase_EM
        implements ISurvivalConstructable {

    //Workaround to stupid superclass having 3 slots.
    ItemStack[] inventoryStorageArray = new ItemStack[600];
    ItemStackHandler inventoryStorageHandler = new ItemStackHandler(inventoryStorageArray);
    ItemStackHandler phantomStorageHandler = new ItemStackHandler(600);
    private int numberOfUnlockedSlots = 144; //Minimum quantity possible
    private int usedSlotCount = 0; //For GUI to show total used so far
    private int storageTier = -1; //funny metaData

    private ArrayList<String> friendsList = new ArrayList();
    protected static int FRIENDSLIST_WINDOW_ID = 592;
    private String owner_name;



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
        numberOfUnlockedSlots = 144 + (storageTier * 54);
        return true;
    }

    public void saveNBTData(NBTTagCompound aNBT){
        super.saveNBTData(aNBT);
        aNBT.setInteger("numberOfUnlockedSlots", numberOfUnlockedSlots);
        aNBT.setInteger("friendsListSize", friendsList.size());
        for (int i = 0; i < friendsList.size(); i++){
            if (friendsList.get(i) == null) {break;}
            aNBT.setString("friendsList."+ i, friendsList.get(i));
        }
        for (int i = 0; i < numberOfUnlockedSlots; i++) {
            if(inventoryStorageArray[i] == null) {break;} //stop us from wasting CPU time looping through nothing
                aNBT.setTag("inventoryStorageHandler."+ i, writeItemStackToNBT(inventoryStorageArray[i]));
        }

    }
    public void loadNBTData(NBTTagCompound aNBT){
        super.loadNBTData(aNBT);
        numberOfUnlockedSlots = aNBT.getInteger("numberOfUnlockedSlots");
        for (int i = 0; i < aNBT.getInteger("friendsListSize"); i++){
            if (!friendsList.contains(aNBT.getString("friendsList."+i))){
                friendsList.add(i, aNBT.getString("friendsList."+i));
            }
        }
        for (int i = 0; i < inventoryStorageArray.length; i++){
            if (readItemStackFromNBT(aNBT.getCompoundTag("inventoryStorageHandler."+i)) == null) {break;} //stop us from wasting CPU time looping through nothing
            inventoryStorageArray[i] = readItemStackFromNBT(aNBT.getCompoundTag("inventoryStorageHandler."+i));
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
            owner_name = aBaseMetaTileEntity.getOwnerName();
            if (!friendsList.contains(owner_name)) {
                friendsList.add(0, owner_name);
            }
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
            usedSlotCount = getUsedSlotCount();
        }
    }
    private int getUsedSlotCount(){
        int slotCounter = 0;
        for (int i = 0; i < numberOfUnlockedSlots; i++) {
            if(inventoryStorageArray[i] == null) {break;} //stop us from wasting CPU time looping through nothing
            slotCounter++;
        }
        return slotCounter;
    }
    @Override
    public void updateSlots() {
        for (int i = 0; i < numberOfUnlockedSlots; i++)
            if (inventoryStorageArray[i] != null && inventoryStorageArray[i].stackSize <= 0) inventoryStorageArray[i] = null;
        fillStacksIntoFirstSlots();
    }
    /*
    We currently have to iterate through ALL slots in case someone places an item far down.
    Might try to find more optimized way later
     */
    protected void fillStacksIntoFirstSlots() {
        final int L = inventoryStorageArray.length;
        HashMap<GT_Utility.ItemId, Integer> slots = new HashMap<>(L);
        HashMap<GT_Utility.ItemId, ItemStack> stacks = new HashMap<>(L);
        List<GT_Utility.ItemId> order = new ArrayList<>(L);
        List<Integer> validSlots = new ArrayList<>(L);
        for (int i = 0; i < L; i++) {
            validSlots.add(i);
            ItemStack s = inventoryStorageArray[i];
            if (s == null) continue;
            GT_Utility.ItemId sID = GT_Utility.ItemId.createNoCopy(s);
            slots.merge(sID, s.stackSize, Integer::sum);
            if (!stacks.containsKey(sID)) stacks.put(sID, s);
            order.add(sID);
            inventoryStorageArray[i] = null;
        }
        int slotindex = 0;
        for (GT_Utility.ItemId sID : order) {
            int toSet = slots.get(sID);
            if (toSet == 0) continue;
            int slot = validSlots.get(slotindex);
            slotindex++;
            inventoryStorageArray[slot] = stacks.get(sID)
                    .copy();
            toSet = Math.min(toSet, inventoryStorageArray[slot].getMaxStackSize());
            inventoryStorageArray[slot].stackSize = toSet;
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
        ModularWindow.Builder currentWindow = ModularWindow.builder(158, 180);



        currentWindow.setBackground(TecTechUITextures.BACKGROUND_SCREEN_BLUE);
        //TO-DO
        /*
        -Network friendsList to other players so everyone sees the same thing
        -Proper spacing without SPACE_BETWEEN /0 bullshit
        -Only allow owner to add/remove friends
        -Probably networking friendsList back to server, who knows
        */

        ArrayList<String> activePlayerList = new ArrayList<>();

        currentWindow.widget(new FakeSyncWidget.StringSyncer(() -> owner_name, val -> owner_name = val));
        System.out.println("OWNER_NAME:"+owner_name);
        for (EntityPlayer playerEntity : player.getEntityWorld().playerEntities) {
            if (playerEntity.getDisplayName().equals(owner_name) && !friendsList.contains(owner_name)){
                friendsList.add(playerEntity.getDisplayName());
                System.out.println("Inside activePlayerList check, added owner");
            } else if (!friendsList.contains(playerEntity.getDisplayName())) {
                activePlayerList.add(playerEntity.getDisplayName());
            }
        }
        if(getBaseMetaTileEntity().isServerSide()){
            if (player instanceof EntityPlayerMP) {
                sendArrayListToClient(friendsList, (EntityPlayerMP) player);
               // System.out.println("SERVER: sendArrayListToClient-Length: " + friendsList.size());
            }
        }
        DynamicPositionedColumn friendsContainer = new DynamicPositionedColumn();

        friendsContainer.widget(new TextWidget("Friends List").setTextAlignment(Alignment.TopCenter).setDefaultColor(COLOR_TEXT_WHITE.get()));

        for (String friend : friendsList){
            System.out.println("We are inside window, friend:friendsList");
            System.out.println(friend + ":" + friendsList.get(0));
            //Sync our friendsList from client -> Server
            if (friend == friendsList.get(0)){ //We know owner is always index 0
                friendsContainer.widget(new Row().setAlignment(MainAxisAlignment.SPACE_BETWEEN, CrossAxisAlignment.CENTER)
                        .widget(new TextWidget("§9"+friend + " (owner)"))
                        .widget(new TextWidget(" ")) //Space_Between can div by zero without a spare 2nd element
                        .setMaxWidth(142));
            } else  {
                friendsContainer.widget(new Row().setAlignment(MainAxisAlignment.SPACE_BETWEEN, CrossAxisAlignment.CENTER)
                        .widget(new TextWidget("§9"+friend))
                        .widget(new TextWidget(" ")) //Space_Between can div by zero without a spare 2nd element
                        .widget(new ButtonWidget().setOnClick(((clickData,widget) -> {
                            friendsList.remove(friend);
                            sendArrayListToServer(friendsList);
                        }))
                                .setBackground(new Text("§c-"))
                                .setSize(8,8)).setMaxWidth(142));
            }

        }

        friendsContainer.widget(new TextWidget("Active Players").setTextAlignment(Alignment.TopCenter).setDefaultColor(COLOR_TEXT_WHITE.get()));

        for (String activePlayer : activePlayerList) {
                if(friendsList.contains(activePlayer)){
                    activePlayerList.remove(activePlayer);
                }


            friendsContainer.widget(new Row().setAlignment(MainAxisAlignment.SPACE_BETWEEN, CrossAxisAlignment.CENTER)
                    .widget(new TextWidget(activePlayer))
                    .widget(new TextWidget(" ")) //Space_Between can div by zero without a spare 2nd element
                    .widget(new ButtonWidget().setOnClick(((clickData, widget) -> {
                        if (!friendsList.contains(activePlayer)) {friendsList.add(activePlayer);}
                        sendArrayListToServer(friendsList);
                    }))
                            .setBackground(new Text("§a+"))
                            .setSize(8, 8)).setMaxWidth(142));
        }



        currentWindow.widget(friendsContainer.setPos(7, 8));
        return currentWindow.build();
    }


    protected ButtonWidget createFriendsButton() {

        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
                    TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                    if (!widget.isClient()) {
                        widget.getContext().openSyncedWindow(FRIENDSLIST_WINDOW_ID);
                        sendArrayListToClient(friendsList, (EntityPlayerMP) widget.getContext().getPlayer());
                        System.out.println("createFriendsButton CALLED -> sendArrayListToClient");
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
        screenElements.widget(new FakeSyncWidget.IntegerSyncer(() -> usedSlotCount, val -> usedSlotCount = val)); // Sync our values
        screenElements.widget(
                TextWidget.dynamicString(() -> ("Current Capacity: " + usedSlotCount + "/" + numberOfUnlockedSlots))
                        .setSynced(false)
                        .setTextAlignment(Alignment.CenterLeft)
                        .setDefaultColor(COLOR_TEXT_WHITE.get())
                        .setEnabled(true));
        return screenElements;
    }
    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        final DynamicPositionedColumn textContainer = new DynamicPositionedColumn();

        builder.widget(new FakeSyncWidget.IntegerSyncer(() -> numberOfUnlockedSlots, val -> numberOfUnlockedSlots = val)); // Sync our value


        builder.setSize(new Size(200, 265));
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 4)
                        .setSize(190, 91));
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 98)
                        .setSize(190, 81));
        drawTexts(textContainer);
        builder.widget(textContainer.setPos(7, 8));

        Widget powerSwitchButton = createPowerSwitchButton();
        builder.widget(powerSwitchButton)
                .widget(new FakeSyncWidget.BooleanSyncer(() -> isAllowedToWork(), val -> {
                    if (val) enableWorking();
                    else disableWorking();
                }));

        final Scrollable inventoryScrollable = new Scrollable().setVerticalScroll();
        for (int row = 0; row * 10 < numberOfUnlockedSlots - 1; row++) {
            int columnsToMake = Math.min(numberOfUnlockedSlots - row * 10, 10);
            for (int column = 0; column < columnsToMake; column++) {
                inventoryScrollable.widget(
                        new SlotWidget(inventoryStorageHandler, row * 10 + column).setPos(column * 18, row * 18).setSize(18, 18));
            }
        }

        builder.widget(inventoryScrollable.setSize(18 * 10 + 4, 18 * 4).setPos(7, 103));

        builder.widget(createFriendsButton());
        buildContext.addSyncedWindow(FRIENDSLIST_WINDOW_ID, this::createFriendsConfigWindow);
    }

    public ArrayList<String> getFriendsList() {
        return friendsList;
    }
    public void setFriendsList(ArrayList<String> arrayList) {
        friendsList = arrayList;
        System.out.println("CLIENT: setFriendsList -> "+ friendsList.get(0));
    }

    public void sendArrayListToServer(ArrayList<String> arrayList){
        SimpleNetworkWrapper networkWrapper = TecTech.networkWrapper;
        SyncArrayListPacket packet = new SyncArrayListPacket(arrayList, this);
        networkWrapper.sendToServer(packet);
    }

    public void sendArrayListToClient(ArrayList<String> arrayList, EntityPlayerMP entityPlayerMP){
        SimpleNetworkWrapper networkWrapper = TecTech.networkWrapper;
        SyncArrayListPacket packet = new SyncArrayListPacket(arrayList, this);
        networkWrapper.sendTo(packet, entityPlayerMP);
    }
}
