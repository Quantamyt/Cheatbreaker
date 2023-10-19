package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.beans.ConstructorProperties;

@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketClientSync extends WSPacket {
    private int id;
    private double value;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(this.id);
        packetBuffer.writeDouble(this.value);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.id = packetBuffer.readInt();
        this.value = packetBuffer.readDouble();
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }
}
