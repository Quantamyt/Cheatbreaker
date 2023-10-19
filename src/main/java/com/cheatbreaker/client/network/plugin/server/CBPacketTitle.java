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
 * @CBPacket CBPacketTitle
 * @see CBPacket
 *
 * This packet adds a title via the CheatBreaker Title System.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketTitle extends CBPacket {
    private String type;
    private String message;

    private long displayTimeMs;
    private long fadeInTimeMs;
    private long fadeOutTimeMs;

    private float scale;

    public CBPacketTitle(String type, String message, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this(type, message, 1.0f, displayTimeMs, fadeInTimeMs, fadeOutTimeMs);
    }

    public CBPacketTitle(String type, String message, float scale, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this.type = type;
        this.message = message;
        this.scale = scale;
        this.displayTimeMs = displayTimeMs;
        this.fadeInTimeMs = fadeInTimeMs;
        this.fadeOutTimeMs = fadeOutTimeMs;
    }

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.type);
        out.writeString(this.message);
        out.getBuf().writeFloat(this.scale);
        out.getBuf().writeLong(this.displayTimeMs);
        out.getBuf().writeLong(this.fadeInTimeMs);
        out.getBuf().writeLong(this.fadeOutTimeMs);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.type = in.readString();
        this.message = in.readString();
        this.scale = in.getBuf().readFloat();
        this.displayTimeMs = in.getBuf().readLong();
        this.fadeInTimeMs = in.getBuf().readLong();
        this.fadeOutTimeMs = in.getBuf().readLong();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleTitle(this);
    }
}
