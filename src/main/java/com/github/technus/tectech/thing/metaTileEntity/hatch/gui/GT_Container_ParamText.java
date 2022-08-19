package com.github.technus.tectech.thing.metaTileEntity.hatch.gui;

import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.loader.NetworkDispatcher;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_ParamText;
import com.github.technus.tectech.thing.metaTileEntity.hatch.TextParametersMessage;
import com.github.technus.tectech.util.TT_Utility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import java.util.Objects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_ParamText extends GT_ContainerMetaTile_Machine {
    public int param = 0;
    public double value0d = 0;
    public double value1d = 0;
    public double input0d = 0;
    public double input1d = 0;
    public long value0l = 0;
    public long value1l = 0;
    public long input0l = 0;
    public long input1l = 0;
    public String value0s = "";
    public String value1s = "";

    public GT_Container_ParamText(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 8, 5, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 26, 5, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 152, 5, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 134, 5, false, false, 1));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 0) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) inventorySlots.get(aSlotIndex);
        if (tSlot != null && mTileEntity.getMetaTileEntity() != null) {
            boolean doStuff = true;
            GT_MetaTileEntity_Hatch_ParamText paramH =
                    (GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity();
            switch (aSlotIndex) {
                case 0:
                    paramH.param -= aShifthold == 1 ? 16 : 4;
                    break;
                case 1:
                    paramH.param -= aShifthold == 1 ? 2 : 1;
                    break;
                case 2:
                    paramH.param += aShifthold == 1 ? 16 : 4;
                    break;
                case 3:
                    paramH.param += aShifthold == 1 ? 2 : 1;
                    break;
                default:
                    doStuff = false;
            }
            if (doStuff) {
                IGregTechTileEntity base = paramH.getBaseMetaTileEntity();
                TecTech.proxy.playSound(base, "fx_click");
                if (paramH.param > 9) {
                    paramH.param = 9;
                } else if (paramH.param < -1) {
                    paramH.param = -1;
                }
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) {
            return;
        }
        param = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).param;
        value0d = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).value0D;
        value1d = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).value1D;
        input0d = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).input0D;
        input1d = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).input1D;
        for (Object crafter : crafters) {
            ICrafting var1 = (ICrafting) crafter;
            TT_Utility.sendInteger(param, this, var1, 100);
            TT_Utility.sendDouble(value0d, this, var1, 102);
            TT_Utility.sendDouble(value1d, this, var1, 106);
            TT_Utility.sendDouble(input0d, this, var1, 110);
            TT_Utility.sendDouble(input1d, this, var1, 114);
        }
        if (!Objects.equals(value0s, ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).value0s)
                || !Objects.equals(
                        value0s, ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).value0s)) {
            value0s = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).value0s;
            value1s = ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity()).value1s;
            for (Object object : crafters) {
                if (object instanceof EntityPlayerMP) {
                    NetworkDispatcher.INSTANCE.sendTo(
                            new TextParametersMessage.ParametersTextData(
                                    ((GT_MetaTileEntity_Hatch_ParamText) mTileEntity.getMetaTileEntity())),
                            (EntityPlayerMP) object);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
            case 101:
                param = TT_Utility.receiveInteger(param, 100, par1, par2);
                return;
            case 102:
            case 103:
            case 104:
            case 105:
                value0d = Double.longBitsToDouble(value0l = TT_Utility.receiveLong(value0l, 102, par1, par2));
                return;
            case 106:
            case 107:
            case 108:
            case 109:
                value1d = Double.longBitsToDouble(value1l = TT_Utility.receiveLong(value1l, 106, par1, par2));
                return;
            case 110:
            case 111:
            case 112:
            case 113:
                input0d = Double.longBitsToDouble(input0l = TT_Utility.receiveLong(input0l, 110, par1, par2));
                return;
            case 114:
            case 115:
            case 116:
            case 117:
                input1d = Double.longBitsToDouble(input1l = TT_Utility.receiveLong(input1l, 114, par1, par2));
                return;
            default:
        }
    }
}
