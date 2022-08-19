package com.github.technus.tectech.thing.metaTileEntity.hatch.gui;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static org.lwjgl.opengl.GL11.*;

import com.github.technus.tectech.font.TecTechFontRender;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_UncertaintyAdv extends GT_GUIContainerMetaTile_Machine {
    protected static final short sX = 52, sY = 33, bU = 0, rU = 70, fU = 192, V = 210, Vs = 216;

    public GT_GUIContainer_UncertaintyAdv(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_Uncertainty(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Uncertainty.png");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        TecTechFontRender.INSTANCE.drawSplitString("Schr\u00F6dinger X", 46, 7, 167, 0xffffff);
        if (mContainer != null && ((GT_Container_Uncertainty) mContainer).status == 0) {
            TecTechFontRender.INSTANCE.drawSplitString("Status: OK", 46, 16, 167, 0xffffff);
        } else {
            TecTechFontRender.INSTANCE.drawSplitString("Status: NG", 46, 16, 167, 0xffffff);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (mContainer != null && ((GT_Container_Uncertainty) mContainer).matrix != null) {
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            x += sX;
            y += sY;
            int state = ((GT_Container_Uncertainty) mContainer).status;
            switch (((GT_Container_Uncertainty) mContainer).mode) {
                case 1: // ooo oxo ooo
                    drawTexturedModalRect(x + 12, y + 12, rU + (state == 0 ? 76 : 12), Vs + 12, 10, 10);
                    break;
                case 2: // ooo xox ooo
                    drawTexturedModalRect(x, y + 12, rU + ((state & 1) == 0 ? 64 : 0), Vs + 12, 10, 10);
                    drawTexturedModalRect(x + 24, y + 12, rU + ((state & 2) == 0 ? 88 : 24), Vs + 12, 10, 10);
                    break;
                case 3: // oxo xox oxo
                    drawTexturedModalRect(x + 12, y, rU + ((state & 1) == 0 ? 76 : 12), Vs, 10, 10);
                    drawTexturedModalRect(x, y + 12, rU + ((state & 2) == 0 ? 64 : 0), Vs + 12, 10, 10);
                    drawTexturedModalRect(x + 24, y + 12, rU + ((state & 4) == 0 ? 88 : 24), Vs + 12, 10, 10);
                    drawTexturedModalRect(x + 12, y + 24, rU + ((state & 8) == 0 ? 76 : 12), Vs + 24, 10, 10);
                    break;
                case 4: // xox ooo xox
                    drawTexturedModalRect(x, y, rU + ((state & 1) == 0 ? 64 : 0), Vs, 10, 10);
                    drawTexturedModalRect(x + 24, y, rU + ((state & 2) == 0 ? 88 : 24), Vs, 10, 10);
                    drawTexturedModalRect(x, y + 24, rU + ((state & 4) == 0 ? 64 : 0), Vs + 24, 10, 10);
                    drawTexturedModalRect(x + 24, y + 24, rU + ((state & 8) == 0 ? 88 : 24), Vs + 24, 10, 10);
                    break;
                case 5: // xox ooo xox
                    drawTexturedModalRect(x, y, rU + ((state & 1) == 0 ? 64 : 0), Vs, 10, 10);
                    drawTexturedModalRect(x + 24, y, rU + ((state & 2) == 0 ? 88 : 24), Vs, 10, 10);
                    drawTexturedModalRect(x + 12, y + 12, rU + ((state & 4) == 0 ? 76 : 12), Vs + 12, 10, 10);
                    drawTexturedModalRect(x, y + 24, rU + ((state & 8) == 0 ? 64 : 0), Vs + 24, 10, 10);
                    drawTexturedModalRect(x + 24, y + 24, rU + ((state & 16) == 0 ? 88 : 24), Vs + 24, 10, 10);
                    break;
            }
            x -= 6;
            y -= 6;

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            for (int i = 0; i < 16; i++) {
                glColor4f(1f, 1f, 1f, (float) ((GT_Container_Uncertainty) mContainer).matrix[i] / 1000f);
                drawTexturedModalRect(x + 12 * (i / 4), y + 12 * (i % 4), fU + 12 * (i / 4), V + 12 * (i % 4), 10, 10);
            }
            glDisable(GL_BLEND);

            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (((GT_Container_Uncertainty) mContainer).selection > -1) {
                int sel = ((GT_Container_Uncertainty) mContainer).selection;
                drawTexturedModalRect(
                        x + 12 * (sel / 4), y + 12 * (sel % 4), bU + 12 * (sel / 4), V + 12 * (sel % 4), 10, 10);
            }
        }
    }
}
