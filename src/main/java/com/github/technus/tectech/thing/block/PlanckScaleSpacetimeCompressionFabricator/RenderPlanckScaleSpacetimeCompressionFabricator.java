package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderBlockInWorld;


public class RenderPlanckScaleSpacetimeCompressionFabricator extends TileEntitySpecialRenderer {

    public RenderPlanckScaleSpacetimeCompressionFabricator() { }

    private static final String[] testShape =  {"   BBB           BBB   "," BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB ","   BBB     B     BBB   ","    B     BBB     B    ","    BB   BBABB   BB    ","     BBBBBAAABBBBB     ","    BB   BBABB   BB    ","    B     BBB     B    ","   BBB     B     BBB   "," BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB ","   BBB           BBB   "};


    private static void centreModel(String[] testShape) {

        int x = testShape.length / 2;
        int y = testShape[0].length() / 2;

        GL11.glTranslated(-x, -1-y, 0.0f);

    }

    private void rotation() {
        float currentTimeInMs = System.currentTimeMillis();

        float angle = (currentTimeInMs / 10.0f) % 360;

        GL11.glRotatef((System.currentTimeMillis() / 16) % 360, 0.3f, 1, 0.5f);
    }

    private static final float scale = 0.999f;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TilePlanckScaleSpacetimeCompressionFabricator PSSCFRenderTile)) return;

        GL11.glPushMatrix();

        this.bindTexture(TextureMap.locationBlocksTexture);

        int xI = 0;
        int yI = 0;

        //centreModel(testShape);
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        rotation();

        centreModel(testShape);


        for (String line : testShape) {
            for (char block : line.toCharArray()) {
                yI++;

                if (block == ' ') continue;

                GL11.glPushMatrix();

                // Move block into place.
                GL11.glTranslated(xI, yI, 0);

                // Build it.
                if(block == 'B')  {
                    renderBlockInWorld(Blocks.quartz_block, 0, scale);
                } else {
                    renderBlockInWorld(Blocks.stained_hardened_clay, 14, scale);
                }

                GL11.glPopMatrix();
            }
            xI++;
            yI = 0;

        }

        GL11.glPopMatrix();
    }

}
