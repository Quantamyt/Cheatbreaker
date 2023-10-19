package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.util.List;

/**
 * @WSPacket WSPacketClientCosmetics
 * @see WSPacket
 *
 * This packet sends back the full cosmetics list for profile saving.
 */
@Getter @NoArgsConstructor
public class WSPacketClientCosmetics extends WSPacket {
    private List<Cosmetic> cosmetics;

    public WSPacketClientCosmetics(List<Cosmetic> cosmeticsOut) {
        this.cosmetics = cosmeticsOut;
    }

    @Override
    public void write(PacketBuffer out) {
        out.writeInt(this.cosmetics.size());

        for (Cosmetic cosmetic : this.cosmetics) {
            out.writeLong(cosmetic.getLastUpdate());
            out.writeBoolean(cosmetic.isEquipped());
            out.writeString(cosmetic.getName());
            out.writeString(cosmetic.getType().getTypeName());
            out.writeFloat(cosmetic.getScale());
            out.writeString(cosmetic.getLocation().toString().replaceFirst("minecraft:", ""));
        }
    }

    @Override
    public void read(PacketBuffer ignored) {
    }

    @Override
    public void process(WSNetHandler ignored) {
    }
}
