package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketVoiceChannel extends CBPacket {
    private UUID uuid;
    private String name;
    private Map<UUID, String> players;
    private Map<UUID, String> listening;

    public PacketVoiceChannel() {
    }

    public PacketVoiceChannel(UUID uUID, String string, Map map, Map map2) {
        this.uuid = uUID;
        this.name = string;
        this.players = map;
        this.listening = map2;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.uuid);
        byteBufWrapper.writeString(this.name);
        this.lIIIIlIIllIIlIIlIIIlIIllI(byteBufWrapper, this.players);
        this.lIIIIlIIllIIlIIlIIIlIIllI(byteBufWrapper, this.listening);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.uuid = byteBufWrapper.readUUID();
        this.name = byteBufWrapper.readString();
        this.players = this.IIIIllIlIIIllIlllIlllllIl(byteBufWrapper);
        this.listening = this.IIIIllIlIIIllIlllIlllllIl(byteBufWrapper);
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ByteBufWrapper byteBufWrapper, Map<UUID, String> map) {
        byteBufWrapper.writeVarInt(map.size());
        for (Map.Entry<UUID, String> entry : map.entrySet()) {
            byteBufWrapper.writeUUID(entry.getKey());
            byteBufWrapper.writeString(entry.getValue());
        }
    }

    private Map<UUID, String> IIIIllIlIIIllIlllIlllllIl(ByteBufWrapper byteBufWrapper) {
        int n = byteBufWrapper.readVarInt();
        HashMap<UUID, String> hashMap = new HashMap<UUID, String>();
        for (int i = 0; i < n; ++i) {
            UUID uUID = byteBufWrapper.readUUID();
            String string = byteBufWrapper.readString();
            hashMap.put(uUID, string);
        }
        return hashMap;
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleVoiceChannel(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public Map<UUID, String> getPlayers() {
        return this.players;
    }

    public Map<UUID, String> getListening() {
        return this.listening;
    }
}
