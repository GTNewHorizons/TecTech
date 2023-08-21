package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;


public class RenderPlanckScaleSpacetimeCompressionFabricator extends TileEntitySpecialRenderer {

    public RenderPlanckScaleSpacetimeCompressionFabricator() { }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TilePlanckScaleSpacetimeCompressionFabricator PSSCFRenderTile)) return;

        GL11.glPushMatrix();

        GL11.glPopMatrix();
    }

}
