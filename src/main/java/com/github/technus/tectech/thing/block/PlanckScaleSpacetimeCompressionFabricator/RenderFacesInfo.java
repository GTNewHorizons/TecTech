package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

public class RenderFacesInfo {

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

    public boolean allHidden() {
        return (!yPos) && (!yNeg) && (!xPos) && (!xNeg) && (!zPos) && (!zNeg);
    }
}
