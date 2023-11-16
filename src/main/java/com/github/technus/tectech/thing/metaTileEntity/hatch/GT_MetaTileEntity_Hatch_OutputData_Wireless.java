package com.github.technus.tectech.thing.metaTileEntity.hatch;

import static gregtech.api.enums.Dyes.MACHINE_METAL;
import static net.minecraft.util.StatCollector.translateToLocal;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.github.technus.tectech.mechanics.dataTransport.WirelessDataNetwork;
import com.github.technus.tectech.mechanics.dataTransport.WirelessInventoryDataPacket;
import com.github.technus.tectech.mechanics.pipe.IConnectsToDataPipe;
import com.github.technus.tectech.util.CommonValues;
import com.github.technus.tectech.util.TT_Utility;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;

public class GT_MetaTileEntity_Hatch_OutputData_Wireless
        extends GT_MetaTileEntity_Hatch_DataConnector<WirelessInventoryDataPacket> {

    public static Textures.BlockIcons.CustomIcon DATA_WIRELESS_SLAVE;

    public void registerIcons(IIconRegister aBlockIconRegister) {
        super.registerIcons(aBlockIconRegister);
        DATA_WIRELESS_SLAVE = new Textures.BlockIcons.CustomIcon("iconsets/OVERLAY_DATA_WIRELESS_SLAVE");
    }

    private String owner_uuid;

    public GT_MetaTileEntity_Hatch_OutputData_Wireless(int aID, String aName, String aNameRegional, int aTier) {
        super(
                aID,
                aName,
                aNameRegional,
                aTier,
                new String[] { CommonValues.TEC_MARK_EM,
                        translateToLocal("gt.blockmachines.hatch.wireless.dataout.desc.0"),
                        translateToLocal("gt.blockmachines.hatch.wireless.dataout.desc.1"),
                        EnumChatFormatting.AQUA + translateToLocal("gt.blockmachines.hatch.wireless.dataout.desc.2") });
        TT_Utility.setTier(aTier, this);
    }

    public GT_MetaTileEntity_Hatch_OutputData_Wireless(String aName, int aTier, String[] aDescription,
            ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
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
        return new GT_MetaTileEntity_Hatch_OutputData_Wireless(
                this.mName,
                this.mTier,
                this.mDescriptionArray,
                this.mTextures);
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
        return side == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        return false;
    }

    @Override
    protected WirelessInventoryDataPacket loadPacketFromNBT(NBTTagCompound nbt) {
        return new WirelessInventoryDataPacket(nbt);
    }

    @Override
    public boolean isDataInputFacing(ForgeDirection side) {
        return isInputFacing(side);
    }

    @Override
    public boolean canConnectData(ForgeDirection side) {
        return isOutputFacing(side);
    }

    @Override
    public IConnectsToDataPipe getNext(IConnectsToDataPipe source) {
        return null;
    }

    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        if (aBaseMetaTileEntity.isServerSide()) {
            // On first tick find the player name and attempt to add them to the map.

            // UUID and username of the owner.
            owner_uuid = aBaseMetaTileEntity.getOwnerUuid().toString();
        }
    }

    @Override
    public void moveAround(IGregTechTileEntity aBaseMetaTileEntity) {
        // Implementation for wireless transmission
        // Get the network ID from the meta tile entity
        String networkId = getNetworkIdFromMetaTileEntity();

        // Transmit data to the wireless network
        if (networkId != null) {
            // Store the data in the network
            WirelessDataNetwork.storeData(networkId, new WirelessInventoryDataPacket(q.getContent()));
        }

        q = null;
    }

    private String getNetworkIdFromMetaTileEntity() {
        return owner_uuid; // Placeholder
    }
}
