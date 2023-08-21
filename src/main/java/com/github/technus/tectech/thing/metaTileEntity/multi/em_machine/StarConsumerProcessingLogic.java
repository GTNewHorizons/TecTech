package com.github.technus.tectech.thing.metaTileEntity.multi.em_machine;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GT_OreDictUnificator;

public final class StarConsumerProcessingLogic extends ProcessingLogic {

    @Override
    public @Nonnull CheckRecipeResult process() {
        if (inputItems == null || inputItems[0] == null) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        ItemStack celestialBody = inputItems[0];

        // List<ItemStack> output = new ArrayList<ItemStack>();
        // output.add(GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Europium, 2));
        // output.add(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 2));

        celestialBody.stackSize -= 1;
        setDuration(1);

        setOutputItems(
                GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Europium, 2),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 2));

        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

}
