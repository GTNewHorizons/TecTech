package com.github.technus.tectech.loader.recipe;

import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.recipe.TT_recipeAdder;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;

public class ForgeOfGods implements Runnable {

    @Override
    public void run() {
        TT_recipeAdder.addFOGPlasmaRecipe(
                new ItemStack[] { Materials.Tritanium.getDust(1) },
                new FluidStack[] { Materials.Tritanium.getPlasma(144) },
                1 * SECONDS,
                (int) TierEU.RECIPE_MAX);

        TT_recipeAdder.addFOGPlasmaRecipe(
                new FluidStack[] { Materials.Helium.getGas(1000) },
                new FluidStack[] { Materials.Helium.getPlasma(1000) },
                1 * SECONDS,
                (int) TierEU.RECIPE_MAX);
    }
}
