package com.github.technus.tectech.recipe;

import static gregtech.api.util.GT_Utility.trans;

import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.github.technus.tectech.thing.gui.TecTechUITextures;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GodforgeExoticFrontend extends RecipeMapFrontend {

    public GodforgeExoticFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
            NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder);
    }

    @Override
    public void addGregTechLogo(ModularWindow.Builder builder, Pos2d windowOffset) {
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_GODFORGE_LOGO).setSize(18, 18)
                        .setPos(new Pos2d(151, 63).add(windowOffset)));
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return Collections.singletonList(new Pos2d(52, 33));
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return Collections.singletonList(new Pos2d(106, 33));
    }

    @Override
    protected void drawEnergyInfo(RecipeDisplayInfo recipeInfo) {
        long eut = recipeInfo.recipe.mEUt;
        long duration = recipeInfo.recipe.mDuration;
        recipeInfo.drawText(trans("152", "Total: ") + GT_Utility.formatNumbers(eut * duration) + " EU");
        recipeInfo.drawText(trans("153", "Usage: ") + GT_Utility.formatNumbers(eut) + " EU/t");
        recipeInfo.drawText(trans("158", "Time: ") + GT_Utility.formatNumbers(duration / 20) + " secs");

    }

    @Override
    protected void drawDurationInfo(RecipeDisplayInfo recipeInfo) {}

}
