package com.github.technus.tectech.thing.block.CustomStructureRendering.Trophies;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderItemRenderer;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTESR;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTileEntity;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.Util.RenderHelper;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderBlock;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import static com.github.technus.tectech.Reference.MODID;

public abstract class Trophies {

    public static final Block TrophyBlock = new BaseTrophyBlock("% Trophy");
    public static Item TrophyItem;


    // These two registrations happen at different stages of the minecraft loading process,
    // hence why we have different methods for them.
    public static void registerBlock() {
        GameRegistry.registerBlock(TrophyBlock, BaseTrophyItemBlock.class, "% Trophy");
    }

    public static void registerRenderer() {
        GameRegistry.registerTileEntity(
                BaseTrophyTileEntity.class,
                MODID + ":BaseGregTechTrophy");

        TrophyItem = Item.getItemFromBlock(TrophyBlock);

        MinecraftForgeClient.registerItemRenderer(TrophyItem, new BaseTrophyItemRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(BaseTrophyTileEntity.class, new BaseTrophyTESR());

        RenderHelper.registerAll();
    }
}
