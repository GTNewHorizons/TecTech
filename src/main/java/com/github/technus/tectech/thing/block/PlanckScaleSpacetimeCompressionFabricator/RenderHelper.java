package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.BasePSSCFStructure;
import org.lwjgl.opengl.GL11;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.*;

public class RenderHelper {

    private static void centreModel(BasePSSCFStructure model) {

        String[][] testShape = model.getStructureString();

        int x = testShape.length / 2;
        int z = testShape[0][0].length() / 2;
        int y = testShape[0].length / 2;

        GL11.glTranslated(-x,  -1 - y, -1 - z);
    }

    private static void rotation() {
        GL11.glRotatef((System.currentTimeMillis() / 16) % 360, 0.3f, 1, 0.5f);
    }

    private static void buildModel(BasePSSCFStructure model) {
        beginRenderingBlocksInWorld(1.0f);

        int xI = 0;
        int yI = 0;
        int zI = 0;

        for (String[] layer : model.getStructureString()) {
            for (String line : layer) {
                yI++;
                for (char blockChar : line.toCharArray()) {
                    zI++;

                    if (blockChar == ' ') continue;

                    BasePSSCFStructure.BlockInfo blockInfo = model.getAssociatedBlockInfo(blockChar);
                    addRenderedBlockInWorld(blockInfo.block, blockInfo.metadata, xI, yI, zI);

                }
                zI = 0;
            }
            xI++;
            yI = 0;
        }

        endRenderingBlocksInWorld();
    }

    private static void scaleModel(final BasePSSCFStructure model) {
        final float maxScale = 1.0f / model.maxAxisSize();
        GL11.glScalef(maxScale, maxScale, maxScale);
    }

    public static void renderModel(double x, double y, double z, final BasePSSCFStructure model) {

        GL11.glPushMatrix();

        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        scaleModel(model);

        //rotation();

        centreModel(model);

        buildModel(model);

        GL11.glPopMatrix();
    }
}
