package com.github.technus.tectech.thing.cover;

import gregtech.api.interfaces.tileentity.ICoverable;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_TM_TeslaCoil_Ultimate extends GT_Cover_TM_TeslaCoil {
    public GT_Cover_TM_TeslaCoil_Ultimate() {}

    @Override
    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    @Override
    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    @Override
    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    @Override
    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    @Override
    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public byte getTeslaReceptionCapability() {
        return 1;
    }
}
