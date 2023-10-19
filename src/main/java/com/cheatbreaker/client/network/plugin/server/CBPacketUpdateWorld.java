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
 * @CBPacket CBPacketUpdateWorld
 * @see CBPacket
 *
 * This packet updates the client's world.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketUpdateWorld extends CBPacket {

    private String world;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.world);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.world = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient)handler).handleUpdateWorld(this);
    }

}
