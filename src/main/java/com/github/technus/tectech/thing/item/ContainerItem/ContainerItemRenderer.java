package com.github.technus.tectech.thing.item.ContainerItem;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderBlockInWorld;
import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderStar;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

import java.awt.*;

public class ContainerItemRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {

        final NBTTagCompound NBTTag = itemStack.stackTagCompound;

        if (NBTTag.getString("celestialBodyType").equals("STAR")) {
            renderStar(type);
        }

        if (NBTTag.getString("celestialBodyType").equals("PLANET")) {
            renderBlockInWorld(Blocks.cobblestone, 0, 5);
        }
    }

}
