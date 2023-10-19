package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WSPacketBulkFriendRequest extends WSPacket {
    private String rawFriendRequests;
    private JsonArray bulkArray;

    @Override
    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeStringToBuffer(this.rawFriendRequests);
    }

    @Override
    public void read(PacketBuffer buffer) throws IOException {
        this.rawFriendRequests = buffer.readStringFromBuffer(32767);
        this.bulkArray = new JsonParser().parse(this.rawFriendRequests).getAsJsonObject().getAsJsonArray("bulk");
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleBulkFriends(this);
    }
}
