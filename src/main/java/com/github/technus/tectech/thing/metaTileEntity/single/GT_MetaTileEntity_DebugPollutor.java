package com.github.technus.tectech.thing.metaTileEntity.single;

import static com.github.technus.tectech.thing.metaTileEntity.Textures.MACHINE_CASINGS_TT;
import static net.minecraft.util.StatCollector.translateToLocal;

import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_OverflowElemental;
import com.github.technus.tectech.thing.metaTileEntity.single.gui.GT_Container_DebugPollutor;
import com.github.technus.tectech.thing.metaTileEntity.single.gui.GT_GUIContainer_DebugPollutor;
import com.github.technus.tectech.util.CommonValues;
import com.github.technus.tectech.util.TT_Utility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.common.GT_Pollution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

/**
 * Created by Tec on 23.03.2017.
 */
public class GT_MetaTileEntity_DebugPollutor extends GT_MetaTileEntity_TieredMachineBlock {
    private static GT_RenderedTexture POLLUTOR;
    public int pollution = 0;
    public float anomaly = 0;

    public GT_MetaTileEntity_DebugPollutor(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "");
        TT_Utility.setTier(aTier, this);
    }

    public GT_MetaTileEntity_DebugPollutor(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
        TT_Utility.setTier(aTier, this);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_DebugPollutor(mName, mTier, mDescription, mTextures);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        super.registerIcons(aBlockIconRegister);
        POLLUTOR = new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("iconsets/POLLUTOR"));
    }

    @Override
    public ITexture[] getTexture(
            IGregTechTileEntity aBaseMetaTileEntity,
            byte aSide,
            byte aFacing,
            byte aColorIndex,
            boolean aActive,
            boolean aRedstone) {
        return new ITexture[] {
            MACHINE_CASINGS_TT[mTier][aColorIndex + 1],
            aSide != aFacing
                    ? aActive
                            ? new GT_RenderedTexture(GT_MetaTileEntity_Hatch_OverflowElemental.MufflerEM)
                            : new GT_RenderedTexture(GT_MetaTileEntity_Hatch_OverflowElemental.MufflerEMidle)
                    : POLLUTOR
        };
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return null;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_DebugPollutor(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_DebugPollutor(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity iGregTechTileEntity, int i, byte b, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity iGregTechTileEntity, int i, byte b, ItemStack itemStack) {
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("ePollution", pollution);
        aNBT.setFloat("eAnomaly", anomaly);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        pollution = aNBT.getInteger("ePollution");
        anomaly = aNBT.getFloat("eAnomaly");
        getBaseMetaTileEntity().setActive(anomaly > 0 || pollution > 0);
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            aBaseMetaTileEntity.setActive(anomaly > 0 || pollution > 0);
            if (anomaly > 0) {
                TecTech.anomalyHandler.addAnomaly(aBaseMetaTileEntity, anomaly);
            }
            if (pollution > 0) {
                GT_Pollution.addPollution(aBaseMetaTileEntity, pollution);
            }
        } else if (aBaseMetaTileEntity.isClientSide() && aBaseMetaTileEntity.isActive()) {
            for (byte i = 0; i < 6; i++) {
                if (i != aBaseMetaTileEntity.getFrontFacing()) {
                    TecTech.proxy.em_particle(aBaseMetaTileEntity, i);
                    TecTech.proxy.pollutor_particle(aBaseMetaTileEntity, i);
                }
            }
        }
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public String[] getDescription() {
        return new String[] {
            CommonValues.TEC_MARK_GENERAL,
            translateToLocal("gt.blockmachines.debug.tt.pollutor.desc.0"), // Shit genny broke!
            EnumChatFormatting.BLUE
                    + translateToLocal("gt.blockmachines.debug.tt.pollutor.desc.1"), // Infinite Producer/Consumer
            EnumChatFormatting.BLUE
                    + translateToLocal("gt.blockmachines.debug.tt.pollutor.desc.2") // Since i wanted one?
        };
    }

    @Override
    public boolean isElectric() {
        return false;
    }
}
