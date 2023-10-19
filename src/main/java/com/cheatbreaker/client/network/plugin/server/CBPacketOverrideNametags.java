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
 * @CBPacket CBPacketOverrideNametags
 * @see CBPacket
 *
 * This packet overrides a player's nametag with a custom one.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketOverrideNametags extends CBPacket {

    private UUID playerId;
    private List<String> tags;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeUUID(this.playerId);
        out.writeOptional(this.tags, t -> {
            out.writeVarInt(t.size());
            t.forEach(out::writeString);
        });
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.playerId = in.readUUID();
        this.tags = in.readOptional(() -> {
            int tagsSize = in.readVarInt();
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 0; i < tagsSize; ++i) {
                tags.add(in.readString());
            }
            return tags;
        });
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient)handler).handleOverrideNametags(this);
    }

}
