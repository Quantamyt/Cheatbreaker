package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * @WSPacket WSPacketFriendRequest
 * @see WSPacket
 *
 * This packet handles friend request sending and receiving.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketFriendUpdate extends WSPacket {
    private String playerId;
    private String name;
    private long offlineSince;
    private boolean online;

    @Override
    public void write(PacketBuffer var1) throws IOException {
        var1.writeString(this.playerId);
        var1.writeString(this.name);
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
}
