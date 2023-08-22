package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.BasePSSCFStructure;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class BaseRenderTESR extends TileEntitySpecialRenderer {

    private static final float scale = 0.9999f;

    public BasePSSCFStructure getPSSCFStructure() {
        return null;
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        this.bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.renderModel(x, y, z, getPSSCFStructure());

    }

}
