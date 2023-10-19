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
 * @CBPacket CBPacketStaffModState
 * @see CBPacket
 *
 * This packet updates the player's staff mod state.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketStaffModState extends CBPacket {

    private String mod;

    private boolean state;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.mod);
        out.getBuf().writeBoolean(this.state);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.mod = in.readString();
        this.state = in.getBuf().readBoolean();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleStaffModState(this);
    }

}
