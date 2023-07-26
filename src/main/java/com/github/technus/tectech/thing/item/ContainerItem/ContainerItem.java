package com.github.technus.tectech.thing.item.ContainerItem;

import static com.github.technus.tectech.Reference.MODID;
import static com.github.technus.tectech.TecTech.creativeTabTecTech;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

public class ContainerItem extends Item {

    public static Item item;

    public static void run() {
        item = new ContainerItem();
        GameRegistry.registerItem(item, "ContainerItem");
    }

    private ContainerItem() {
        setMaxStackSize(1);
        setUnlocalizedName("ContainerItem");
        setTextureName(MODID + ":ContainerItem");
        setCreativeTab(creativeTabTecTech);
    }

}
