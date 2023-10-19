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
 * @CBPacket CBPacketWorldBorder
 * @see CBPacket
 *
 * This packet adds a custom world border to the client.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketWorldBorder extends CBPacket {

    private String id;
    private String world;
    private boolean cancelsExit;
    private boolean canShrinkExpand;
    private int color = -13421569;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeOptional(this.id, out::writeString);
        out.writeString(this.world);
        out.getBuf().writeBoolean(this.cancelsExit);
        out.getBuf().writeBoolean(this.canShrinkExpand);
        out.getBuf().writeInt(this.color);
        out.getBuf().writeDouble(this.minX);
        out.getBuf().writeDouble(this.minZ);
        out.getBuf().writeDouble(this.maxX);
        out.getBuf().writeDouble(this.maxZ);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.id = in.readOptional(in::readString);
        this.world = in.readString();
        this.cancelsExit = in.getBuf().readBoolean();
        this.canShrinkExpand = in.getBuf().readBoolean();
        this.color = in.getBuf().readInt();
        this.minX = in.getBuf().readDouble();
        this.minZ = in.getBuf().readDouble();
        this.maxX = in.getBuf().readDouble();
        this.maxZ = in.getBuf().readDouble();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleWorldBorder(this);
    }

}
