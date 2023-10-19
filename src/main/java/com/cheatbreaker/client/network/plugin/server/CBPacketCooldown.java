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
 * @CBPacket CBPacketCooldown
 * @see CBPacket
 * @see com.cheatbreaker.client.module.impl.normal.hud.cooldowns.ModuleCooldowns
 *
 * This packet adds a Cooldown via CheatBreaker's Cooldown Module.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketCooldown extends CBPacket {

    private String message;
    private long durationMs;
    private int iconId;

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.message);
        out.getBuf().writeLong(this.durationMs);
        out.getBuf().writeInt(this.iconId);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.message = in.readString();
        this.durationMs = in.getBuf().readLong();
        this.iconId = in.getBuf().readInt();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient)handler).handleCooldown(this);
    }

}
