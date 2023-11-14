package com.github.technus.tectech.mechanics.dataTransport;

import static com.github.technus.tectech.recipe.TT_recipeAdder.nullItem;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WirelessInventoryDataPacket extends DataPacket<ItemStack[]> {

    public WirelessInventoryDataPacket(ItemStack[] content) {
        super(content);
    }

    public WirelessInventoryDataPacket(NBTTagCompound compound) {
        super(compound);
    }

    @Override
    protected ItemStack[] contentFromNBT(NBTTagCompound nbt) {
        int count = nbt.getInteger("count");
        if (count > 0) {
            ArrayList<ItemStack> stacks = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                ItemStack stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(Integer.toString(i)));
                if (stack != null) {
                    stacks.add(stack);
                }
            }
            return stacks.size() > 0 ? stacks.toArray(nullItem) : null;
        }
        return null;
    }

    @Override
    protected NBTTagCompound contentToNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        if (content != null && content.length > 0) {
            compound.setInteger("count", content.length);
            for (int i = 0; i < content.length; i++) {
                compound.setTag(Integer.toString(i), content[i].writeToNBT(new NBTTagCompound()));
            }
        }
        return compound;
    }

    @Override
    public boolean extraCheck() {
        // Nothing here yet
        return true;
    }

    @Override
    protected ItemStack[] unifyContentWith(ItemStack[] content) {
        // Nothing here yet
        return content;
    }

    @Override
    public String getContentString() {
        return "Wireless Stack Count: " + (content == null ? 0 : content.length);
    }
}
