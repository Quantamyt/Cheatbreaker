package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;
import net.minecraft.util.CryptManager;

import java.security.PublicKey;
import javax.crypto.SecretKey;

public class WSPacketClientJoinServerResponse extends WSPacket {
    private byte[] secretKey = new byte[0];
    private byte[] publicKey = new byte[0];

    public WSPacketClientJoinServerResponse(SecretKey secretKey, PublicKey publicKey, byte[] arrby) {
        this.secretKey = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.publicKey = CryptManager.encryptData(publicKey, arrby);
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
