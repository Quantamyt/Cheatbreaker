package com.cheatbreaker.client.network.websocket;

import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.shared.WSPacketFriendAcceptOrDeny;
import com.cheatbreaker.client.network.websocket.client.*;
import com.cheatbreaker.client.network.websocket.server.*;
import com.cheatbreaker.client.network.websocket.shared.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public abstract class WSPacket {
    public static BiMap<Class<? extends WSPacket>, Integer> REGISTRY = HashBiMap.create();

    public abstract void write(PacketBuffer buf) throws IOException;

    public abstract void read(PacketBuffer buf) throws IOException;

    public abstract void process(WSNetHandler netHandler);

    protected void writeBlob(ByteBuf buf, byte[] data) {
        buf.writeShort(data.length);
        buf.writeBytes(data);
    }

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
//        REGISTRY.put(WSPacketUserConnect.class, 38);
        REGISTRY.put(WSPacketEmote.class, 39);
    }
}
