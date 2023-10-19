package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @CBPacket CBPacketTeammates
 * @see CBPacket
 *
 * This packet updates a player's teammate arrow.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketTeammates extends CBPacket {

    private UUID leader;

    private long lastMs;

    private Map<UUID, Map<String, Double>> players;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.getBuf().writeBoolean(this.leader != null);
        if (this.leader != null) {
            out.writeUUID(this.leader);
        }
        out.getBuf().writeLong(this.lastMs);
        out.writeVarInt(this.players.values().size());
        for (Map.Entry<UUID, Map<String, Double>> playerMap : this.players.entrySet()) {
            out.writeUUID(playerMap.getKey());
            out.writeVarInt(playerMap.getValue().values().size());
            for (Map.Entry<String, Double> posMap : playerMap.getValue().entrySet()) {
                out.writeString(posMap.getKey());
                out.getBuf().writeDouble(posMap.getValue());
            }
        }
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        boolean hasLeader = in.getBuf().readBoolean();
        if (hasLeader) {
            this.leader = in.readUUID();
        }
        this.lastMs = in.getBuf().readLong();
        int playersSize = in.readVarInt();
        this.players = new HashMap<>();
        for (int i = 0; i < playersSize; ++i) {
            UUID uuid = in.readUUID();
            int posMapSize = in.readVarInt();
            HashMap<String, Double> posMap = new HashMap<>();
            for (int j = 0; j < posMapSize; ++j) {
                String key = in.readString();
                double val = in.getBuf().readDouble();
                posMap.put(key, val);
            }
            this.players.put(uuid, posMap);
        }
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient)handler).handleTeammates(this);
    }

}
