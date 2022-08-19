package com.github.technus.tectech.loader.thing;

import static com.github.technus.tectech.TecTech.tectechTexturePage1;

import com.github.technus.tectech.Reference;
import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.compatibility.openmodularturrets.blocks.turretbases.TurretBaseEM;
import com.github.technus.tectech.compatibility.openmodularturrets.blocks.turretheads.TurretHeadEM;
import com.github.technus.tectech.thing.block.QuantumGlassBlock;
import com.github.technus.tectech.thing.block.QuantumStuffBlock;
import com.github.technus.tectech.thing.block.ReactorSimBlock;
import com.github.technus.tectech.thing.casing.GT_Block_CasingsBA0;
import com.github.technus.tectech.thing.casing.GT_Block_CasingsNH;
import com.github.technus.tectech.thing.casing.GT_Block_CasingsTT;
import com.github.technus.tectech.thing.casing.TT_Container_Casings;
import com.github.technus.tectech.thing.item.*;
import cpw.mods.fml.common.Loader;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;

/**
 * Created by danie_000 on 16.11.2016.
 */
public class ThingsLoader implements Runnable {
    @Override
    public void run() {
        if (Textures.BlockIcons.casingTexturePages[tectechTexturePage1] == null) {
            Textures.BlockIcons.casingTexturePages[tectechTexturePage1] = new ITexture[128];
        }
        TecTech.LOGGER.info("Added texture page if was null");
        if (!Loader.isModLoaded(Reference.DREAMCRAFT)) {
            TT_Container_Casings.sBlockCasingsNH = new GT_Block_CasingsNH();
            TecTech.LOGGER.info("Adding basic casings");
        }
        TT_Container_Casings.sBlockCasingsTT = new GT_Block_CasingsTT();
        TecTech.LOGGER.info("Elemental Casing registered");
        TT_Container_Casings.sBlockCasingsBA0 = new GT_Block_CasingsBA0();
        TecTech.LOGGER.info("Nikolai's Casing registered");

        QuantumGlassBlock.run();
        TecTech.LOGGER.info("Quantum Glass registered");

        QuantumStuffBlock.run();
        TecTech.LOGGER.info("Quantum Stuff registered");

        if (Loader.isModLoaded("openmodularturrets")) {
            TurretHeadEM.run();
            TecTech.LOGGER.info("TurretHeadEM registered");
            TurretBaseEM.run();
            TecTech.LOGGER.info("TurretBaseEM registered");
        }

        ReactorSimBlock.run();
        TecTech.LOGGER.info("Reactor Simulator registered");

        ParametrizerMemoryCard.run();
        ElementalDefinitionScanStorage_EM.run();
        EuMeterGT.run();
        TeslaStaff.run();
        TeslaCoilCover.run();
        TeslaCoilCapacitor.run();
        EnderFluidLinkCover.run();
        PowerPassUpgradeCover.run();
        TecTech.LOGGER.info("Useful Items registered");

        TeslaCoilComponent.run();
        TecTech.LOGGER.info("Crafting Components registered");

        ElementalDefinitionContainer_EM.run();
        DebugElementalInstanceContainer_EM.run();
        TecTech.LOGGER.info("Debug Items registered");
    }
}
