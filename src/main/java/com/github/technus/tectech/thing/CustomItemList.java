package com.github.technus.tectech.thing;

import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static gregtech.api.enums.GT_Values.W;

public enum CustomItemList implements IItemContainer {
    Casing_UEV, Casing_UIV, Casing_UMV, Casing_UXV, Casing_OPV, Casing_MAXV,
    Hull_UEV, Hull_UIV, Hull_UMV, Hull_UXV, Hull_OPV, Hull_MAXV,
    Transformer_UEV_UHV, Transformer_UIV_UEV, Transformer_UMV_UIV,
    Transformer_UXV_UMV, Transformer_OPV_UXV, Transformer_MAXV_OPV,
    WetTransformer_LV_ULV, WetTransformer_MV_LV, WetTransformer_HV_MV, WetTransformer_EV_HV,
    WetTransformer_IV_EV, WetTransformer_LuV_IV, WetTransformer_ZPM_LuV, WetTransformer_UV_ZPM,
    WetTransformer_UHV_UV, WetTransformer_UEV_UHV, WetTransformer_UIV_UEV, WetTransformer_UMV_UIV,
    WetTransformer_UXV_UMV, WetTransformer_OPV_UXV, WetTransformer_MAXV_OPV,

    Transformer_HA_UEV_UHV, Transformer_HA_UIV_UEV, Transformer_HA_UMV_UIV, Transformer_HA_UXV_UMV,
    Transformer_HA_OPV_UXV, Transformer_HA_MAXV_OPV,

    hatch_CreativeMaintenance, hatch_CreativeData,
    Machine_OwnerDetector, Machine_DataReader,
    Machine_BuckConverter_IV, Machine_BuckConverter_LuV, Machine_BuckConverter_ZPM,
    Machine_BuckConverter_UV, Machine_BuckConverter_UHV, Machine_BuckConverter_UEV,
    Machine_BuckConverter_UIV, Machine_BuckConverter_UMV, Machine_BuckConverter_UXV,
    Machine_DebugWriter, Machine_DebugGenny, UnusedStuff, Machine_DebugPollutor,
    EMpipe, DATApipe, LASERpipe, rack_Hatch, holder_Hatch, capacitor_Hatch,

    eM_dynamoMulti4_IV, eM_dynamoMulti16_IV, eM_dynamoMulti64_IV,
    eM_dynamoMulti4_LuV, eM_dynamoMulti16_LuV, eM_dynamoMulti64_LuV,
    eM_dynamoMulti4_ZPM, eM_dynamoMulti16_ZPM, eM_dynamoMulti64_ZPM,
    eM_dynamoMulti4_UV, eM_dynamoMulti16_UV, eM_dynamoMulti64_UV,
    eM_dynamoMulti4_UHV, eM_dynamoMulti16_UHV, eM_dynamoMulti64_UHV,
    eM_dynamoMulti4_UEV, eM_dynamoMulti16_UEV, eM_dynamoMulti64_UEV,
    eM_dynamoMulti4_UIV, eM_dynamoMulti16_UIV, eM_dynamoMulti64_UIV,
    eM_dynamoMulti4_UMV, eM_dynamoMulti16_UMV, eM_dynamoMulti64_UMV,
    eM_dynamoMulti4_UXV, eM_dynamoMulti16_UXV, eM_dynamoMulti64_UXV,
    eM_dynamoTunnel1_IV, eM_dynamoTunnel2_IV, eM_dynamoTunnel3_IV, eM_dynamoTunnel4_IV, eM_dynamoTunnel5_IV, eM_dynamoTunnel6_IV, eM_dynamoTunnel7_IV,
    eM_dynamoTunnel1_LuV, eM_dynamoTunnel2_LuV, eM_dynamoTunnel3_LuV, eM_dynamoTunnel4_LuV, eM_dynamoTunnel5_LuV, eM_dynamoTunnel6_LuV, eM_dynamoTunnel7_LuV,
    eM_dynamoTunnel1_ZPM, eM_dynamoTunnel2_ZPM, eM_dynamoTunnel3_ZPM, eM_dynamoTunnel4_ZPM, eM_dynamoTunnel5_ZPM, eM_dynamoTunnel6_ZPM, eM_dynamoTunnel7_ZPM,
    eM_dynamoTunnel1_UV, eM_dynamoTunnel2_UV, eM_dynamoTunnel3_UV, eM_dynamoTunnel4_UV, eM_dynamoTunnel5_UV, eM_dynamoTunnel6_UV, eM_dynamoTunnel7_UV,
    eM_dynamoTunnel1_UHV, eM_dynamoTunnel2_UHV, eM_dynamoTunnel3_UHV, eM_dynamoTunnel4_UHV, eM_dynamoTunnel5_UHV, eM_dynamoTunnel6_UHV, eM_dynamoTunnel7_UHV,
    eM_dynamoTunnel1_UEV, eM_dynamoTunnel2_UEV, eM_dynamoTunnel3_UEV, eM_dynamoTunnel4_UEV, eM_dynamoTunnel5_UEV, eM_dynamoTunnel6_UEV, eM_dynamoTunnel7_UEV,
    eM_dynamoTunnel1_UIV, eM_dynamoTunnel2_UIV, eM_dynamoTunnel3_UIV, eM_dynamoTunnel4_UIV, eM_dynamoTunnel5_UIV, eM_dynamoTunnel6_UIV, eM_dynamoTunnel7_UIV,
    eM_dynamoTunnel1_UMV, eM_dynamoTunnel2_UMV, eM_dynamoTunnel3_UMV, eM_dynamoTunnel4_UMV, eM_dynamoTunnel5_UMV, eM_dynamoTunnel6_UMV, eM_dynamoTunnel7_UMV,
    eM_dynamoTunnel1_UXV, eM_dynamoTunnel2_UXV, eM_dynamoTunnel3_UXV, eM_dynamoTunnel4_UXV, eM_dynamoTunnel5_UXV, eM_dynamoTunnel6_UXV, eM_dynamoTunnel7_UXV,
    eM_dynamoTunnel9001,

