package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;

@Getter
public class WSPacketConsoleMessage extends WSPacket {
    private String message;

    public WSPacketConsoleMessage() {
    }

    public WSPacketConsoleMessage(String string) {
        this.message = string;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeStringToBuffer(this.message);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.message = packetBuffer.readStringFromBuffer(32767);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleConsoleOutput(this);
    }
}
