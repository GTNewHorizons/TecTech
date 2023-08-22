package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.HashMap;

public abstract class BasePSSCFStructure {

    public String[][] getStructure() {
        return null;
    }

    public final int getXLength() {
        return getStructure().length;
    }
    public final int getYLength() {
        return getStructure()[0][0].length();
    }
    public final int getZLength() {
        return getStructure()[0].length;
    }

    public final float maxAxisSize() {
        return Math.max(getXLength(), Math.max(getYLength(), getZLength()));
    }

    public final BlockInfo getAssociatedBlockInfo(final char letter) {
        return map.get(letter);
    }

    protected HashMap<Character, BlockInfo> map = new HashMap<>();

    public static class BlockInfo {
        public final int metadata;
        public final Block block;
        BlockInfo(Block block, int metadata) {
            this.block = block;
            this.metadata = metadata;
        }
    }
}
