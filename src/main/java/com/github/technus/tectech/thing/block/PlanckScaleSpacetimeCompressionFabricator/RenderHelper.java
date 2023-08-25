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

        int xI = 0;
        int yI = 0;
        int zI = 0;
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        TTRenderBlocks renderBlocks = new TTRenderBlocks(world);

        for (String[] layer : model.getStructureString()) {
            for (String line : layer) {
                yI++;
                for (char blockChar : line.toCharArray()) {
                    zI++;

                    if (blockChar == ' ') continue;

                    try {
                        String[][] s = model.getStructureString();
                        String[] a = s[yI- 1];
                        String b = a[xI];
                        Character c = b.charAt(zI - 1);
                        if (blockChar != c) {
                            System.out.println("t");
                        }
                    } catch (Exception e) {
                        System.out.println(xI + " " + yI + " " + zI);
                    }

                    BasePSSCFStructure.BlockInfo blockInfo = model.getAssociatedBlockInfo(blockChar);
                    renderBlock(blockInfo.block, blockInfo.metadata, renderBlocks, xI, yI, zI);

                }
                zI = 0;
            }
            xI++;
            yI = 0;
        }
    }

    public static class TTRenderBlocks extends RenderBlocks {

        public int x;
        public int y;
        public int z;

        public TTRenderBlocks(World world) {
            super(world);
        }


        @Override
        public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_) {
            super.renderFaceYNeg(p_147768_1_, 0, 0, 0, p_147768_8_);
        }

        @Override
        public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_) {
            super.renderFaceYPos(p_147806_1_, 0, 0, 0, p_147806_8_);
        }

        @Override
        public void renderFaceZNeg(Block block, double x, double y, double z, IIcon p_147761_8_) {

            BasePSSCFStructure model = getModel("Default");

            if (this.z == 0) {
                super.renderFaceZNeg(block, 0, 0, 0, p_147761_8_);
                return;
            }

            if (model.doesBlockExist(this.x, this.y, this.z - 1)) return;

            super.renderFaceZNeg(block, 0, 0, 0, p_147761_8_);
        }

        @Override
        public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_) {
            super.renderFaceZPos(p_147734_1_, 0, 0, 0, p_147734_8_);
        }

        @Override
        public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_) {
            super.renderFaceXNeg(p_147798_1_, 0, 0, 0, p_147798_8_);
        }

        @Override
        public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_) {
            super.renderFaceXPos(p_147764_1_, 0, 0, 0, p_147764_8_);
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

        // rotation();

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
