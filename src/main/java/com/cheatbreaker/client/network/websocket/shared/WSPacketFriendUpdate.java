package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.io.IOException;

public class WSPacketFriendUpdate extends WSPacket {
    private String playerId;
    private String name;
    private long offlineSince;
    private boolean online;

    public WSPacketFriendUpdate() {
    }

    public WSPacketFriendUpdate(String var1, String var2, long var3, boolean var5) {
        this.playerId = var1;
        this.name = var2;
        this.offlineSince = var3;
        this.online = var5;
    }

    @Override
    public void write(PacketBuffer var1) throws IOException {
        var1.writeStringToBuffer(this.playerId);
        var1.writeStringToBuffer(this.name);
        var1.writeLong(this.offlineSince);
        var1.writeBoolean(this.online);

    }

    @Override
    public void read(PacketBuffer var1) throws IOException {
        this.playerId = var1.readStringFromBuffer(52);
        this.name = var1.readStringFromBuffer(32);
        this.offlineSince = var1.readLong();
        this.online = var1.readBoolean();
        System.out.println(name);
    }

    @Override
    public void process(WSNetHandler var1) {
        var1.handleFriendUpdate(this);
    }

    public String getMessage() {
        return this.playerId;
    }

    public String getName() {
        return this.name;
    }

    public long getOfflineSince() {
        return this.offlineSince;
    }

    public boolean isOnline() {
        return this.online;
    }
}
