package com.github.technus.tectech.util;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_ForgeOfGods;

public class GodforgeMath {

    public static Double calculateFuelConsumption(GT_MetaTileEntity_EM_ForgeOfGods godforge) {
        double upgradeFactor = 1;
        if (godforge.isUpgradeActive(2)) {
            upgradeFactor = 0.8;
        }
        if (godforge.getFuelType() == 0) {
            return godforge.getFuelFactor() * 300 * Math.pow(1.15, godforge.getFuelFactor()) * upgradeFactor;
        }
        if (godforge.getFuelType() == 1) {
            return godforge.getFuelFactor() * 2 * Math.pow(1.08, godforge.getFuelFactor()) * upgradeFactor;
        } else return godforge.getFuelFactor() / 25 * upgradeFactor;
    }
}
