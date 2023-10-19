package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientSync
 * @see WSPacket
 *
 * Unknown usage. Reads an Integer and a Double.
 */
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
