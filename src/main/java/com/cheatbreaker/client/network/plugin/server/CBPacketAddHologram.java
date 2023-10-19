package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @CBPacket CBPacketAddHologram
 * @see CBPacket
 *
 * This packet adds a Hologram via the client's custom Hologram system.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketAddHologram extends CBPacket {
    private List<String> lines;

    private UUID uuid;

    private double x;
    private double y;
    private double z;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeUUID(this.uuid);
        out.getBuf().writeDouble(this.x);
        out.getBuf().writeDouble(this.y);
        out.getBuf().writeDouble(this.z);
        out.writeVarInt(this.lines.size());
        this.lines.forEach(out::writeString);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.uuid = in.readUUID();
        this.x = in.getBuf().readDouble();
        this.y = in.getBuf().readDouble();
        this.z = in.getBuf().readDouble();

        int linesSize = in.readVarInt();

        this.lines = new ArrayList<>();

        for (int i = 0; i < linesSize; ++i) {
            this.lines.add(in.readString());
        }
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleAddHologram(this);
    }
}
