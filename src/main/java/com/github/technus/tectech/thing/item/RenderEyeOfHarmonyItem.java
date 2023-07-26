package com.github.technus.tectech.thing.item;

import static com.github.technus.tectech.thing.block.RenderEyeOfHarmony.STAR_LAYER_0;
import static com.github.technus.tectech.thing.block.RenderEyeOfHarmony.STAR_LAYER_1;
import static com.github.technus.tectech.thing.block.RenderEyeOfHarmony.STAR_LAYER_2;
import static com.github.technus.tectech.thing.block.RenderEyeOfHarmony.starModel;
import static com.github.technus.tectech.thing.item.ContainerItem.StarRenderUtility.renderStar;
import static java.lang.Math.pow;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderEyeOfHarmonyItem implements IItemRenderer {

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
