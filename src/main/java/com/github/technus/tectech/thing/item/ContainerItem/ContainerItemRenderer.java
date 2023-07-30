package com.github.technus.tectech.thing.item.ContainerItem;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderBlockInWorld;
import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderStar;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

public class ContainerItemRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    private final String CELESTIAL_BODY_TYPE_NBTTAG = "CELESTIAL_BODY_TYPE_NBTTAG";

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {

        if (itemStack == null) {
            return;
        }

        NBTTagCompound NBTTag = new NBTTagCompound();
        itemStack.readFromNBT(NBTTag);

        if (NBTTag.hasKey(CELESTIAL_BODY_TYPE_NBTTAG)) {

            String type = NBTTag.getString(CELESTIAL_BODY_TYPE_NBTTAG);

            if (type.equals("STAR")) {
                renderStar(itemRenderType);
            }

            if (type.equals("PLANET")) {
                renderBlockInWorld(Blocks.cobblestone, 0, 5);
            }
        }

    }

}
