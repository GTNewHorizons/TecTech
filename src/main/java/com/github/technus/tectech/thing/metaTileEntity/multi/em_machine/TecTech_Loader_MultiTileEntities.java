package com.github.technus.tectech.thing.metaTileEntity.multi.em_machine;

import static gregtech.GT_Mod.GT_FML_LOGGER;
import static gregtech.loaders.preload.GT_Loader_MultiTileEntities.MACHINE_BLOCK;
import static gregtech.loaders.preload.GT_Loader_MultiTileEntities.MACHINE_REGISTRY;

import gregtech.api.enums.Mods;

public class TecTech_Loader_MultiTileEntities implements Runnable {

    @Override
    public void run() {
        if (Mods.NewHorizonsCoreMod.isModLoaded()) {
            return;
        }
        GT_FML_LOGGER.info("TecTech: Registering MultiTileEntities");
        registerMachines();

    }

    private static void registerMachines() {

        MACHINE_REGISTRY.create(4141, StarConsumer.class).name("Star Consumer").category("MultiblockController")
                .setBlock(MACHINE_BLOCK).textureFolder("starConsumer").inputInventorySize(1).outputInventorySize(32)
                .register();

    }

}
