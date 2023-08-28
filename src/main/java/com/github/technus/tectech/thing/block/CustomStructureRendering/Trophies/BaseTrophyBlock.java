package com.github.technus.tectech.thing.block.CustomStructureRendering.Trophies;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BaseTrophyBlock extends BaseRenderBlock {
    public BaseTrophyBlock(String name) {
        super(name);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new BaseTrophyTileEntity();
    }

}
