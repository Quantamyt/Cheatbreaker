package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @CBPacket CBPacketWorldBorderRemove
 * @see CBPacket
 *
 * This packet removes a custom world border from the client.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketWorldBorderRemove extends CBPacket {

    private String id;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.id);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.id = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleWorldBorderRemove(this);
    }

}
