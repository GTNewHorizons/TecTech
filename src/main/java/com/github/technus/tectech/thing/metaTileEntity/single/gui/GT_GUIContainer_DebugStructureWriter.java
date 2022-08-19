package com.github.technus.tectech.thing.metaTileEntity.single.gui;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_DebugStructureWriter extends GT_GUIContainerMetaTile_Machine {
    public GT_GUIContainer_DebugStructureWriter(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_DebugStructureWriter(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Teleporter.png");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString("StructPrint", 46, 8, 16448255);
        if (mContainer != null) {
            GT_Container_DebugStructureWriter dsw = (GT_Container_DebugStructureWriter) mContainer;
            if (dsw.numbers == null) {
                return;
            }
            fontRendererObj.drawString(dsw.size ? "Structure size" : "My position", 46, 16, 16448255);
            fontRendererObj.drawString(dsw.size ? "(Changing scan size)" : "(Moving origin)", 46, 24, 16448255);
            fontRendererObj.drawString("A: " + dsw.numbers[dsw.size ? 3 : 0], 46, 32, 16448255);
            fontRendererObj.drawString("B: " + dsw.numbers[dsw.size ? 4 : 1], 46, 40, 16448255);
            fontRendererObj.drawString("C: " + dsw.numbers[dsw.size ? 5 : 2], 46, 48, 16448255);
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
