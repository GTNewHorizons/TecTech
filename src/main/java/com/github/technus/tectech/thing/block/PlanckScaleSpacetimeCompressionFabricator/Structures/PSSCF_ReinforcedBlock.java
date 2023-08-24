package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import gregtech.api.GregTech_API;

public class PSSCF_ReinforcedBlock extends BasePSSCFStructure {

    public PSSCF_ReinforcedBlock() {
        map.put('A', new BlockInfo(GregTech_API.sBlockCasings1, 12));
        map.put('B', new BlockInfo(GregTech_API.sBlockCasings1, 14));
        map.put('C', new BlockInfo(GregTech_API.sBlockCasings5, 11));
        map.put('D', new BlockInfo(GregTech_API.sBlockCasings5, 12));
        map.put('E', new BlockInfo(GregTech_API.sBlockCasings8, 10));
        map.put('F', new BlockInfo(GregTech_API.sBlockCasings8, 14));
    }

    @Override
    public String[][] getStructureString() {
        return structure;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private final String[][] structure = new String[][] {
            { "BAAAAAB", "AFF FFA", "AF   FA", "A     A", "AF   FA", "AFF FFA", "BAAAAAB" },
            { "AFF FFA", "FEEEEEF", "FEECEEF", " ECDCE ", "FEECEEF", "FEEEEEF", "AFF FFA" },
            { "AF   FA", "FEECEEF", " EEEEE ", " CEEEC ", " EEEEE ", "FEECEEF", "AF   FA" },
            { "A     A", " ECDCE ", " CEEEC ", " DEEED ", " CEEEC ", " ECDCE ", "A     A" },
            { "AF   FA", "FEECEEF", " EEEEE ", " CEEEC ", " EEEEE ", "FEECEEF", "AF   FA" },
            { "AFF FFA", "FEEEEEF", "FEECEEF", " ECDCE ", "FEECEEF", "FEEEEEF", "AFF FFA" },
            { "BAAAAAB", "AFF FFA", "AF   FA", "A     A", "AF   FA", "AFF FFA", "BAAAAAB" } };

}
