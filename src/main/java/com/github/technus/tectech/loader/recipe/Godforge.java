package com.github.technus.tectech.loader.recipe;

import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.github.technus.tectech.recipe.TT_recipeAdder;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gtPlusPlus.core.material.ELEMENT;

public class Godforge implements Runnable {

    @Override
    public void run() {

        // Solid to plasma recipes
        {
            // Fusion tier 1-3
            {
                // Single step
                ItemStack[] solids_t0_1step = { Materials.Aluminium.getDust(1) };
                FluidStack[] solid_plasmas_t0_1step = { Materials.Aluminium.getPlasma(144) };

                for (int i = 0; i < solids_t0_1step.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new ItemStack[] { solids_t0_1step[i] },
                            new FluidStack[] { solid_plasmas_t0_1step[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            false,
                            0);
                }

                // Multi-step
                ItemStack[] solids_t0_xstep = { Materials.Force.getDust(1) };
                FluidStack[] solid_plasmas_t0_xstep = { new FluidStack(ELEMENT.STANDALONE.FORCE.getPlasma(), 144) };

                for (int i = 0; i < solids_t0_xstep.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new ItemStack[] { solids_t0_xstep[i] },
                            new FluidStack[] { solid_plasmas_t0_xstep[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            true,
                            0);
                }
            }
            // Fusion tier 4-5
            {
                // Single step
                ItemStack[] solids_t1_1step = { Materials.Iron.getDust(1), Materials.Lead.getDust(1) };
                FluidStack[] solid_plasmas_t1_1step = { Materials.Iron.getPlasma(144), Materials.Lead.getPlasma(144) };

                for (int i = 0; i < solids_t1_1step.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new ItemStack[] { solids_t1_1step[i] },
                            new FluidStack[] { solid_plasmas_t1_1step[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            false,
                            1);
                }

                // Multi-step
                ItemStack[] solids_t1_xstep = { Materials.Bismuth.getDust(1),
                        ELEMENT.STANDALONE.ADVANCED_NITINOL.getDust(1) };
                FluidStack[] solid_plasmas_t1_xstep = { Materials.Bismuth.getPlasma(144),
                        new FluidStack(ELEMENT.STANDALONE.ADVANCED_NITINOL.getPlasma(), 144) };

                for (int i = 0; i < solids_t1_xstep.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new ItemStack[] { solids_t1_xstep[i] },
                            new FluidStack[] { solid_plasmas_t1_xstep[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            true,
                            1);
                }
            }
            // Exotic Plasmas
            {
                // Single step
                ItemStack[] solids_t2_1step = { ELEMENT.STANDALONE.RHUGNOR.getDust(1) };
                FluidStack[] solid_plasmas_t2_1step = { new FluidStack(ELEMENT.STANDALONE.RHUGNOR.getPlasma(), 144) };

                for (int i = 0; i < solids_t2_1step.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new ItemStack[] { solids_t2_1step[i] },
                            new FluidStack[] { solid_plasmas_t2_1step[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            false,
                            2);
                }

                // Multi-step
                ItemStack[] solids_t2_xstep = { ELEMENT.STANDALONE.HYPOGEN.getDust(1), Materials.Tritanium.getDust(1) };
                FluidStack[] solid_plasmas_t2_xstep = { new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getPlasma(), 144),
                        Materials.Tritanium.getPlasma(144) };

                for (int i = 0; i < solids_t2_xstep.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new ItemStack[] { solids_t2_xstep[i] },
                            new FluidStack[] { solid_plasmas_t2_xstep[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            true,
                            2);
                }

            }

        }

        // Fluid to plasma recipes
        {
            // Fusion tier 1-3
            {
                // Single step
                FluidStack[] fluids_t0_1step = { Materials.Helium.getGas(1000) };
                FluidStack[] fluid_plasmas_t0_1step = { Materials.Helium.getPlasma(1000) };

                for (int i = 0; i < fluids_t0_1step.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new FluidStack[] { fluids_t0_1step[i] },
                            new FluidStack[] { fluid_plasmas_t0_1step[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            false,
                            0);
                }

                // Multi-step
                FluidStack[] fluids_t0_xstep = { ELEMENT.getInstance().NEON.getFluidStack(1000) };
                FluidStack[] fluid_plasmas_t0_xstep = { new FluidStack(ELEMENT.getInstance().NEON.getPlasma(), 1000) };

                for (int i = 0; i < fluids_t0_xstep.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new FluidStack[] { fluids_t0_xstep[i] },
                            new FluidStack[] { fluid_plasmas_t0_xstep[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            true,
                            0);
                }
            }
            // Fusion tier 4-5
            {
                // Single step
                FluidStack[] fluids_t1_1step = { Materials.Radon.getGas(1000) };
                FluidStack[] fluid_plasmas_t1_1step = { Materials.Radon.getPlasma(1000) };

                for (int i = 0; i < fluids_t1_1step.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new FluidStack[] { fluids_t1_1step[i] },
                            new FluidStack[] { fluid_plasmas_t1_1step[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            false,
                            1);
                }

                // Multi-step
                FluidStack[] fluids_t1_xstep = { ELEMENT.getInstance().XENON.getFluidStack(1000) };
                FluidStack[] fluid_plasmas_t1_xstep = { new FluidStack(ELEMENT.getInstance().XENON.getPlasma(), 1000) };

                for (int i = 0; i < fluids_t1_xstep.length; i++) {
                    TT_recipeAdder.addFOGPlasmaRecipe(
                            new FluidStack[] { fluids_t1_xstep[i] },
                            new FluidStack[] { fluid_plasmas_t1_xstep[i] },
                            1 * SECONDS,
                            (int) TierEU.RECIPE_MAX,
                            true,
                            1);
                }
            }
            // Exotic
            {
                // None yet
            }
        }
    }
}
