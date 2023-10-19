package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.server.ICBNetHandlerServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @CBPacket PacketVoiceChannelSwitch
 * @see CBPacket
 *
 * This CBPacket reads and writes channel UUIDs for successful Voice Channel switching.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class PacketVoiceChannelSwitch extends CBPacket {
    private UUID switchingTo;

    @Override
    public void write(ByteBufWrapper out) {
        out.writeUUID(this.switchingTo);
    }

    @Override
    public void read(ByteBufWrapper in) {
        this.switchingTo = in.readUUID();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerServer) handler).handleVoiceChannelSwitch(this);
    }
}
