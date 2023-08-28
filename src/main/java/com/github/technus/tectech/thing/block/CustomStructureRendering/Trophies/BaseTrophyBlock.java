package com.github.technus.tectech.thing.block.CustomStructureRendering.Trophies;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import static com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTESR.MODEL_NAME_NBT_TAG;

public class BaseTrophyBlock extends BaseRenderBlock {
    public BaseTrophyBlock(String name) {
        super(name);
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

}
