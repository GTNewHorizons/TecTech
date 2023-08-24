package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.BasePSSCFStructure;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.PSSCF_DTPF;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.PSSCF_Default;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Trophies.Trophies;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper.getModel;

public class BaseRenderTESR extends TileEntitySpecialRenderer {

    public static final String MODEL_NAME_NBT_TAG = "modelName";

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {

        if (!(tile instanceof BaseRenderTileEntity trophyTileEntity)) return;

        this.bindTexture(TextureMap.locationBlocksTexture);

        RenderHelper.renderModel(x, y, z, getModel(trophyTileEntity.modelName));
    }

}
