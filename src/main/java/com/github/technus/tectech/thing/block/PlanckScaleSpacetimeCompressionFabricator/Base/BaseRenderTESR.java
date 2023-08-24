package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper.getModel;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper;

public class BaseRenderTESR extends TileEntitySpecialRenderer {

    public static final String MODEL_NAME_NBT_TAG = "modelName";

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {

        if (!(tile instanceof BaseRenderTileEntity trophyTileEntity)) return;

        this.bindTexture(TextureMap.locationBlocksTexture);

        RenderHelper.renderModel(x, y, z, getModel(trophyTileEntity.modelName));
    }

}
