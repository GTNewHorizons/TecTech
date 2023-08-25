package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import net.minecraft.init.Blocks;

public class PSSCF_Default extends BasePSSCFStructure {

    public PSSCF_Default() {
        charToBlock.put('x', new BlockInfo(Blocks.stone, 0));
        charToBlock.put('z', new BlockInfo(Blocks.glass, 0));
        charToBlock.put('a', new BlockInfo(Blocks.redstone_block, 0));
        charToBlock.put('b', new BlockInfo(Blocks.wool, 0));
        charToBlock.put('c', new BlockInfo(Blocks.coal_block, 0));
        processStructureMap();
    }

    @Override
    public String[][] getStructureString() {
        return structure;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private final String[][] structure = new String[][] {
            { "xzx", "abc"}
    };

}
