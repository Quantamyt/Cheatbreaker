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
 * @CBPacket CBPacketServerUpdate
 * @see CBPacket
 *
 * This packet updates the server address on the client's Discord RPC.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketServerUpdate extends CBPacket {

    private String server;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.server);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.server = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient)handler).handleServerUpdate(this);
    }

}
