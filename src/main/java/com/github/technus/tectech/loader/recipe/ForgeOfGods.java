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

        // GT solid to plasma recipes
        ItemStack[] solids = { Materials.Tritanium.getDust(1), Materials.Aluminium.getDust(1) };
        FluidStack[] solid_plasmas = { Materials.Tritanium.getPlasma(144), Materials.Aluminium.getPlasma(144) };

        for (int i = 0; i < solids.length; i++) {
            TT_recipeAdder.addFOGPlasmaRecipe(
                    new ItemStack[] { solids[i] },
                    new FluidStack[] { solid_plasmas[i] },
                    1 * SECONDS,
                    (int) TierEU.RECIPE_MAX);
        }

        // GT fluid to plasma recipes
        FluidStack[] fluids = { Materials.Helium.getGas(1000) };
        FluidStack[] fluid_plasmas = { Materials.Helium.getPlasma(1000) };

        for (int i = 0; i < fluids.length; i++) {
            TT_recipeAdder.addFOGPlasmaRecipe(
                    new FluidStack[] { fluids[i] },
                    new FluidStack[] { fluid_plasmas[i] },
                    1 * SECONDS,
                    (int) TierEU.RECIPE_MAX);
        }
    }
}
