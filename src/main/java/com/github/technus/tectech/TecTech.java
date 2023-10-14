package com.github.technus.tectech;

import static com.github.technus.tectech.loader.TecTechConfig.DEBUG_MODE;
import static gregtech.api.enums.Mods.COFHCore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;

import net.minecraftforge.common.MinecraftForge;

import com.github.technus.tectech.loader.MainLoader;
import com.github.technus.tectech.loader.TecTechConfig;
import com.github.technus.tectech.loader.gui.CreativeTabTecTech;
import com.github.technus.tectech.mechanics.data.ChunkDataHandler;
import com.github.technus.tectech.mechanics.data.PlayerPersistence;
import com.github.technus.tectech.mechanics.enderStorage.EnderWorldSavedData;
import com.github.technus.tectech.nei.IMCForNEI;
import com.github.technus.tectech.proxy.CommonProxy;
import com.github.technus.tectech.recipe.EyeOfHarmonyRecipeStorage;
import com.github.technus.tectech.util.XSTR;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import eu.usrv.yamcore.auxiliary.IngameErrorLog;
import eu.usrv.yamcore.auxiliary.LogHelper;
import gregtech.GT_Mod;
import gregtech.common.GT_Proxy;

@Mod(
        modid = Reference.MODID,
        name = Reference.NAME,
        version = Reference.VERSION,
        dependencies = "required-after:Forge@[10.13.4.1614,);" + "required-after:YAMCore@[0.5.70,);"
                + "required-after:structurelib;"
                + "after:ComputerCraft;"
                + "after:OpenComputers;"
                + "required-after:gtneioreplugin;"
                + "required-after:gregtech;"
                + "after:dreamcraft;"
                + "after:appliedenergistics2;"
                + "after:CoFHCore;"
                + "after:Thaumcraft;")
public class TecTech {

    @SidedProxy(clientSide = Reference.CLIENTSIDE, serverSide = Reference.SERVERSIDE)
    public static CommonProxy proxy;

    @Mod.Instance(Reference.MODID)
    public static TecTech instance;

    public static final XSTR RANDOM = XSTR.XSTR_INSTANCE;
    public static final LogHelper LOGGER = new LogHelper(Reference.MODID);
    public static CreativeTabTecTech creativeTabTecTech;

    private static IngameErrorLog moduleAdminErrorLogs;
    public static TecTechConfig configTecTech;

    public static EnderWorldSavedData enderWorldSavedData;
    public static ChunkDataHandler chunkDataHandler;
    public static PlayerPersistence playerPersistence;

    /**
     * For Loader.isModLoaded checks during the runtime
     */
    public static boolean hasCOFH = false;

    public static final byte tectechTexturePage1 = 8;

    public static void AddLoginError(String pMessage) {
        if (moduleAdminErrorLogs != null) {
            moduleAdminErrorLogs.AddErrorLogOnAdminJoin(pMessage);
        }
    }

    @Mod.EventHandler
    public void PreLoad(FMLPreInitializationEvent PreEvent) {
        LOGGER.setDebugOutput(true);

        configTecTech = new TecTechConfig(
                PreEvent.getModConfigurationDirectory(),
                Reference.COLLECTIONNAME,
                Reference.MODID);

        if (!configTecTech.LoadConfig()) {
            LOGGER.error(Reference.MODID + " could not load its config file. Things are going to be weird!");
        }

        if (configTecTech.MOD_ADMIN_ERROR_LOGS) {
            LOGGER.setDebugOutput(DEBUG_MODE);
            LOGGER.debug("moduleAdminErrorLogs is enabled");
            moduleAdminErrorLogs = new IngameErrorLog();
        }

        playerPersistence = new PlayerPersistence("tec");
        FMLCommonHandler.instance().bus().register(playerPersistence);
        MinecraftForge.EVENT_BUS.register(playerPersistence);

        chunkDataHandler = new ChunkDataHandler();
        FMLCommonHandler.instance().bus().register(chunkDataHandler);
        MinecraftForge.EVENT_BUS.register(chunkDataHandler);

        enderWorldSavedData = new EnderWorldSavedData();
        FMLCommonHandler.instance().bus().register(enderWorldSavedData);
        MinecraftForge.EVENT_BUS.register(enderWorldSavedData);

        MainLoader.preLoad();
    }

    @Mod.EventHandler
    public void Load(FMLInitializationEvent event) {
        hasCOFH = COFHCore.isModLoaded();

        MainLoader.load();
        MainLoader.addAfterGregTechPostLoadRunner();
        IMCForNEI.IMCSender();
    }

    @Mod.EventHandler
    public void PostLoad(FMLPostInitializationEvent PostEvent) {
        MainLoader.postLoad();
    }

    @Mod.EventHandler
    public void onLoadCompleted(FMLLoadCompleteEvent event) {
        eyeOfHarmonyRecipeStorage = new EyeOfHarmonyRecipeStorage();
    }

    public static EyeOfHarmonyRecipeStorage eyeOfHarmonyRecipeStorage = null;

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent aEvent) {
        chunkDataHandler.clearData();
        playerPersistence.clearData();
    }
}
