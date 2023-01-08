package com.github.technus.tectech.thing.casing;

import static com.github.technus.tectech.util.CommonValues.TEC_MARK_EM;
import static net.minecraft.util.StatCollector.translateToLocal;

import gregtech.common.blocks.GT_Item_Casings_Abstract;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

/**
 * Created by danie_000 on 03.10.2016.
 */
public class GT_Item_CasingsTT2 extends GT_Item_Casings_Abstract {
    public GT_Item_CasingsTT2(Block block) {
        super(block);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        aList.add(TEC_MARK_EM);
        switch (aStack.getItemDamage()) {
            case 0: // "Computer Air Cooler MK-I"
                aList.add(translateToLocal("gt.blockcasingsTT2.0.desc.0")); // The Ol' reliable.
                aList.add(EnumChatFormatting.AQUA.toString()
                        + EnumChatFormatting.BOLD
                        + translateToLocal("gt.blockcasingsTT2.0.desc.1")); // I hope you clean it from time to time.
                break;
            case 1: // "Computer Air Cooler MK-II"
                aList.add(translateToLocal("gt.blockcasingsTT2.1.desc.0")); // Like a jet engine.
                aList.add(EnumChatFormatting.AQUA.toString()
                        + EnumChatFormatting.BOLD
                        + translateToLocal("gt.blockcasingsTT2.1.desc.1")); // You clean it from time to time, right?
                break;
            case 2: // "Computer Liquid Cooler MK-I"
                aList.add(translateToLocal("gt.blockcasingsTT2.2.desc.0")); // Industrial AIO cooling solution.
                aList.add(EnumChatFormatting.AQUA.toString()
                        + EnumChatFormatting.BOLD
                        + translateToLocal("gt.blockcasingsTT2.2.desc.1")); // I hope it doesn't leak!
                break;
            case 3: // "Computer Liquid Cooler MK-II"
                aList.add(translateToLocal("gt.blockcasingsTT2.3.desc.0")); // Finally a custom loop.
                aList.add(EnumChatFormatting.AQUA.toString()
                        + EnumChatFormatting.BOLD
                        + translateToLocal("gt.blockcasingsTT2.3.desc.1")); // It uses colored liquid!
                break;
            default: // WTF?
                aList.add("Damn son where did you get that!?");
                aList.add(EnumChatFormatting.BLUE + "From outer space... I guess...");
        }
    }
}
