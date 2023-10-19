package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.CryptManager;

import javax.crypto.SecretKey;
import java.security.PublicKey;

/**
 * @WSPacket WSPacketClientJoinServerResponse
 * @see WSPacket
 *
 * This packet sends encrypted data when it joins the server.
 */
public class WSPacketClientJoinServerResponse extends WSPacket {
    private final byte[] secretKey;
    private final byte[] publicKey;

    public WSPacketClientJoinServerResponse(SecretKey secretKey, PublicKey publicKey, byte[] data) {
        this.secretKey = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.publicKey = CryptManager.encryptData(publicKey, data);
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        this.writeBlob(packetBuffer, this.secretKey);
        this.writeBlob(packetBuffer, this.publicKey);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }
}
