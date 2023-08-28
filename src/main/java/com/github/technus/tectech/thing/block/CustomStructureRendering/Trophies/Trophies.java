package com.github.technus.tectech.thing.block.CustomStructureRendering.Trophies;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderItemRenderer;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTESR;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderTileEntity;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.Util.RenderHelper;
import com.github.technus.tectech.thing.block.CustomStructureRendering.Structures.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import com.github.technus.tectech.thing.block.CustomStructureRendering.Base.BaseRenderBlock;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.HashMap;
import java.util.Set;

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
                MODID + ":ModelTrophy");

        TrophyItem = Item.getItemFromBlock(TrophyBlock);

        MinecraftForgeClient.registerItemRenderer(TrophyItem, new BaseTrophyItemRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(BaseTrophyTileEntity.class, new BaseTrophyTESR());

        registerAll();
    }


    private static final HashMap<String, BaseModelStructure> modelMap = new HashMap<>();

    public static void registerModel(final String label, final BaseModelStructure model) {
        modelMap.put(label, model);
    }

    public static Set<String> getModelList() {
        return modelMap.keySet();
    }

    public static BaseModelStructure getModel(final String modelName) {
        BaseModelStructure model = modelMap.getOrDefault(modelName, null);

        if (model == null) return modelMap.get("Default");

        return model;
    }

    private static void registerAll() {
        registerModel("Default", new Model_Default());
        registerModel("DTPF", new Model_DTPF());
        registerModel("Nano Forge", new Model_NanoForge());
    }
}
