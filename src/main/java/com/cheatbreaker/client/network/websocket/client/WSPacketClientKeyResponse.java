package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.beans.ConstructorProperties;

public class WSPacketClientKeyResponse extends WSPacket {
    private byte[] data;

    @Override
    public void write(PacketBuffer packetBuffer) {
        this.writeBlob(packetBuffer, this.data);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.data = this.readBlob(packetBuffer);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }

    public byte[] getData() {
        return this.data;
    }

    @ConstructorProperties(value={"data"})
    public WSPacketClientKeyResponse(byte[] data) {
        this.data = data;
    }

    public WSPacketClientKeyResponse() {
    }
}
