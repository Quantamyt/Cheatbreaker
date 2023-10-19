package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.util.List;

public class WSPacketClientCosmetics extends WSPacket {
    @Getter private List<Cosmetic> cosmetics;

    public WSPacketClientCosmetics() {
    }

    public WSPacketClientCosmetics(List<Cosmetic> var1) {
        this.cosmetics = var1;
    }

    @Override
    public void write(PacketBuffer out) {
        out.writeInt(this.cosmetics.size());
        for (Cosmetic cosmetic : this.cosmetics) {
            out.writeLong(cosmetic.getLastUpdate());
            out.writeBoolean(cosmetic.isEquipped());
            out.writeStringToBuffer(cosmetic.getName());
            out.writeStringToBuffer(cosmetic.getName());
            out.writeFloat(cosmetic.getScale());
            out.writeStringToBuffer(cosmetic.getLocation().toString().replaceFirst("minecraft:", ""));
        }
    }

    @Override
    public void read(PacketBuffer in) {
    }

    @Override
    public void process(WSNetHandler var1) {
    }
}
