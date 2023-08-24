package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base;

import static com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Base.BaseRenderTESR.MODEL_NAME_NBT_TAG;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.RenderHelper;

public class BaseRenderItemBlock extends ItemBlock {

    public BaseRenderItemBlock(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {

        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(MODEL_NAME_NBT_TAG)) {
            NBTTagCompound tag = itemStack.getTagCompound();

            return tag.getString(MODEL_NAME_NBT_TAG) + " Trophy";
        }

        return "NULL: Report to mod author";
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
        super.getSubItems(item, creativeTabs, itemList);

        // Removes the default item with no NBT in it.
        itemList.clear();

        // Add one for each model.
        for (String modelName : RenderHelper.getModelList()) {
            ItemStack itemStack = new ItemStack(item, 1, 0);

            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(MODEL_NAME_NBT_TAG, modelName);

            itemStack.setTagCompound(tag);

            itemList.add(itemStack);
        }
    }

}
