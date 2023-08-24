package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base.BaseRenderTESR.renderBlock;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.*;

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

        RenderBlocks renderBlocks = new TTRenderBlocks(world);

        for (String[] layer : model.getStructureString()) {
            for (String line : layer) {
                yI++;
                for (char blockChar : line.toCharArray()) {
                    zI++;

                    if (blockChar == ' ') continue;

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

        public RenderFacesInfo renderFaces;

        public TTRenderBlocks(World worldObj) {
            super(worldObj);
        }

//        @Override
//        public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_) {
//
//        }
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
