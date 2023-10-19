package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class WSPacketClientProcessList extends WSPacket {
    private List<String> processes;
    public static byte[] cbProcessBytes = new byte[]{36, -70, -63, 3, -116, 46, -121, -127, 117, 64, 58, 5, 75, 96, -63, 36};

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(this.processes.size());
        for (Object string : this.processes) {
            packetBuffer.writeStringToBuffer((String) string);
        }
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        int n = packetBuffer.readInt();
        this.processes = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            this.processes.add(packetBuffer.readStringFromBuffer(512));
        }
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }

    public WSPacketClientProcessList() {
    }

    @ConstructorProperties(value={"processes"})
    public WSPacketClientProcessList(List<String> processes) {
        this.processes = processes;
    }

    public List<String> getProcesses() {
        return this.processes;
    }
}
