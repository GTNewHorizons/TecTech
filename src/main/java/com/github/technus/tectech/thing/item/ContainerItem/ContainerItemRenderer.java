package com.github.technus.tectech.thing.item.ContainerItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
        GL11.glPushMatrix();
        GL11.glScalef(2F, 2F, 2F);  // Doubles the item size for testing
        RenderBlocks renderer = (RenderBlocks) data[0];

        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        textureManager.bindTexture(textureManager.getResourceLocation(0));

        final float scale = 0.4f;
        GL11.glScalef(scale, scale, scale);
        renderer.renderBlockAsItem(Blocks.cobblestone, 0, 1.0F);

        GL11.glPopMatrix();
        //renderStar(itemRenderType);
        return;
/*
        if (itemStack == null) {
            return;
        }

        if (itemStack.getItem() == null) {
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
        }*/

    }

}
