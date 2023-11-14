package com.github.technus.tectech.thing.metaTileEntity.hatch;

import static com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector.EM_D_SIDES;
import static com.github.technus.tectech.util.CommonValues.MOVE_AT;
import static gregtech.api.enums.Dyes.MACHINE_METAL;
import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.github.technus.tectech.mechanics.dataTransport.WirelessDataNetwork;
import com.github.technus.tectech.mechanics.dataTransport.WirelessInventoryDataPacket;
import com.github.technus.tectech.util.CommonValues;
import com.github.technus.tectech.util.TT_Utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DataAccess;
import gregtech.api.objects.GT_RenderedTexture;

public class GT_MetaTileEntity_Hatch_InputData_Wireless extends GT_MetaTileEntity_Hatch_DataAccess {

    private String owner_uuid;
    private String owner_name;
    public static Textures.BlockIcons.CustomIcon DATA_WIRELESS_SLAVE;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        super.registerIcons(aBlockIconRegister);
        DATA_WIRELESS_SLAVE = new Textures.BlockIcons.CustomIcon("iconsets/OVERLAY_DATA_WIRELESS_SLAVE");
    }

    private ItemStack[] stacks;
    private WirelessInventoryDataPacket inventoryDataPacket;

    public GT_MetaTileEntity_Hatch_InputData_Wireless(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
        TT_Utility.setTier(aTier, this);
    }

    public GT_MetaTileEntity_Hatch_InputData_Wireless(String aName, int aTier, String[] aDescription,
            ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture,
                new GT_RenderedTexture(
                        DATA_WIRELESS_SLAVE,
                        Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                new GT_RenderedTexture(DATA_WIRELESS_SLAVE) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture,
                new GT_RenderedTexture(
                        EM_D_SIDES,
                        Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                new GT_RenderedTexture(DATA_WIRELESS_SLAVE) };
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Hatch_InputData_Wireless(
                this.mName,
                this.mTier,
                mDescriptionArray,
                this.mTextures);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }
        return true;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
            ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
            ItemStack aStack) {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        return side == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public void onRemoval() {
        stacks = null;
    }

    @Override
    public int getSizeInventory() {
        return inventoryDataPacket != null ? inventoryDataPacket.getContent().length : 0;
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        return inventoryDataPacket != null && aIndex >= 0 && aIndex < inventoryDataPacket.getContent().length
                ? inventoryDataPacket.getContent()[aIndex]
                : null;
    }

    @Override
    public boolean shouldDropItemAt(int index) {
        return false;
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);
        if (MOVE_AT == aTick % 20) {
            // Retrieve data from the wireless network
            String networkId = getNetworkIdFromMetaTileEntity();
            if (networkId != null) {
                inventoryDataPacket = WirelessDataNetwork.retrieveData(networkId);
            }

            // Update the active state based on the retrieved data
            if (inventoryDataPacket == null || inventoryDataPacket.getContent().length == 0) {
                getBaseMetaTileEntity().setActive(false);
            } else {
                getBaseMetaTileEntity().setActive(true);
            }
        }
    }

    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        if (aBaseMetaTileEntity.isServerSide()) {
            // On first tick find the player name and attempt to add them to the map.

            // UUID and username of the owner.
            owner_uuid = aBaseMetaTileEntity.getOwnerUuid().toString();
            owner_name = aBaseMetaTileEntity.getOwnerName();
        }
    }

    private String getNetworkIdFromMetaTileEntity() {
        return owner_uuid + owner_name; // Placeholder
    }

    @Override
    public String[] getDescription() {
        return new String[] { CommonValues.TEC_MARK_EM,
                translateToLocal("gt.blockmachines.hatch.wireless.datain.desc.0"),
                translateToLocal("gt.blockmachines.hatch.wireless.datain.desc.1"),
                EnumChatFormatting.AQUA + translateToLocal("gt.blockmachines.hatch.wireless.datain.desc.2") };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[] { translateToLocalFormatted("tt.keyphrase.Content_Stack_Count") + ": "
                + (stacks == null ? 0 : stacks.length) };
    }

}
