package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base.BaseRenderTESR.renderBlock;

import java.util.HashMap;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.init.Blocks;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.*;
import org.lwjgl.opengl.GL12;
import scala.Char;

public class RenderHelper {

    private static void centreModel(BasePSSCFStructure model) {

        String[][] testShape = model.getStructureString();

        int x = testShape.length / 2;
        int z = testShape[0][0].length() / 2;
        int y = testShape[0].length / 2;

        GL11.glTranslated(-x, -1 - y, -1 - z);
    }

    private static void rotation() {
        GL11.glRotatef((System.currentTimeMillis() / 16) % 360, 0.3f, 1, 0.5f);
    }

    private static void buildModel(World world, BasePSSCFStructure model) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        TTRenderBlocks renderBlocks = new TTRenderBlocks(world);

        for (int x = 0; x < model.getXLength(); x++) {
            for (int y = 0; y < model.getYLength(); y++) {
                for (int z = 0; z < model.getZLength(); z++) {
                    Character blockChar = model.getStructureString()[x][z].charAt(y);

                    if (blockChar.equals(' ')) continue;
                    if (model.renderFacesArray[x][z][y].allHidden()) continue;

                    BasePSSCFStructure.BlockInfo blockInfo = model.getAssociatedBlockInfo(blockChar);

                    renderBlocks.renderFacesInfo = model.renderFacesArray[x][z][y];
                    renderBlock(blockInfo.block, blockInfo.metadata, renderBlocks, x, z+1, y+1);
                }
            }
        }
    }

    public static class TTRenderBlocks extends RenderBlocks {

        RenderFacesInfo renderFacesInfo;

        public TTRenderBlocks(World world) {
            super(world);
        }



        @Override
        public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_) {
            if (this.renderFacesInfo.yNeg) {
                super.renderFaceYNeg(p_147768_1_, 0, 0, 0, p_147768_8_);
            }
        }

        @Override
        public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_) {
            if (this.renderFacesInfo.yPos) {
                super.renderFaceYPos(p_147806_1_, 0, 0, 0, p_147806_8_);
            }
        }

        @Override
        public void renderFaceZNeg(Block block, double x, double y, double z, IIcon p_147761_8_) {
            if (this.renderFacesInfo.zNeg) {
                super.renderFaceZNeg(block, 0, 0, 0, p_147761_8_);
            }
        }

        @Override
        public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_) {
            if (this.renderFacesInfo.zPos) {
                super.renderFaceZPos(p_147734_1_, 0, 0, 0, p_147734_8_);
            }
        }

        @Override
        public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_) {
            if (this.renderFacesInfo.xNeg) {
                super.renderFaceXNeg(p_147798_1_, 0, 0, 0, p_147798_8_);
            }
        }

        @Override
        public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_) {
            if (this.renderFacesInfo.xPos) {
                super.renderFaceXPos(p_147764_1_, 0, 0, 0, p_147764_8_);
            }
        }
    }

    private static void scaleModel(final BasePSSCFStructure model) {
        final float maxScale = 1.0f / model.maxAxisSize();
        GL11.glScalef(maxScale, maxScale, maxScale);
    }

    private static final HashMap<String, BasePSSCFStructure> modelMap = new HashMap<>();

    public static void registerModel(String label, BasePSSCFStructure model) {
        modelMap.put(label, model);
    }

    public static Set<String> getModelList() {
        return modelMap.keySet();
    }

    public static BasePSSCFStructure getModel(String label) {
        BasePSSCFStructure model = modelMap.getOrDefault(label, null);

        if (model == null) return modelMap.get("Default");

        return model;
    }

    public static void renderModel(World world, double x, double y, double z, final BasePSSCFStructure model) {

        GL11.glPushMatrix();

        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        scaleModel(model);

        //rotation();

        centreModel(model);

        buildModel(world, model);

        GL11.glPopMatrix();
    }

    public static void registerAll() {
        RenderHelper.registerModel("Default", new PSSCF_Default());
        RenderHelper.registerModel("DTPF", new PSSCF_DTPF());
        RenderHelper.registerModel("NanoForge", new PSSCF_NanoForge());
        RenderHelper.registerModel("ReinforcedBlock", new PSSCF_ReinforcedBlock());
    }
}
