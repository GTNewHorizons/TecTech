package com.github.technus.tectech.thing.casing;

import static com.github.technus.tectech.TecTech.creativeTabTecTech;
import static com.github.technus.tectech.TecTech.tectechTexturePage1;

import com.github.technus.tectech.thing.CustomItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GT_Block_CasingsTT2 extends GT_Block_Casings_Abstract {
    public static final byte TEXTURE_PAGE = tectechTexturePage1;
    public static final int NUM_OF_BLOCKS = 4;
    public static final short TEXTURE_OFFSET = 96; // Start of PAGE 8 (which is the 9th page)  (8*128)
    private static IIcon eM0, eM0s, eM1, eM1s, eM2, eM2s, eM3, eM3s;

    public GT_Block_CasingsTT2() {
        super(GT_Item_CasingsTT2.class, "gt.blockcasingsTT2", GT_Material_Casings.INSTANCE);
        setCreativeTab(creativeTabTecTech);

        for (byte b = 0; b < NUM_OF_BLOCKS; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[TEXTURE_PAGE][b + TEXTURE_OFFSET] = TextureFactory.of(this, b);
            /*IMPORTANT for block recoloring**/
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Computer Air Cooler MK-I");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Computer Air Cooler MK-II");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Computer Liquid Cooler MK-I");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Computer Liquid Cooler MK-II");

        CustomItemList.eM_Computer_Vent_T1.set(new ItemStack(this, 1, 0));
        CustomItemList.eM_Computer_Vent_T2.set(new ItemStack(this, 2, 0));
        CustomItemList.eM_Computer_Liquid_Cooler_T1.set(new ItemStack(this, 3, 0));
        CustomItemList.eM_Computer_Liquid_Cooler_T1.set(new ItemStack(this, 4, 0));
    }

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        eM0 = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_VENT_T1_NONSIDE");
        eM0s = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_VENT_T1");
        eM1 = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_VENT_T2_NONSIDE");
        eM1s = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_VENT_T2");

        eM2 = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_LIQUID_COOLER_T1_NONSIDE");
        eM2s = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_LIQUID_COOLER_T1");
        eM3 = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_LIQUID_COOLER_T2_NONSIDE");
        eM3s = aIconRegister.registerIcon("gregtech:iconsets/EM_PC_LIQUID_COOLER_T2");
    }

    @Override
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                if (aSide < 2) {
                    return eM0;
                }
                return eM0s;
            case 1:
                if (aSide < 2) {
                    return eM1;
                }
                return eM1s;
            case 2:
                if (aSide < 2) {
                    return eM2;
                }
                return eM2s;
            case 3:
                if (aSide < 2) {
                    return eM3;
                }
                return eM3s;
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

    @Override
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList) {
        for (int i = 0; i < NUM_OF_BLOCKS; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }
}
