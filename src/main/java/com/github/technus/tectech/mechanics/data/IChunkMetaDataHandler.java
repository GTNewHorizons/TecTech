package com.github.technus.tectech.mechanics.data;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;

public interface IChunkMetaDataHandler {
    String getTagName();

    void mergeData(NBTTagCompound target, NBTTagCompound loadedData);

    NBTTagCompound createData();

    @SideOnly(Side.CLIENT)
    default void pullData(ChunkEvent.Load aEvent) {}

    default void pushData(World world, ChunkCoordIntPair chunk) {}

    default void pushPayload(World world, ArrayList<ChunkCoordIntPair> chunk) {
        chunk.forEach(chunkCoordIntPair -> pushData(world, chunkCoordIntPair));
    }

    default int pushPayloadSpreadPeriod() {
        return 20; // must be a constant!
    }

    @SideOnly(Side.CLIENT)
    default void tickRender(HashMap<Integer, ChunkDataHandler.ChunkHashMap> data, TickEvent.RenderTickEvent event) {}

    @SideOnly(Side.CLIENT)
    default void tickClient(HashMap<Integer, ChunkDataHandler.ChunkHashMap> data, TickEvent.ClientTickEvent event) {}

    default void tickServer(HashMap<Integer, ChunkDataHandler.ChunkHashMap> data, TickEvent.ServerTickEvent event) {}

    default void tickWorld(HashMap<Integer, ChunkDataHandler.ChunkHashMap> data, TickEvent.WorldTickEvent event) {}

    default void tickPlayer(HashMap<Integer, ChunkDataHandler.ChunkHashMap> data, TickEvent.PlayerTickEvent event) {}
}
