package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

public class PacketTitle extends CBPacket {
    private String type;
    private String message;
    private long displayTimeMs;
    private long fadeInTimeMs;
    private long fadeOutTimeMs;
    private float scale;

    public PacketTitle() {
    }

    public PacketTitle(String type, String message, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this(type, message, 1.0f, displayTimeMs, fadeInTimeMs, fadeOutTimeMs);
    }

    public PacketTitle(String type, String message, float scale, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this.type = type;
        this.message = message;
        this.scale = scale;
        this.displayTimeMs = displayTimeMs;
        this.fadeInTimeMs = fadeInTimeMs;
        this.fadeOutTimeMs = fadeOutTimeMs;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.type);
        byteBufWrapper.writeString(this.message);
        byteBufWrapper.buf().writeFloat(this.scale);
        byteBufWrapper.buf().writeLong(this.displayTimeMs);
        byteBufWrapper.buf().writeLong(this.fadeInTimeMs);
        byteBufWrapper.buf().writeLong(this.fadeOutTimeMs);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.type = byteBufWrapper.readString();
        this.message = byteBufWrapper.readString();
        this.scale = byteBufWrapper.buf().readFloat();
        this.displayTimeMs = byteBufWrapper.buf().readLong();
        this.fadeInTimeMs = byteBufWrapper.buf().readLong();
        this.fadeOutTimeMs = byteBufWrapper.buf().readLong();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleTitle(this);
    }

    public String getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public long getDisplayTimeMs() {
        return this.displayTimeMs;
    }

    public long getFadeInTimeMs() {
        return this.fadeInTimeMs;
    }

    public long getFadeOutTimeMs() {
        return this.fadeOutTimeMs;
    }

    public float getScale() {
        return this.scale;
    }
}
