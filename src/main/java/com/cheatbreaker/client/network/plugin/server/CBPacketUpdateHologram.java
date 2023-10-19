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
 * @CBPacket CBPacketUpdateHologram
 * @see CBPacket
 *
 * This packet updates an already existing Hologram via the CheatBreaker Hologram System.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketUpdateHologram extends CBPacket {

    private UUID uuid;
    private List<String> lines;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeUUID(this.uuid);
        out.writeVarInt(this.lines.size());
        for (String string : this.lines) {
            out.writeString(string);
        }
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.uuid = in.readUUID();
        int linesSize = in.readVarInt();
        this.lines = new ArrayList<>();
        for (int i = 0; i < linesSize; ++i) {
            this.lines.add(in.readString());
        }
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleUpdateHologram(this);
    }

}
