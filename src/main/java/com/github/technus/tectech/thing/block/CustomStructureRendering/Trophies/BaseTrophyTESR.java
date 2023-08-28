package com.github.technus.tectech.thing.block.CustomStructureRendering.Trophies;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTESR;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Structures.BaseModelStructure;

public class BaseTrophyTESR extends BaseRenderTESR {

    @Override
    protected BaseModelStructure getModel(String modelName) {
        return Trophies.getModel(modelName);
    }

}
