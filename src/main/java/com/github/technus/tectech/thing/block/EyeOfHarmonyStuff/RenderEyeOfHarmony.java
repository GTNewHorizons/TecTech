package com.github.technus.tectech.thing.block.EyeOfHarmonyStuff;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static com.github.technus.tectech.Reference.MODID;
import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderBlockInWorld;
import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderStar;

public class RenderEyeOfHarmony extends TileEntitySpecialRenderer {

    public static final ResourceLocation STAR_LAYER_0 = new ResourceLocation(MODID, "models/StarLayer0.png");
    public static final ResourceLocation STAR_LAYER_1 = new ResourceLocation(MODID, "models/StarLayer1.png");
    public static final ResourceLocation STAR_LAYER_2 = new ResourceLocation(MODID, "models/StarLayer2.png");
    public static IModelCustom starModel;
    private static IModelCustom spaceModel;

    public RenderEyeOfHarmony() {
        starModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/Star.obj"));
        spaceModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/Space.obj"));
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TileEyeOfHarmony EOHRenderTile)) return;

        GL11.glPushMatrix();
        // Required to centre the render to the middle of the block.
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        // Star shell.
        renderOuterSpaceShell();

        // Render the planets.
        renderOrbitObjects(EOHRenderTile);

        // Render the star itself.
        renderStar(IItemRenderer.ItemRenderType.INVENTORY);

        GL11.glPopMatrix();
    }

    private void renderOrbitObjects(final TileEyeOfHarmony EOHRenderTile) {

        if (EOHRenderTile.getOrbitingObjects() != null) {

            if (EOHRenderTile.getOrbitingObjects().size() == 0) {
                EOHRenderTile.generateImportantInfo();
            }

            for (TileEyeOfHarmony.OrbitingObject t : EOHRenderTile.getOrbitingObjects()) {
                renderOrbit(EOHRenderTile, t);
            }
        }
    }

    void renderOrbit(final TileEyeOfHarmony EOHRenderTile, final TileEyeOfHarmony.OrbitingObject orbitingObject) {
        // Render orbiting body.
        GL11.glPushMatrix();
        GL11.glRotatef(orbitingObject.zAngle, 0, 0, 1);
        GL11.glRotatef(orbitingObject.xAngle, 1, 0, 0);
        GL11.glRotatef((orbitingObject.rotationSpeed * 0.1f * EOHRenderTile.angle) % 360.0f, 0F, 1F, 0F);
        GL11.glTranslated(-0.2 - orbitingObject.distance - STAR_RESCALE * EOHRenderTile.getSize(), 0, 0);
        GL11.glRotatef((orbitingObject.orbitSpeed * 0.1f * EOHRenderTile.angle) % 360.0f, 0F, 1F, 0F);

        this.bindTexture(TextureMap.locationBlocksTexture);
        renderBlockInWorld(orbitingObject.block, 0, orbitingObject.scale);

        GL11.glPopMatrix();
    }

    public static void renderOuterSpaceShell() {

        // Begin animation.
        GL11.glPushMatrix();

        // OpenGL's settings, not sure exactly what these do.

        // Disables lighting, so star is always lit (I think).
        GL11.glDisable(GL11.GL_LIGHTING);
        // Merges colours of the various layers of the star?
        GL11.glEnable(GL11.GL_BLEND);

        // Bind animation to layer of star.
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(new ResourceLocation(MODID, "models/spaceLayer.png"));

        final float scale = 0.01f * 17.5f;
        // Scale the star up in the x, y and z directions.
        GL11.glScalef(scale, scale, scale);

        GL11.glColor4f(1, 1, 1, 1);

        spaceModel.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);

        // Finish animation.
        GL11.glPopMatrix();
    }

    private static final float STAR_RESCALE = 0.2f;

}
