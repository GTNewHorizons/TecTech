package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Trophies;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base.BaseRenderTESR;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.BasePSSCFStructure;
import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures.PSSCF_DTPF;

public final class DTPF_Trophy extends BaseRenderTESR {

    BasePSSCFStructure DTPF = new PSSCF_DTPF();

    @Override
    public BasePSSCFStructure getPSSCFStructure() {
        return DTPF;
    }

}
