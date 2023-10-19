package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WSPacketFriendsListUpdate extends WSPacket {
    private boolean consoleAllowed;
    private boolean requestsEnabled;
    private Map<String, List<String>> onlineFriends;
    private Map<String, List<String>> offlineFriends;

    @Override
    public void write(PacketBuffer buffer) {
    }

    @Override
    public void read(PacketBuffer buffer) throws IOException {
        int n;
        this.consoleAllowed = buffer.readBoolean();
        this.requestsEnabled = buffer.readBoolean();
        int n2 = buffer.readInt();
        int n3 = buffer.readInt();
        this.onlineFriends = new HashMap();
        for (n = 0; n < n2; ++n) {
            this.onlineFriends.put(buffer.readStringFromBuffer(52), Arrays.asList(buffer.readStringFromBuffer(32), String.valueOf(buffer.readInt()), buffer.readStringFromBuffer(256)));
        }
        this.offlineFriends = new HashMap();
        for (n = 0; n < n3; ++n) {
            this.offlineFriends.put(buffer.readStringFromBuffer(52), Arrays.asList(buffer.readStringFromBuffer(32), String.valueOf(buffer.readLong())));
        }
    }

    @Override
    public void process(WSNetHandler handler) {
        handler.handleFriendsUpdate(this);
    }
}
