package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.UUID;

/**
 * @CBPacket CBPacketRemoveHologram
 * @see CBPacket
 *
 * This packet removes a Hologram from the custom CheatBreaker Hologram system.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketRemoveHologram extends CBPacket {

    private UUID uuid;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeUUID(this.uuid);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.uuid = in.readUUID();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient)handler).handleRemoveHologram(this);
    }

}
