package com.github.technus.tectech.thing.block.CustomStructureRendering.Base;

import static com.github.technus.tectech.thing.block.CustomStructureRendering.RenderHelper.getModel;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import com.github.technus.tectech.thing.block.CustomStructureRendering.RenderHelper;
import org.lwjgl.opengl.GL11;

public class BaseRenderTESR extends TileEntitySpecialRenderer {

    public static final String MODEL_NAME_NBT_TAG = "modelName";

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof BaseRenderTileEntity trophyTileEntity)) return;

        RenderHelper.renderModel(tile.getWorldObj(), x, y, z, getModel(trophyTileEntity.modelName));
    }

    public static void renderBlock(Block block, int metadata, RenderHelper.TTRenderBlocks renderBlocks, int x, int y, int z) {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        GL11.glRotated(-90, 0.0, 1.0, 0.0);
        renderBlocks.renderBlockAsItem(block, metadata, 1.0f);

        GL11.glPopMatrix();
    }

}
