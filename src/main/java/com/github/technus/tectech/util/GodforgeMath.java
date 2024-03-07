package com.github.technus.tectech.util;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_ForgeOfGods;
import com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules.GT_MetaTileEntity_EM_BaseModule;
import com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules.GT_MetaTileEntity_EM_SmeltingModule;

public class GodforgeMath {

    public static double calculateFuelConsumption(GT_MetaTileEntity_EM_ForgeOfGods godforge) {
        double upgradeFactor = 1;
        if (godforge.isUpgradeActive(2)) {
            upgradeFactor = 0.8;
        }
        if (godforge.getFuelType() == 0) {
            return Math
                    .max(godforge.getFuelFactor() * 300 * Math.pow(1.15, godforge.getFuelFactor()) * upgradeFactor, 1);
        }
        if (godforge.getFuelType() == 1) {
            return Math.max(godforge.getFuelFactor() * 2 * Math.pow(1.08, godforge.getFuelFactor()) * upgradeFactor, 1);
        } else return Math.max(godforge.getFuelFactor() / 25 * upgradeFactor, 1);
    }

    public static Integer calculateMaxHeatForModules(GT_MetaTileEntity_EM_BaseModule module,
            GT_MetaTileEntity_EM_ForgeOfGods godforge) {
        double logBase = 1.5;
        int baseHeat = 12601;
        if (godforge.isUpgradeActive(12)) {
            if (module instanceof GT_MetaTileEntity_EM_SmeltingModule) {
                logBase = 1.12;
            } else {
                logBase = 1.18;
            }
        }
        return baseHeat + (int) (Math.log(godforge.getFuelFactor()) / Math.log(logBase) * 1000);
    }
}
