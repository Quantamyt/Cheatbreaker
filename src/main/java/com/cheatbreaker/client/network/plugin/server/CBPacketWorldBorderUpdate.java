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
 * @CBPacket CBPacketWorldBorderUpdate
 * @see CBPacket
 *
 * This packet updates an already existing custom world border in the client.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketWorldBorderUpdate extends CBPacket {

    private String id;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;
    private int durationTicks;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.id);
        out.getBuf().writeDouble(this.minX);
        out.getBuf().writeDouble(this.minZ);
        out.getBuf().writeDouble(this.maxX);
        out.getBuf().writeDouble(this.maxZ);
        out.getBuf().writeInt(this.durationTicks);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.id = in.readString();
        this.minX = in.getBuf().readDouble();
        this.minZ = in.getBuf().readDouble();
        this.maxX = in.getBuf().readDouble();
        this.maxZ = in.getBuf().readDouble();
        this.durationTicks = in.getBuf().readInt();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleWorldBorderUpdate(this);
    }

}
