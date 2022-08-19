package com.github.technus.tectech.thing.metaTileEntity.single;

import static com.github.technus.tectech.thing.metaTileEntity.Textures.MACHINE_CASINGS_TT;
import static net.minecraft.util.StatCollector.translateToLocal;

import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.thing.metaTileEntity.single.gui.GT_Container_DebugStructureWriter;
import com.github.technus.tectech.thing.metaTileEntity.single.gui.GT_GUIContainer_DebugStructureWriter;
import com.github.technus.tectech.util.CommonValues;
import com.github.technus.tectech.util.TT_Utility;
import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.structure.StructureUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Tec on 23.03.2017.
 */
public class GT_MetaTileEntity_DebugStructureWriter extends GT_MetaTileEntity_TieredMachineBlock {
    private static GT_RenderedTexture MARK;
    public short[] numbers = new short[6];
    public boolean size = false;
    public String[] result = new String[] {"Undefined"};

    public GT_MetaTileEntity_DebugStructureWriter(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "");
        TT_Utility.setTier(aTier, this);
    }

    public GT_MetaTileEntity_DebugStructureWriter(
            String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
        TT_Utility.setTier(aTier, this);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_DebugStructureWriter(mName, mTier, mDescription, mTextures);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        super.registerIcons(aBlockIconRegister);
        MARK = new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("iconsets/MARK"));
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
            aSide != aFacing ? new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER_ACTIVE) : MARK
        };
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return null;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_DebugStructureWriter(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_DebugStructureWriter(aPlayerInventory, aBaseMetaTileEntity);
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
        for (int i = 0; i < numbers.length; i++) {
            aNBT.setShort("eData" + i, numbers[i]);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = aNBT.getShort("eData" + i);
        }
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        aBaseMetaTileEntity.disableWorking();
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isAllowedToWork()) {
            //            String pseudoJavaCode = StructureUtility.getPseudoJavaCode(aBaseMetaTileEntity.getWorld(),
            //
            // ExtendedFacing.of(ForgeDirection.getOrientation(aBaseMetaTileEntity.getFrontFacing())),
            //                    aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(),
            // aBaseMetaTileEntity.getZCoord(),
            //                    numbers[0], numbers[1], numbers[2],
            //                    numbers[3], numbers[4], numbers[5],false);
            String pseudoJavaCode = StructureUtility.getPseudoJavaCode(
                    aBaseMetaTileEntity.getWorld(),
                    ExtendedFacing.of(ForgeDirection.getOrientation(aBaseMetaTileEntity.getFrontFacing())),
                    aBaseMetaTileEntity.getXCoord(),
                    aBaseMetaTileEntity.getYCoord(),
                    aBaseMetaTileEntity.getZCoord(),
                    numbers[0],
                    numbers[1],
                    numbers[2],
                    te -> te.getClass().getCanonicalName(),
                    numbers[3],
                    numbers[4],
                    numbers[5],
                    false);
            TecTech.LOGGER.info(pseudoJavaCode);
            result = pseudoJavaCode.split("\\n");
            aBaseMetaTileEntity.disableWorking();
        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        IGregTechTileEntity aBaseMetaTileEntity = getBaseMetaTileEntity();
        //        String pseudoJavaCode = StructureUtility.getPseudoJavaCode(aBaseMetaTileEntity.getWorld(),
        //                ExtendedFacing.of(ForgeDirection.getOrientation(aBaseMetaTileEntity.getFrontFacing())),
        //                aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(),
        // aBaseMetaTileEntity.getZCoord(),
        //                numbers[0], numbers[1], numbers[2],
        //                numbers[3], numbers[4], numbers[5],true);
        String pseudoJavaCode = StructureUtility.getPseudoJavaCode(
                aBaseMetaTileEntity.getWorld(),
                ExtendedFacing.of(ForgeDirection.getOrientation(aBaseMetaTileEntity.getFrontFacing())),
                aBaseMetaTileEntity.getXCoord(),
                aBaseMetaTileEntity.getYCoord(),
                aBaseMetaTileEntity.getZCoord(),
                numbers[0],
                numbers[1],
                numbers[2],
                te -> te.getClass().getCanonicalName(),
                numbers[3],
                numbers[4],
                numbers[5],
                false);
        TecTech.LOGGER.info(pseudoJavaCode);
        result = pseudoJavaCode.split("\\n");
        aBaseMetaTileEntity.disableWorking();
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
    public boolean isElectric() {
        return false;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public String[] getDescription() {
        return new String[] {
            CommonValues.TEC_MARK_GENERAL,
            translateToLocal("gt.blockmachines.debug.tt.writer.desc.0"), // Scans Blocks Around
            EnumChatFormatting.BLUE
                    + translateToLocal(
                            "gt.blockmachines.debug.tt.writer.desc.1"), // Prints Multiblock NonTE structure check code
            EnumChatFormatting.BLUE
                    + translateToLocal("gt.blockmachines.debug.tt.writer.desc.2") // ABC axises aligned to machine front
        };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return result;
    }
}
