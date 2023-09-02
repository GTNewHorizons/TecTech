package com.github.technus.tectech.loader.gui;

import com.github.technus.tectech.thing.CustomItemList;
import com.github.technus.tectech.thing.block.QuantumGlassBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

import static com.github.technus.tectech.thing.CustomItemList.Machine_Multi_EyeOfHarmony;

public class CreativeTabTecTech extends CreativeTabs {

    public CreativeTabTecTech(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(QuantumGlassBlock.INSTANCE);
    }

    @Override
    public void displayAllReleventItems(List<ItemStack> stuffToShow) {
        for (CustomItemList item : CustomItemList.values()) {
            if (item.hasBeenSet() && item.getBlock() == GregTech_API.sBlockMachines) {
                stuffToShow.add(item.get(1));
            }
        }
        super.displayAllReleventItems(stuffToShow);
    }
}
