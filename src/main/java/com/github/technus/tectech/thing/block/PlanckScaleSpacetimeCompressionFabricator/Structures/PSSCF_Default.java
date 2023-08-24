package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import net.minecraft.init.Blocks;

public class PSSCF_Default extends BasePSSCFStructure {

    public PSSCF_Default() {
        map.put('x', new BlockInfo(Blocks.stone, 0));
        map.put('z', new BlockInfo(Blocks.glass, 0));
        processStructureMap();
    }

    @Override
    public String[][] getStructureString() {
        return structure;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private final String[][] structure = new String[][] {
            { "x z", "xxx"}
    };

}
