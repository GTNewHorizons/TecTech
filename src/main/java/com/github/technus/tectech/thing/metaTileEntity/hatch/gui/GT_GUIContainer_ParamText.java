package com.github.technus.tectech.thing.metaTileEntity.hatch.gui;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import com.github.technus.tectech.font.TecTechFontRender;
import com.github.technus.tectech.loader.NetworkDispatcher;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_ParamText;
import com.github.technus.tectech.thing.metaTileEntity.hatch.TextParametersMessage;
import com.github.technus.tectech.util.TT_Utility;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import java.util.Objects;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_ParamText extends GT_GUIContainerMetaTile_Machine {
    private GuiTextField value0tb;
    private GuiTextField value1tb;

    public GT_GUIContainer_ParamText(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_ParamText(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "ParametrizerText.png");
    }

    @Override
    public void initGui() {
        super.initGui();
        value0tb = new GuiTextField(
                TecTechFontRender.INSTANCE,
                (this.width - 176) / 2 + 12 + 14,
                (this.height - 166) / 2 + 26,
                156 - 18,
                12);
        value0tb.setMaxStringLength(80);
        value1tb = new GuiTextField(
                TecTechFontRender.INSTANCE,
                (this.width - 176) / 2 + 12 + 14,
                (this.height - 166) / 2 + 41,
                156 - 18,
                12);
        value1tb.setMaxStringLength(80);
        updateValues();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        value0tb.setFocused(false);
        value1tb.setFocused(false);
        updateValues();
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        value0tb.textboxKeyTyped(p_73869_1_, p_73869_2_);
        value1tb.textboxKeyTyped(p_73869_1_, p_73869_2_);
        if ((p_73869_2_ != 1 && p_73869_2_ != this.mc.gameSettings.keyBindInventory.getKeyCode())
                || (!value0tb.isFocused() && !value1tb.isFocused())) {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
        updateValues();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        value0tb.updateCursorCounter();
        value1tb.updateCursorCounter();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        value0tb.drawTextBox();
        value1tb.drawTextBox();
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        value0tb.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        value1tb.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        updateValues();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        if (mContainer != null) {
            TecTechFontRender.INSTANCE.drawSplitString(
                    "Parameters tXt: " + ((GT_Container_ParamText) mContainer).param, 46, 7, 167, 0xffffff);
            TecTechFontRender.INSTANCE.drawSplitString("\u24EA\u2b06", 10, 29, 16, 0x00bbff);
            TecTechFontRender.INSTANCE.drawSplitString("\u2460\u2b06", 10, 44, 16, 0x0077ff);
            TecTechFontRender.INSTANCE.drawSplitString(
                    "\u24EA\u2b07" + TT_Utility.formatNumberExp((((GT_Container_ParamText) mContainer).input0d)),
                    10,
                    56,
                    167,
                    0x22ddff);
            TecTechFontRender.INSTANCE.drawSplitString(
                    "\u2460\u2b07" + TT_Utility.formatNumberExp((((GT_Container_ParamText) mContainer).input1d)),
                    10,
                    65,
                    167,
                    0x00ffff);
        } else {
            TecTechFontRender.INSTANCE.drawSplitString("Parameters tXt", 46, 7, 167, 0xffffff);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    private void updateValues() {
        updateIn0();
        updateIn1();
    }

    private void updateIn0() {
        if (!value0tb.isFocused()) {
            String str = value0tb.getText().toLowerCase();
            double val;
            try {
                if (str.contains("b")) {
                    String[] split = str.split("b");
                    val = TT_Utility.bitStringToInt(split[0].replaceAll("[^-]", "") + split[1].replaceAll("_", ""));
                } else if (str.contains("x")) {
                    String[] split = str.split("x");
                    val = TT_Utility.hexStringToInt(split[0].replaceAll("[^-]", "") + split[1].replaceAll("_", ""));
                } else {
                    val = TT_Utility.stringToDouble(str);
                }
                if (!Objects.equals(
                        ((GT_MetaTileEntity_Hatch_ParamText)
                                        ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity())
                                .value0s,
                        value0tb.getText())) {
                    ((GT_Container_ParamText) mContainer).value0s = value0tb.getText();
                    ((GT_Container_ParamText) mContainer).value0d = val;
                    ((GT_MetaTileEntity_Hatch_ParamText)
                                            ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity())
                                    .value0s =
                            value0tb.getText();

                    NetworkDispatcher.INSTANCE.sendToServer(
                            new TextParametersMessage.ParametersTextUpdate((GT_MetaTileEntity_Hatch_ParamText)
                                    ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity()));
                }
            } catch (Exception e) {
                value0tb.setText(((GT_MetaTileEntity_Hatch_ParamText)
                                ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity())
                        .value0s);
            }
        }
    }

    private void updateIn1() {
        if (!value1tb.isFocused()) {
            String str = value1tb.getText().toLowerCase();
            double val;
            try {
                if (str.contains("b")) {
                    String[] split = str.split("b");
                    val = TT_Utility.bitStringToInt(split[0].replaceAll("[^-]", "") + split[1].replaceAll("_", ""));
                } else if (str.contains("x")) {
                    String[] split = str.split("x");
                    val = TT_Utility.hexStringToInt(split[0].replaceAll("[^-]", "") + split[1].replaceAll("_", ""));
                } else {
                    val = TT_Utility.stringToDouble(str);
                }
                if (!Objects.equals(
                        ((GT_MetaTileEntity_Hatch_ParamText)
                                        ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity())
                                .value1s,
                        value1tb.getText())) {
                    ((GT_Container_ParamText) mContainer).value1s = value1tb.getText();
                    ((GT_Container_ParamText) mContainer).value1d = val;
                    ((GT_MetaTileEntity_Hatch_ParamText)
                                            ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity())
                                    .value1s =
                            value1tb.getText();

                    NetworkDispatcher.INSTANCE.sendToServer(
                            new TextParametersMessage.ParametersTextUpdate((GT_MetaTileEntity_Hatch_ParamText)
                                    ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity()));
                }
            } catch (Exception e) {
                value1tb.setText(((GT_MetaTileEntity_Hatch_ParamText)
                                ((GT_Container_ParamText) mContainer).mTileEntity.getMetaTileEntity())
                        .value1s);
            }
        }
    }

    public void setTextIn0(String in0) {
        ((GT_Container_ParamText) mContainer).value0s = in0;
        this.value0tb.setText(in0);
    }

    public void setTextIn1(String in1) {
        ((GT_Container_ParamText) mContainer).value1s = in1;
        this.value1tb.setText(in1);
    }
}
