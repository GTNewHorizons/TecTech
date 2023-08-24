package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base;

import com.github.technus.tectech.TecTech;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base.BaseRenderTESR.MODEL_NAME_NBT_TAG;

public class BaseRenderBlock extends Block {

    public BaseRenderBlock(String name) {
        super(Material.iron);
        this.setResistance(20f);
        this.setCreativeTab(TecTech.creativeTabEM);
        this.setBlockName(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("gregtech:iconsets/TRANSPARENT");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int a) {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new BaseRenderTileEntity();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasTagCompound()) {
            // Get the NBT data from the ItemStack
            NBTTagCompound nbt = stack.getTagCompound();

            // Transfer the NBT data to the TileEntity of the placed block
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof BaseRenderTileEntity trophyTileEntity) {
                trophyTileEntity.modelName = nbt.getString(MODEL_NAME_NBT_TAG);
                trophyTileEntity.markDirty(); // Marks the TileEntity as needing to be saved
            }
        }
    }


    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        return new ArrayList<>();
    }

}
