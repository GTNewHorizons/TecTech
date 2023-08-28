package com.github.technus.tectech.thing.block.CustomStructureRendering.Trophies;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTESR.MODEL_NAME_NBT_TAG;

public class BaseTrophyBlock extends BaseRenderBlock {
    public BaseTrophyBlock(String name) {
        super(name);
        this.setResistance(1.0F);
        this.setHardness(1.5F);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new BaseTrophyTileEntity();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        final BaseTrophyTileEntity tileEntity = (BaseTrophyTileEntity) world.getTileEntity(x, y, z);
        final ItemStack itemToDrop = new ItemStack(Trophies.TrophyItem, 1, 0);

        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString(MODEL_NAME_NBT_TAG, tileEntity.modelName);

        itemToDrop.stackTagCompound = tag;

        return itemToDrop;
    }

    @Deprecated
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        // Although this is deprecated, waila depends on it to generate the ItemStack for its visualisation annoyingly.
        return getPickBlock(null, world, x, y, z, null);
    }




    // Handling dropping of item when block is broken.
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
        if (willHarvest) return true; // Delay deletion of the block until after getDrops.
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
    {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {

        final ArrayList<ItemStack> arrayList = new ArrayList<>();
        arrayList.add(getPickBlock(null, world, x, y, z, null));

        return arrayList;
    }

}
