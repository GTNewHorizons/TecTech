package com.github.technus.tectech.thing.item;

import static com.github.technus.tectech.Reference.MODID;
import static com.github.technus.tectech.thing.CustomItemList.teslaComponent;
import static net.minecraft.util.StatCollector.translateToLocal;

import com.github.technus.tectech.util.CommonValues;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public final class TeslaCoilComponent extends Item {
    public static TeslaCoilComponent INSTANCE;
    private static IIcon ultItemIcon;

    private TeslaCoilComponent() {
        setHasSubtypes(true);
        setUnlocalizedName("tm.itemTeslaComponent");
        setTextureName(MODID + ":itemTeslaComponent");
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer ep, List aList, boolean boo) {
        aList.add(CommonValues.BASS_MARK);
        aList.add(EnumChatFormatting.BLUE
                + translateToLocal("item.tm.itemTeslaComponent.desc")); // Tesla bois need these!
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        return getUnlocalizedName() + "." + getDamage(aStack);
    }

    public static void run() {
        INSTANCE = new TeslaCoilComponent();
        GameRegistry.registerItem(INSTANCE, INSTANCE.getUnlocalizedName());
        teslaComponent.set(INSTANCE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(getIconString());
        ultItemIcon = iconRegister.registerIcon(MODID + ":itemTeslaComponentUltimate");
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        if (damage == 1) {
            return ultItemIcon;
        }
        return itemIcon;
    }

    @Override
    public void getSubItems(Item aItem, CreativeTabs par2CreativeTabs, List aList) {
        aList.add(new ItemStack(aItem, 1, 0));
        aList.add(new ItemStack(aItem, 1, 1));
    }
}
