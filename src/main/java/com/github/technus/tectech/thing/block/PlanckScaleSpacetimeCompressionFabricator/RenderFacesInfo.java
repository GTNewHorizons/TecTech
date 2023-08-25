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

    public boolean yPos;
    public boolean yNeg;
    public boolean xPos;
    public boolean xNeg;
    public boolean zPos;
    public boolean zNeg;
}