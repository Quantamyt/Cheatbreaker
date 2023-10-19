package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.CryptManager;

import java.security.PublicKey;

/**
 * @WSPacket WSPacketJoinServer
 * @see WSPacket
 *
 * This packet gets fired when a player joins the server.
 */
@Getter @NoArgsConstructor @AllArgsConstructor
public class WSPacketJoinServer extends WSPacket {
    private PublicKey publicKey;
    private byte[] bytes;

    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.publicKey = CryptManager.decodePublicKey(this.readBlob(packetBuffer));
        this.bytes = this.readBlob(packetBuffer);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleJoinServer(this);
    }
}
