package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketUpdateNametags extends CBPacket {
    private Map<UUID, List<String>> playersMap;

    public PacketUpdateNametags() {
    }

    public PacketUpdateNametags(Map map) {
        this.playersMap = map;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeVarInt(this.playersMap == null ? -1 : this.playersMap.size());
        if (this.playersMap != null) {
            for (Map.Entry<UUID, List<String>> entry : this.playersMap.entrySet()) {
                UUID uUID = entry.getKey();
                List<String> list = entry.getValue();
                byteBufWrapper.writeUUID(uUID);
                byteBufWrapper.writeVarInt(list.size());
                for (String string : list) {
                    byteBufWrapper.writeString(string);
                }
            }
        }
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        int n = byteBufWrapper.readVarInt();
        if (n == -1) {
            this.playersMap = null;
            return;
        }
        this.playersMap = new HashMap();
        for (int i = 0; i < n; ++i) {
            UUID uUID = byteBufWrapper.readUUID();
            int n2 = byteBufWrapper.readVarInt();
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int j = 0; j < n2; ++j) {
                arrayList.add(byteBufWrapper.readString());
            }
            this.playersMap.put(uUID, arrayList);
        }
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleUpdateNametags(this);
    }

    public Map<UUID, List<String>> getPlayersMap() {
        return this.playersMap;
    }
}
