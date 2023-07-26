package com.github.technus.tectech.thing.item.ContainerItem;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import static com.github.technus.tectech.thing.item.ContainerItem.StarRenderUtility.renderStar;

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
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        renderStar(type);
    }

}
