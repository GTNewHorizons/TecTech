package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper.getModel;

import com.github.technus.tectech.thing.CustomItemList;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderFacesInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
        renderBlocks.x = x;
        renderBlocks.y = y;
        renderBlocks.z = z;
        renderBlocks.renderBlockAsItem(block, metadata, 1.0f);

        GL11.glPopMatrix();
    }

}
