package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.server.ICBNetHandlerServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CBPacket PacketClientVoice
 * @see CBPacket
 *
 * This CBPacket writes and reads voice data, from which the server and client
 * will send to each other for Voice Chat related transmissions.
 *
 * @implNote - The client will translate the byte data to actual sound.
 * The server will send the byte data to everyone in the current Voice Channel.
 */
@Getter @NoArgsConstructor @AllArgsConstructor
public class PacketClientVoice extends CBPacket {
    private byte[] data;

    @Override
    public void write(ByteBufWrapper out) {
        this.writeBlob(out, this.data);
    }

    @Override
    public void read(ByteBufWrapper in) {
        this.data = this.readBlob(in);
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerServer) handler).handleClientVoice(this);
    }
}
