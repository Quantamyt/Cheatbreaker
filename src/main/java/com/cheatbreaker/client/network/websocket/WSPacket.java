package com.cheatbreaker.client.network.websocket;

import com.cheatbreaker.client.network.websocket.client.*;
import com.cheatbreaker.client.network.websocket.server.*;
import com.cheatbreaker.client.network.websocket.shared.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * This class defines what a websocket packet is for the CheatBreaker client.
 */
public abstract class WSPacket {

    public static BiMap<Class<? extends WSPacket>, Integer> REGISTRY = HashBiMap.create();

    /**
     * Writes outgoing data.
     * Example: I wrote a string using buf.writeString(); !
     */
    public abstract void write(PacketBuffer buf) throws IOException;

    /**
     * Reads incoming data.
     */
    public abstract void read(PacketBuffer buf) throws IOException;

    /**
     * This gets ran when a packet is received.
     * Example: When I receive the packet, a console message is received.
     */
    public abstract void process(WSNetHandler netHandler);

    /**
     * Writes the length of incoming traffic and the traffic itself short using the ByteBufWrapper.
     */
    protected void writeBlob(ByteBuf buf, byte[] data) {
        buf.writeShort(data.length);
        buf.writeBytes(data);
    }

    /**
     * Reads incoming data and returns it as bytes.
     */
    protected byte[] readBlob(ByteBuf buf) {
        short key = buf.readShort();
        if (key < 0) {
            System.out.println("[WS] Key was smaller than nothing!  Weird key!");
            return new byte[0];
        }
        byte[] data = new byte[key];
        buf.readBytes(data);
        return data;
    }

    /*
     * Registers all the websocket packets for use on the client.
     */
    static {
        REGISTRY.put(WSPacketJoinServer.class, 0);
        REGISTRY.put(WSPacketClientJoinServerResponse.class, 1);
        REGISTRY.put(WSPacketConsoleMessage.class, 2);
        REGISTRY.put(WSPacketNotification.class, 3);
        REGISTRY.put(WSPacketFriendsListUpdate.class, 4);
        REGISTRY.put(WSPacketFriendMessage.class, 5);
        REGISTRY.put(WSPacketServerUpdate.class, 6);
        REGISTRY.put(WSPacketBulkFriendRequest.class, 7);
        REGISTRY.put(WSPacketCosmetics.class, 8);
        REGISTRY.put(WSPacketFriendRequest.class, 9);
        REGISTRY.put(WSPacketFriendRequestSent.class, 16);
        REGISTRY.put(WSPacketClientFriendRemove.class, 17);
        REGISTRY.put(WSPacketFriendUpdate.class, 18);
        REGISTRY.put(WSPacketClientPlayerJoin.class, 19);
        REGISTRY.put(WSPacketClientCosmetics.class, 20);
        REGISTRY.put(WSPacketFriendAcceptOrDeny.class, 21);
        REGISTRY.put(WSPacketClientRequestsStatus.class, 22);
        REGISTRY.put(WSPacketClientCrashReport.class, 23);
        REGISTRY.put(WSPacketClientSync.class, 24);
        REGISTRY.put(WSPacketClientKeyResponse.class, 25);
        REGISTRY.put(WSPacketKeyRequest.class, 32);
        REGISTRY.put(WSPacketForceCrash.class, 33);
        REGISTRY.put(WSPacketClientProfilesExist.class, 34);
        REGISTRY.put(WSPacketRequestProcessList.class, 35);
        REGISTRY.put(WSPacketClientProcessList.class, 36);
        REGISTRY.put(WSPacketClientKeySync.class, 37);
        REGISTRY.put(WSPacketEmote.class, 39);
    }
}