    eM_energyMulti4_IV, eM_energyMulti16_IV, eM_energyMulti64_IV,
    eM_energyMulti4_LuV, eM_energyMulti16_LuV, eM_energyMulti64_LuV,
    eM_energyMulti4_ZPM, eM_energyMulti16_ZPM, eM_energyMulti64_ZPM,
    eM_energyMulti4_UV, eM_energyMulti16_UV, eM_energyMulti64_UV,
    eM_energyMulti4_UHV, eM_energyMulti16_UHV, eM_energyMulti64_UHV,
    eM_energyMulti4_UEV, eM_energyMulti16_UEV, eM_energyMulti64_UEV,
    eM_energyMulti4_UIV, eM_energyMulti16_UIV, eM_energyMulti64_UIV,
    eM_energyMulti4_UMV, eM_energyMulti16_UMV, eM_energyMulti64_UMV,
    eM_energyMulti4_UXV, eM_energyMulti16_UXV, eM_energyMulti64_UXV,
    eM_energyTunnel1_IV, eM_energyTunnel2_IV, eM_energyTunnel3_IV, eM_energyTunnel4_IV, eM_energyTunnel5_IV, eM_energyTunnel6_IV, eM_energyTunnel7_IV,
    eM_energyTunnel1_LuV, eM_energyTunnel2_LuV, eM_energyTunnel3_LuV, eM_energyTunnel4_LuV, eM_energyTunnel5_LuV, eM_energyTunnel6_LuV, eM_energyTunnel7_LuV,
    eM_energyTunnel1_ZPM, eM_energyTunnel2_ZPM, eM_energyTunnel3_ZPM, eM_energyTunnel4_ZPM, eM_energyTunnel5_ZPM, eM_energyTunnel6_ZPM, eM_energyTunnel7_ZPM,
    eM_energyTunnel1_UV, eM_energyTunnel2_UV, eM_energyTunnel3_UV, eM_energyTunnel4_UV, eM_energyTunnel5_UV, eM_energyTunnel6_UV, eM_energyTunnel7_UV,
    eM_energyTunnel1_UHV, eM_energyTunnel2_UHV, eM_energyTunnel3_UHV, eM_energyTunnel4_UHV, eM_energyTunnel5_UHV, eM_energyTunnel6_UHV, eM_energyTunnel7_UHV,
    eM_energyTunnel1_UEV, eM_energyTunnel2_UEV, eM_energyTunnel3_UEV, eM_energyTunnel4_UEV, eM_energyTunnel5_UEV, eM_energyTunnel6_UEV, eM_energyTunnel7_UEV,
    eM_energyTunnel1_UIV, eM_energyTunnel2_UIV, eM_energyTunnel3_UIV, eM_energyTunnel4_UIV, eM_energyTunnel5_UIV, eM_energyTunnel6_UIV, eM_energyTunnel7_UIV,
    eM_energyTunnel1_UMV, eM_energyTunnel2_UMV, eM_energyTunnel3_UMV, eM_energyTunnel4_UMV, eM_energyTunnel5_UMV, eM_energyTunnel6_UMV, eM_energyTunnel7_UMV,
    eM_energyTunnel1_UXV, eM_energyTunnel2_UXV, eM_energyTunnel3_UXV, eM_energyTunnel4_UXV, eM_energyTunnel5_UXV, eM_energyTunnel6_UXV, eM_energyTunnel7_UXV,
    eM_energyTunnel9001,

    eM_in_UV, eM_in_UHV, eM_in_UEV, eM_in_UIV, eM_in_UMV, eM_in_UXV,
    eM_out_UV, eM_out_UHV, eM_out_UEV, eM_out_UIV, eM_out_UMV, eM_out_UXV,
    eM_muffler_UV, eM_muffler_UHV, eM_muffler_UEV, eM_muffler_UIV, eM_muffler_UMV, eM_muffler_UXV,
    Parametrizer_Hatch, ParametrizerX_Hatch, ParametrizerTXT_Hatch, Uncertainty_Hatch, UncertaintyX_Hatch, dataIn_Hatch, dataOut_Hatch, dataInAss_Hatch, dataOutAss_Hatch,
    eM_Containment, eM_Containment_Field, eM_Containment_Advanced, eM_Coil, eM_Teleportation, eM_Dimensional, eM_Ultimate_Containment, eM_Ultimate_Containment_Advanced, eM_Ultimate_Containment_Field, eM_Spacetime, eM_Computer_Casing, eM_Computer_Bus, eM_Computer_Vent, eM_Hollow, eM_Power,
    debugBlock,

