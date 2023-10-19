package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import net.minecraft.network.PacketBuffer;

@AllArgsConstructor
public class WSPacketClientCrashReport extends WSPacket {
    private String crashId;
    private String version;
    private String osInfo;
    private String memoryInfo;
    private String stackTrace;

    @Override
    public void write(PacketBuffer buf) {
        buf.writeStringToBuffer(this.crashId);
        buf.writeStringToBuffer(this.version);
        buf.writeStringToBuffer(this.osInfo);
        buf.writeStringToBuffer(this.memoryInfo);
        buf.writeStringToBuffer(this.stackTrace);
    }

    @Override
    public void read(PacketBuffer buf) {
        this.crashId = buf.readStringFromBuffer(100);
        this.version = buf.readStringFromBuffer(100);
        this.osInfo = buf.readStringFromBuffer(500);
        this.memoryInfo = buf.readStringFromBuffer(500);
        this.stackTrace = buf.readStringFromBuffer(10000);
    }

    @Override
    public void process(WSNetHandler var1) {
    }
}
