package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import com.github.technus.tectech.TecTech;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public class PlanckScaleSpacetimeCompressionFabricatorBlock extends Block {

    public PlanckScaleSpacetimeCompressionFabricatorBlock() {
        super(Material.iron);
        this.setResistance(20f);
        this.setHardness(-1.0f);
        this.setCreativeTab(TecTech.creativeTabEM);
        this.setBlockName("Planck Scale Spacetime Compression Fabricator Renderer");
        this.setLightLevel(100.0f);
        registerOther(this);
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
        return new TilePlanckScaleSpacetimeCompressionFabricator();
    }

    public static void registerOther(Block block) {
        String name = block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
        GameRegistry.registerBlock(block, name.substring(name.indexOf(":") + 1));
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        return new ArrayList<>();
    }

}
