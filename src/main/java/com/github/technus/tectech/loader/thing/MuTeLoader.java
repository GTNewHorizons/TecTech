package com.github.technus.tectech.loader.thing;

import static gregtech.loaders.preload.GT_Loader_MultiTileEntities.*;
import static gregtech.loaders.preload.GT_Loader_MultiTileEntities.CASING_BLOCK;

import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.thing.multiTileEntity.GodForge;

import gregtech.api.enums.Materials;
import gregtech.api.multitileentity.multiblock.casing.BasicCasing;

public class MuTeLoader implements Runnable {

    @Override
    public void run() {
        TecTech.LOGGER.info("TecTech: Registering MultiTileEntities");
        registerMachines();
        registerCasings();
    }

    private static void registerMachines() {
        MACHINE_REGISTRY.create(5000, GodForge.class).name("Forge of Gods").category("Multiblock Controller")
                .setBlock(MACHINE_BLOCK).material(Materials.Iron).textureFolder("godForge").tankCapacity(262144000L)
                .inputInventorySize(64).outputInventorySize(64).register();
    }

    private static void registerCasings() {
        CASING_REGISTRY.create(5000, BasicCasing.class).name("Spatially Confined Boundary Casing")
                .category("MultiBlock Casing").setBlock(CASING_BLOCK).textureFolder("godForge").register();
    }
}
