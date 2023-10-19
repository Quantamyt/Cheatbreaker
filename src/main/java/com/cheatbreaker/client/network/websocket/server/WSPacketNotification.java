package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WSPacketNotification extends WSPacket {
    private String title;
    private String content;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeStringToBuffer(this.title);
        packetBuffer.writeStringToBuffer(this.content);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.title = packetBuffer.readStringFromBuffer(128);
        this.content = packetBuffer.readStringFromBuffer(512);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleFormattedConsoleOutput(this);
    }
}
