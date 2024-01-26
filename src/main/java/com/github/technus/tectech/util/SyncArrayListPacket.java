package com.github.technus.tectech.util;

import com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_quantumBank;
import com.glodblock.github.util.BlockPos;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.multitileentity.base.MultiTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import java.io.IOException;
import java.util.ArrayList;

public class SyncArrayListPacket implements IMessage {

    private ArrayList<String> syncedList;
    private static GT_MetaTileEntity_EM_quantumBank quantumTE;

    public SyncArrayListPacket() { } //Satisfy forge w/ empty constructor

    public SyncArrayListPacket(@Nonnull final ArrayList<String> dataList){
        this.syncedList = dataList;
    }

    public SyncArrayListPacket(@Nonnull final ArrayList<String> dataList, GT_MetaTileEntity_EM_quantumBank dataTE) {
        this.syncedList = dataList;
        this.quantumTE = dataTE;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        int size = packetBuffer.readInt();
        syncedList = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            try {
                syncedList.add(packetBuffer.readStringFromBuffer(32767));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeInt(syncedList.size());
        for (String str : syncedList) {
            try {
                packetBuffer.writeStringToBuffer(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getSyncedList(){
        return syncedList;
    }

    public static GT_MetaTileEntity_EM_quantumBank getQuantumTE() {return quantumTE;}

    public static class Handler implements IMessageHandler<SyncArrayListPacket, IMessage> {
        public IMessage onMessage(SyncArrayListPacket message, MessageContext ctx) {
            if (ctx.side.isServer()){
                   // getQuantumTE().sendArrayListToClient(message.getSyncedList(), ctx.getServerHandler().playerEntity);
                    System.out.println("onMessage:Server:Client -> " + message.getSyncedList().size());
            } else {
                if (getQuantumTE() == null) {
                   // GT_MetaTileEntity_EM_quantumBank.setFriendsList(message.getSyncedList());
                } else {
                   // getQuantumTE().setFriendsList(message.getSyncedList());
                    System.out.println("onMessage:Client:Server -> " + message.getSyncedList().size());
                }
            }
            return null;
        }

    }

    private GT_MetaTileEntity_EM_quantumBank getTEFromContext(MessageContext ctx){
       // ctx.getClientHandler().
        return null;
    }
}
