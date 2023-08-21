package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderBlockInWorld;


public class RenderPlanckScaleSpacetimeCompressionFabricator extends TileEntitySpecialRenderer {

    public RenderPlanckScaleSpacetimeCompressionFabricator() { }

    private static final String[] testShape = {
            "AAAAA",
            "BB BB",
            "ABABA",
    };

    private static void centreModel(String[] testShape) {

        int x = testShape.length / 2;
        int y = testShape[0].length() / 2;

        GL11.glTranslated(0.5f - x,-0.5f - y, 0.5f);

    }

    private void rotation() {
        float currentTimeInMs = System.currentTimeMillis();

        float angle = (currentTimeInMs) % 360;

        GL11.glRotatef(angle, 0, 1, 0);
    }

    private static final float scale = 0.999f;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TilePlanckScaleSpacetimeCompressionFabricator PSSCFRenderTile)) return;

        GL11.glPushMatrix();

        this.bindTexture(TextureMap.locationBlocksTexture);

        int xI = 0;
        int yI = 0;

        centreModel(testShape);
        //rotation();

        for (String line : testShape) {
            for (char block : line.toCharArray()) {
                yI++;

                if (block == ' ') continue;

                GL11.glPushMatrix();

                // Move block into place.
                GL11.glTranslated(x + xI, y + yI, z);

                // Build it.
                if(block == 'A')  {
                    renderBlockInWorld(Blocks.iron_block, 0, scale);
                } else {
                    renderBlockInWorld(Blocks.redstone_block, 0, scale);
                }

                GL11.glPopMatrix();
            }
            xI++;
            yI = 0;

        }

        GL11.glPopMatrix();
    }

}
