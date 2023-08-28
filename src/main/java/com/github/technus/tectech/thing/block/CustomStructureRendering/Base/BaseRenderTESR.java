package com.github.technus.tectech.thing.block.CustomStructureRendering.Base;

import static com.github.technus.tectech.thing.block.CustomStructureRendering.Base.Util.RenderHelper.getModel;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.Util.CustomRenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.Util.RenderHelper;
import org.lwjgl.opengl.GL11;

public class BaseRenderTESR extends TileEntitySpecialRenderer {

    public static final String MODEL_NAME_NBT_TAG = "modelName";

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof BaseRenderTileEntity trophyTileEntity)) return;

        RenderHelper.renderModel(tile.getWorldObj(), x, y, z, getModel(trophyTileEntity.modelName));
    }

}
