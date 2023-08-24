package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import net.minecraft.block.Block;

import java.util.HashMap;

public abstract class BasePSSCFStructure {

    public String[][] getStructureString() {
        return null;
    }

    public final int getXLength() {
        return getStructureString().length;
    }
    public final int getYLength() {
        return getStructureString()[0][0].length();
    }
    public final int getZLength() {
        return getStructureString()[0].length;
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

    protected static void reverseInnerArrays(String[][] array) {
        for (String[] innerArray : array) {
            int start = 0;
            int end = innerArray.length - 1;

            while (start < end) {
                String temp = innerArray[start];
                innerArray[start] = innerArray[end];
                innerArray[end] = temp;

                start++;
                end--;
            }
        }
    }
}
