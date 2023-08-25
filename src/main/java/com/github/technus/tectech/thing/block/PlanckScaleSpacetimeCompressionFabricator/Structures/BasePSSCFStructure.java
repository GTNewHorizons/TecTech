package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import java.util.*;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderFacesInfo;
import net.minecraft.block.Block;

public abstract class BasePSSCFStructure {

    public final int getXLength() {
        return getStructureString().length;
    }

    public final int getYLength() {
        return getStructureString()[0][0].length();
    }

    public final int getZLength() {
        return getStructureString()[0].length;
    }

    public boolean doesBlockExist(int x, int y, int z) {
        try {
            return transparentStructure[x][y].charAt(z) != ' ';
        } catch (Exception e) {
            return false;
        }
    }

    public String[][] getStructureString() {
        return null;
    }

    public final float maxAxisSize() {
        return 1; // Math.max(getXLength(), Math.max(getYLength(), getZLength()));
    }

    public final BlockInfo getAssociatedBlockInfo(final char letter) {
        return charToBlock.get(letter);
    }

    protected HashMap<Character, BlockInfo> charToBlock = new HashMap<>();

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

    private static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }

        final String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    protected void processStructureMap() {

        String[][] structureCopy = deepCopy(getStructureString());
        HashSet<Character> transparentBlocks = getTransparentBlocks();

        // These will be replaced with air, so that blocks behind
        // them are rendered as normal.
        removeTransparentBlocks(structureCopy, transparentBlocks);

        transparentStructure = structureCopy;

        //generateRenderFacesInfo(structureCopy);
    }

    String[][] transparentStructure;

    private RenderFacesInfo[][][] renderFacesArray;

    private void removeTransparentBlocks(String[][] structure, HashSet<Character> transparentBlocks) {
        if (structure == null || transparentBlocks == null) {
            return;  // Nothing to do if either of them is null
        }

        for (int i = 0; i < structure.length; i++) {
            for (int j = 0; j < structure[i].length; j++) {
                StringBuilder newStr = new StringBuilder();

                // Check each character in the string
                for (char c : structure[i][j].toCharArray()) {
                    if (!transparentBlocks.contains(c)) {
                        // If the character is not in the transparentBlocks, append it to the new string.
                        newStr.append(c);
                    } else {
                        // Otherwise air block.
                        newStr.append(' ');
                    }
                }

                // Update the string in the structure.
                structure[i][j] = newStr.toString();
            }
        }
    }

    private HashSet<Character> getTransparentBlocks() {
        HashSet<Character> transparentBlocks = new HashSet<>();

        // Iterate over all blocks to find transparent ones.
        for (Map.Entry<Character, BlockInfo> entry : charToBlock.entrySet()) {

            Block block = entry.getValue().block;

            // Block cannot be seen through.
            if (!block.isOpaqueCube()) {
                transparentBlocks.add(entry.getKey());
            }
        }

        return transparentBlocks;
    }
}
