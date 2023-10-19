package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketTeammates extends CBPacket {
    private UUID leader;
    private long lastMs;
    private Map<UUID, Map<String, Double>> players;

    public PacketTeammates() {
    }
    @ConstructorProperties(value={"leader", "lastMs", "players"})
    public PacketTeammates(UUID leader, long lastMs, Map<UUID, Map<String, Double>> players) {
        this.leader = leader;
        this.lastMs = lastMs;
        this.players = players;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.buf().writeBoolean(leader != null);

        if (leader != null) {
            buf.writeUUID(leader);
        }

        buf.buf().writeLong(lastMs);
        buf.writeVarInt(this.players.size());

        this.players.forEach((uuid, posMap) -> {
            buf.writeUUID(uuid);
            buf.writeVarInt(posMap.size());

            posMap.forEach((key, val) -> {
                buf.writeString(key);
                buf.buf().writeDouble(val);
            });
        });
    }

    @Override
    public void read(ByteBufWrapper buf) {
        if (buf.buf().readBoolean()) {
            this.leader = buf.readUUID();
        }

        this.lastMs = buf.buf().readLong();

        int playersSize = buf.readVarInt();
        this.players = new HashMap<>();

        for (int i = 0; i < playersSize; i++) {
            UUID uuid = buf.readUUID();
            int posMapSize = buf.readVarInt();
            Map<String, Double> posMap = new HashMap<>();

            for (int j = 0; j < posMapSize; j++) {
                String key = buf.readString();
                double val = buf.buf().readDouble();

                posMap.put(key, val);
            }

            this.players.put(uuid, posMap);
        }
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleTeammates(this);
    }

    public UUID getLeader() {
        return this.leader;
    }

    public long getLastMs() {
        return this.lastMs;
    }

    public Map<UUID, Map<String, Double>> getPlayers() {
        return this.players;
    }
}
