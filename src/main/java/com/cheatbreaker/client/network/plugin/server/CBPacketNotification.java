package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @CBPacket CBPacketNotification
 * @see CBPacket
 *
 * This packet handles Notifications via CheatBreaker's Notification Module.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketNotification extends CBPacket {

    private String message;
    private long durationMs;
    private String level;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.message);
        out.getBuf().writeLong(this.durationMs);
        out.writeString(this.level);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.message = in.readString();
        this.durationMs = in.getBuf().readLong();
        this.level = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleNotification(this);
    }

}
