package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class RenderFacesInfo {

    public RenderFacesInfo() { }

    public RenderFacesInfo(boolean state) {
        this.yPos = state;
        this.yNeg = state;
        this.xPos = state;
        this.xNeg = state;
        this.zPos = state;
        this.zNeg = state;
    }

    public boolean yPos, yNeg;
    public boolean xPos, xNeg;
    public boolean zPos, zNeg;
}
