package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * @WSPacket WSPacketCosmetics
 * @see WSPacket
 *
 * This packet receives all cosmetics being sent to the client.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WSPacketCosmetics extends WSPacket {
    private List<Cosmetic> cosmetics = new ArrayList<>();
    private String playerId;
    private int emoteId;

    private String username;
    private boolean join;
    private int color;
    private int color2;

    @Override
    public void write(PacketBuffer out) {
    }

    @Override
    public void read(PacketBuffer in) {
        this.playerId = in.readStringFromBuffer(52);

        int cosmeticsSize = in.readInt();

        for (int i = 0; i < cosmeticsSize; i++) {
            long time = in.readLong();
            float scale = in.readFloat();
            boolean active = in.readBoolean();
            String resourceLocation = in.readStringFromBuffer(512);
            String name = in.readStringFromBuffer(128);
            Cosmetic.CosmeticType type = Cosmetic.CosmeticType.get(in.readStringFromBuffer(128));

            assert type != null;
            if (type.getTypeName().equals("emote")) {
                this.cosmetics.add(new Cosmetic(this.playerId, Integer.parseInt(name), type));
            } else {
                this.cosmetics.add(new Cosmetic(time, this.playerId, name, type, scale, active, resourceLocation));
            }
        }

        this.username = in.readStringFromBuffer(16);
        this.join = in.readBoolean();
        this.color = in.readInt();
        this.color2 = in.readInt();
    }

    @Override
    public void process(WSNetHandler handler) {
        handler.handleCosmetics(this);
    }
}