package com.github.technus.tectech.recipe;

import static com.github.technus.tectech.loader.recipe.Godforge.exoticModulePlasmaFluidMap;
import static com.github.technus.tectech.loader.recipe.Godforge.exoticModulePlasmaItemMap;
import static gregtech.api.util.GT_Utility.trans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.thing.gui.TecTechUITextures;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;

import codechicken.nei.PositionedStack;
import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.GT_NEI_DefaultHandler;
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
    public void drawNEIOverlays(GT_NEI_DefaultHandler.CachedDefaultRecipe neiCachedRecipe) {

        List<ItemStack> cyclingDusts = new ArrayList<>(exoticModulePlasmaItemMap.keySet());
        neiCachedRecipe.mInputs.set(0, new PositionedStack(cyclingDusts, 48, 23, true));

        List<FluidStack> fluids = new ArrayList<>(exoticModulePlasmaFluidMap.keySet());
        List<ItemStack> fluidItems = new ArrayList<>();
        for (FluidStack fluid : fluids) {
            fluidItems.add(GT_Utility.getFluidDisplayStack(fluid, true));
        }
        neiCachedRecipe.mInputs.add(0, new PositionedStack(fluidItems, 48, 52, true));

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
