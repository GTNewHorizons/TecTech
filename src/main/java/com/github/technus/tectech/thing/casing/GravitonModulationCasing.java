package com.github.technus.tectech.thing.casing;

import static com.github.technus.tectech.util.CommonValues.EOH_TIER_FANCY_NAMES;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.WHITE;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.github.technus.tectech.thing.CustomItemList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;

public class GravitonModulationCasing extends GT_Block_Casings_Abstract {

    private static IIcon textureTier0;
    private static IIcon textureTier1;
    private static IIcon textureTier2;
    private static IIcon textureTier3;
    private static IIcon textureTier4;
    private static IIcon textureTier5;
    private static IIcon textureTier6;
    private static IIcon textureTier7;
    private static IIcon textureTier8;
    private static final int MAX_BLOCK_TIER = 9;

    private static final byte START_INDEX = 64;

    public GravitonModulationCasing() {
        super(GT_Item_Casings_GravitonModulation.class, "gt.graviton_flow_modulator", GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[7][b + START_INDEX] = new GT_CopiedBlockTexture(this, 6, b);
        }

        for (int i = 0; i < MAX_BLOCK_TIER; i++) {
            if (i < 8) {
                GT_LanguageManager.addStringLocalization(
                        getUnlocalizedName() + "." + i + ".name",
                        WHITE + EOH_TIER_FANCY_NAMES[i] + RESET + " Graviton Flow Modulator");
            } else {
                GT_LanguageManager.addStringLocalization(
                        getUnlocalizedName() + "." + i + ".name",
                        WHITE + (EnumChatFormatting.BOLD + "Hyperion") + RESET + " Graviton Flow Modulator");
            }
        }

        CustomItemList.GravitonFlowModulatorTier0.set(new ItemStack(this, 1, 0));
        CustomItemList.GravitonFlowModulatorTier1.set(new ItemStack(this, 1, 1));
        CustomItemList.GravitonFlowModulatorTier2.set(new ItemStack(this, 1, 2));
        CustomItemList.GravitonFlowModulatorTier3.set(new ItemStack(this, 1, 3));
        CustomItemList.GravitonFlowModulatorTier4.set(new ItemStack(this, 1, 4));
        CustomItemList.GravitonFlowModulatorTier5.set(new ItemStack(this, 1, 5));
        CustomItemList.GravitonFlowModulatorTier6.set(new ItemStack(this, 1, 6));
        CustomItemList.GravitonFlowModulatorTier7.set(new ItemStack(this, 1, 7));
        CustomItemList.GravitonFlowModulatorTier8.set(new ItemStack(this, 1, 8));
    }

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        textureTier0 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_0");
        textureTier1 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_1");
        textureTier2 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_2");
        textureTier3 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_3");
        textureTier4 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_4");
        textureTier5 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_5");
        textureTier6 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_6");
        textureTier7 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_7");
        textureTier8 = aIconRegister.registerIcon("gregtech:iconsets/STABILITY_CASING_8");
    }

    @Override
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return textureTier0;
            case 1:
                return textureTier1;
            case 2:
                return textureTier2;
            case 3:
                return textureTier3;
            case 4:
                return textureTier4;
            case 5:
                return textureTier5;
            case 6:
                return textureTier6;
            case 7:
                return textureTier7;
            case 8:
                return textureTier8;
            default:
                return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
        return getIcon(aSide, tMeta);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList) {
        for (int i = 0; i < MAX_BLOCK_TIER; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }
}
