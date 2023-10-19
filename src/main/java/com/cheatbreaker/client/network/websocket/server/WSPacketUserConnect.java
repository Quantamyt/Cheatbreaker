package com.cheatbreaker.client.network.websocket.server;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.Getter;

import java.io.IOException;

@Deprecated
@Getter @NoArgsConstructor @AllArgsConstructor
public class WSPacketUserConnect extends WSPacket {

    private String playerId;
    private String username;
    private boolean join;

    @Override
    public void write(PacketBuffer buf) {
    }

    @Override
    public void read(PacketBuffer buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
        this.username = buf.readStringFromBuffer(16);
        this.join = buf.readBoolean();
    }

    @Override
    public void process(WSNetHandler wsNetHandler) {
//        wsNetHandler.handleUserConnect(this);
    }
}
