package com.cheatbreaker.client.network.plugin.shared;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CBPacket CBPacketRemoveWaypoint
 * @see CBPacket
 *
 * This packet removes a Waypoint to Zans Minimap.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class CBPacketRemoveWaypoint extends CBPacket {
    private String name;
    private String world;

    @Override
    public void write(ByteBufWrapper out) {
        out.writeString(this.name);
        out.writeString(this.world);
    }

    @Override
    public void read(ByteBufWrapper in) {
        this.name = in.readString();
        this.world = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        handler.handleRemoveWaypoint(this);
    }
}
