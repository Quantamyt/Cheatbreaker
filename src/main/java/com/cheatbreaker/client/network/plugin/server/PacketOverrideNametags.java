package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PacketOverrideNametags extends CBPacket {
    private UUID uuid;
    private List<String> tags;

    public PacketOverrideNametags() {
    }

    public PacketOverrideNametags(UUID uUID, List list) {
        this.uuid = uUID;
        this.tags = list;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeUUID(this.uuid);
        buf.writeOptional(this.tags, new Consumer() {
            @Override
            public void accept(Object o) {
                buf.writeVarInt(tags.size());
                tags.forEach(buf::writeString);
            }
        });
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.uuid = byteBufWrapper.readUUID();
        this.tags = (List)byteBufWrapper.readOptional(() -> {
            int n = byteBufWrapper.readVarInt();
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < n; ++i) {
                arrayList.add(byteBufWrapper.readString());
            }
            return arrayList;
        });
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleOverrideNametags(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public List<String> getTags() {
        return this.tags;
    }
}
