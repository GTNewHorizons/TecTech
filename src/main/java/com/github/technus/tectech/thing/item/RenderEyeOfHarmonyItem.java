package com.github.technus.tectech.thing.item;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base.BaseRenderTESR.MODEL_NAME_NBT_TAG;
import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper.getModel;
import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderStar;

import java.awt.*;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.BasePSSCFStructure;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.PSSCF_DTPF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

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
