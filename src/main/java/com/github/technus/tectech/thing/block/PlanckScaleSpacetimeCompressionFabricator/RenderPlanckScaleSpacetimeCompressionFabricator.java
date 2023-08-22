package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.BasePSSCFStructure;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.PSSCF_DTPF;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.PSSCF_NanoForge;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.*;

public class RenderPlanckScaleSpacetimeCompressionFabricator extends TileEntitySpecialRenderer {

    private static void centreModel(BasePSSCFStructure model) {

        String[][] testShape = model.getStructure();

        int x = testShape.length / 2;
        int z = testShape[0][0].length() / 2;
        int y = testShape[0].length / 2;

        GL11.glTranslated(-x,  -1 - y, -1 - z);
    }

    private static void rotation() {
        GL11.glRotatef((System.currentTimeMillis() / 16) % 360, 0.3f, 1, 0.5f);
    }

    private static void buildModel(BasePSSCFStructure model) {
        beginRenderingBlocksInWorld(scale);

        int xI = 0;
        int yI = 0;
        int zI = 0;

        for (String[] layer : model.getStructure()) {
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

    private void scaleModel(final BasePSSCFStructure model) {
        final float maxScale = 5.0f / model.maxAxisSize();
        GL11.glScalef(maxScale, maxScale, maxScale);
    }

    private static final float scale = 0.9999f;

    BasePSSCFStructure DTPF = new PSSCF_NanoForge();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TilePlanckScaleSpacetimeCompressionFabricator)) return;

        GL11.glPushMatrix();

        this.bindTexture(TextureMap.locationBlocksTexture);

        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        scaleModel(DTPF);

        //GL11.glRotatef(180, 0.0f, 0.0f, 1.0f);
        //rotation();

        centreModel(DTPF);

        buildModel(DTPF);

        GL11.glPopMatrix();
    }

}
