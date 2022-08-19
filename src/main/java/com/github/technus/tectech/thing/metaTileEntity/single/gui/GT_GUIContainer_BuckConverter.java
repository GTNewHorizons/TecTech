package com.github.technus.tectech.thing.metaTileEntity.single.gui;

import static com.github.technus.tectech.util.CommonValues.VN;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import com.github.technus.tectech.util.TT_Utility;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_BuckConverter extends GT_GUIContainerMetaTile_Machine {
    public GT_GUIContainer_BuckConverter(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_BuckConverter(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Teleporter.png");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString("Buck Converter", 46, 8, 16448255);
        if (mContainer != null) {
            GT_Container_BuckConverter buck = (GT_Container_BuckConverter) mContainer;
            fontRendererObj.drawString("EUT: " + buck.EUT, 46, 24, 16448255);
            fontRendererObj.drawString(
                    "TIER: " + VN[TT_Utility.getTier(buck.EUT < 0 ? -buck.EUT : buck.EUT)], 46, 32, 16448255);
            fontRendererObj.drawString("AMP: " + buck.AMP, 46, 40, 16448255);
            fontRendererObj.drawString("SUM: " + (long) buck.AMP * buck.EUT, 46, 48, 16448255);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
