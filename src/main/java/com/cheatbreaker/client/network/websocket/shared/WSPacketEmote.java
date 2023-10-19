package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor @Getter
public class WSPacketEmote extends WSPacket {
    public UUID playerId;
    public int emoteId;

    @Override
    public void write(PacketBuffer buf) throws IOException {
        buf.writeUUID(this.playerId);
        buf.writeInt(this.emoteId);
    }

    @Override
    public void read(PacketBuffer buf) throws IOException {
        this.playerId = buf.readUUID();
        this.emoteId = buf.readInt();
    }

    @Override
    public void process(WSNetHandler netHandler) {
        netHandler.handleEmote(this);
    }
}
