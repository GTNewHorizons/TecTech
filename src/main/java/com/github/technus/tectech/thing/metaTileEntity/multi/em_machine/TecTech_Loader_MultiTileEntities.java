package com.github.technus.tectech.thing.metaTileEntity.multi.em_machine;

import gregtech.api.enums.Mods;
import gregtech.api.multitileentity.MultiTileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import static gregtech.GT_Mod.GT_FML_LOGGER;
import static gregtech.loaders.preload.GT_Loader_MultiTileEntities.MACHINE_BLOCK;
import static gregtech.loaders.preload.GT_Loader_MultiTileEntities.MACHINE_REGISTRY;

public class TecTech_Loader_MultiTileEntities implements Runnable {


    @Override
    public void run() {
        if (Mods.NewHorizonsCoreMod.isModLoaded()) {
            return;
        }
        GT_FML_LOGGER.info("GT_Mod: Registering MultiTileEntities");
        registerMachines();

        registerCasings();
    }

    private static void registerMachines() {
        // Disable for now
        MACHINE_REGISTRY.create(15, StarConsumer.class)
                .name("Star Consumer")
                .category("MultiblockController")
                .setBlock(MACHINE_BLOCK)
                .textureFolder("cokeOven")
                .inputInventorySize(1)
                .outputInventorySize(1)
                .register();
    }

    private static void registerCasings() {

/*
        CASING_REGISTRY.create(CokeOven.getId(), WallShareablePart.class)
                .name("Coke Oven Bricks")
                .category("MultiBlock Casing")
                .setBlock(CASING_BLOCK)
                .textureFolder("cokeOven")
                .register();
        CASING_REGISTRY.create(Chemical.getId(), BasicCasing.class)
                .name("Chemical Casing")
                .category("MultiBlock Casing")
                .setBlock(CASING_BLOCK)
                .textureFolder("advChemicalReactor")
                .register();
        CASING_REGISTRY.create(18000, BasicCasing.class)
                .name("Test Casing")
                .category("Multiblock Casing")
                .setBlock(CASING_BLOCK)
                .material(Materials.Cobalt)
                .textureFolder("macerator")
                .register();
*/

    }

}
