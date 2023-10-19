package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.server.ICBNetHandlerServer;

import java.util.UUID;

/**
 * @CBPacket PacketVoiceMute
 * @see CBPacket
 *
 * This CBPacket reads and writes player UUIDs for self muting and locally muting other players.
 */
public class PacketVoiceMute extends CBPacket {
    private UUID muting;

    public PacketVoiceMute() {
    }

    public PacketVoiceMute(UUID uUID) {
        this.muting = uUID;
    }

    @Override
    public void write(ByteBufWrapper out) {
        out.writeUUID(this.muting);
    }

    @Override
    public void read(ByteBufWrapper in) {
        this.muting = in.readUUID();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerServer) handler).handleVoiceMute(this);
    }

    public UUID getMuting() {
        return this.muting;
    }
}