    tM_TeslaBase, tM_TeslaToroid, tM_TeslaSecondary, tM_TeslaPrimary_0, tM_TeslaPrimary_1, tM_TeslaPrimary_2, tM_TeslaPrimary_3, tM_TeslaPrimary_4, tM_TeslaPrimary_5, tM_TeslaPrimary_6,

    Machine_Multi_Microwave, Machine_Multi_TeslaCoil,
    Machine_Multi_Transformer,
    Machine_Multi_Computer, Machine_Multi_Switch, Machine_Multi_Research, Machine_Multi_DataBank,
    Machine_Multi_MatterToEM, Machine_Multi_EMToMatter, Machine_Multi_EMjunction, Machine_Multi_Scanner,
    Machine_Multi_EssentiaToEM, Machine_Multi_EMToEssentia,
    Machine_Multi_Infuser, Machine_Multi_Collider,
    Machine_Multi_Decay, Machine_Multi_EMmachine,
    Machine_Multi_Stabilizer, Machine_Multi_EMCrafter,
    Machine_Multi_Wormhole, Machine_Multi_Annihilation,
    Machine_Multi_BHG,
    hint_0, hint_1, hint_2, hint_3, hint_4, hint_5, hint_6, hint_7, hint_8, hint_9, hint_10, hint_11, hint_general, hint_air, hint_noAir, hint_error,

    eM_avr_HV, eM_avr_EV, eM_avr_IV, eM_avr_LuV, eM_avr_ZPM, eM_avr_UV, eM_avr_UHV, eM_avr_UEV, eM_avr_UIV, eM_avr_UMV,

    scanContainer, parametrizerMemory, teslaCapacitor, teslaCover, teslaComponent, teslaStaff, enderLinkFluidCover, powerPassUpgradeCover,

    Machine_TeslaCoil_1by1_LV, Machine_TeslaCoil_1by1_MV, Machine_TeslaCoil_1by1_HV, Machine_TeslaCoil_1by1_EV, Machine_TeslaCoil_1by1_IV,
    Machine_TeslaCoil_2by2_LV, Machine_TeslaCoil_2by2_MV, Machine_TeslaCoil_2by2_HV, Machine_TeslaCoil_2by2_EV, Machine_TeslaCoil_2by2_IV,
    Machine_TeslaCoil_3by3_LV, Machine_TeslaCoil_3by3_MV, Machine_TeslaCoil_3by3_HV, Machine_TeslaCoil_3by3_EV, Machine_TeslaCoil_3by3_IV,
    Machine_TeslaCoil_4by4_LV, Machine_TeslaCoil_4by4_MV, Machine_TeslaCoil_4by4_HV, Machine_TeslaCoil_4by4_EV, Machine_TeslaCoil_4by4_IV;

    private ItemStack mStack;
    private boolean mHasNotBeenSet = true;

    //public static Fluid sOilExtraHeavy, sOilHeavy, sOilMedium, sOilLight, sNaturalGas;

    @Override
    public IItemContainer set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null) {
            return this;
        }
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = GT_Utility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        mHasNotBeenSet = false;
        mStack = GT_Utility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public Item getItem() {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return null;
        }
        return mStack.getItem();
    }

    @Override
    public Block getBlock() {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        return GT_Utility.getBlockFromStack(new ItemStack(getItem()));
    }

    @Override
    public final boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    @Override
    public boolean isStackEqual(Object aStack) {
        return isStackEqual(aStack, false, false);
    }

    @Override
    public boolean isStackEqual(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
        if (GT_Utility.isStackInvalid(aStack)) {
            return false;
        }
        return GT_Utility.areUnificationsEqual((ItemStack) aStack, aWildcard ? getWildcard(1) : get(1), aIgnoreNBT);
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmount(aAmount, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, W, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, 0, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, mStack.getMaxDamage() - 1, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GT_Utility.isStackInvalid(rStack)) {
            return null;
        }
        rStack.setStackDisplayName(aDisplayName);
        return GT_Utility.copyAmount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GT_Utility.isStackInvalid(rStack)) {
            return null;
        }
        GT_ModHandler.chargeElectricItem(rStack, aEnergy, Integer.MAX_VALUE, true, false);
        return GT_Utility.copyAmount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GT_Utility.isStackInvalid(mStack)) {
            return GT_Utility.copyAmount(aAmount, aReplacements);
        }
        return GT_Utility.copyAmountAndMetaData(aAmount, aMetaValue, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        for (Object tOreName : aOreNames) {
            GT_OreDictUnificator.registerOre(tOreName, get(1));
        }
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        for (Object tOreName : aOreNames) {
            GT_OreDictUnificator.registerOre(tOreName, getWildcard(1));
        }
        return this;
    }
}
