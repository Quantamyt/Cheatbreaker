package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientCrashReport
 * @see WSPacket
 *
 * This packet sends a crash report to the server, it also can take in one.
 */
@AllArgsConstructor
public class WSPacketClientCrashReport extends WSPacket {
    private String crashId;
    private String version;
    private String osInfo;
    private String memoryInfo;
    private String stackTrace;

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(this.crashId);
        buf.writeString(this.version);
        buf.writeString(this.osInfo);
        buf.writeString(this.memoryInfo);
        buf.writeString(this.stackTrace);
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
