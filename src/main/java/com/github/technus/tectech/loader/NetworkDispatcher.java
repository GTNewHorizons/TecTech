package com.github.technus.tectech.loader;

import static com.github.technus.tectech.Reference.MODID;

import com.github.technus.tectech.mechanics.data.ChunkDataMessage;
import com.github.technus.tectech.mechanics.data.PlayerDataMessage;
import com.github.technus.tectech.mechanics.enderStorage.EnderLinkCoverMessage;
import com.github.technus.tectech.mechanics.pipe.PipeActivityMessage;
import com.github.technus.tectech.mechanics.spark.RendererMessage;
import com.github.technus.tectech.thing.metaTileEntity.hatch.TextParametersMessage;

public class NetworkDispatcher extends eu.usrv.yamcore.network.PacketDispatcher {
    public static NetworkDispatcher INSTANCE;

    public NetworkDispatcher() {
        super(MODID);
        INSTANCE = this;
        registerPackets();
    }

    @Override
    public void registerPackets() {
        registerMessage(PipeActivityMessage.ServerHandler.class, PipeActivityMessage.PipeActivityQuery.class);
        registerMessage(PipeActivityMessage.ClientHandler.class, PipeActivityMessage.PipeActivityData.class);

        registerMessage(ChunkDataMessage.ServerHandler.class, ChunkDataMessage.ChunkDataQuery.class);
        registerMessage(ChunkDataMessage.ClientHandler.class, ChunkDataMessage.ChunkDataData.class);

        registerMessage(PlayerDataMessage.ServerHandler.class, PlayerDataMessage.PlayerDataQuery.class);
        registerMessage(PlayerDataMessage.ClientHandler.class, PlayerDataMessage.PlayerDataData.class);

        registerMessage(RendererMessage.ClientHandler.class, RendererMessage.RendererData.class);

        registerMessage(EnderLinkCoverMessage.ServerHandler.class, EnderLinkCoverMessage.EnderLinkCoverQuery.class);
        registerMessage(
                EnderLinkCoverMessage.ServerUpdateHandler.class, EnderLinkCoverMessage.EnderLinkCoverUpdate.class);
        registerMessage(EnderLinkCoverMessage.ClientHandler.class, EnderLinkCoverMessage.EnderLinkCoverData.class);

        registerMessage(TextParametersMessage.ServerHandler.class, TextParametersMessage.ParametersTextQuery.class);
        registerMessage(
                TextParametersMessage.ServerUpdateHandler.class, TextParametersMessage.ParametersTextUpdate.class);
        registerMessage(TextParametersMessage.ClientHandler.class, TextParametersMessage.ParametersTextData.class);
    }
}
