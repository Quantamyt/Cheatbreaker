package com.cheatbreaker.client.network.plugin.shared;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CBPacket CBPacketAddWaypoint
 * @see CBPacket
 *
 * This packet adds a Waypoint to Zans Minimap.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class CBPacketAddWaypoint extends CBPacket {

    private String name;
    private String world;

    private int color;
    private int x;
    private int y;
    private int z;

    private boolean forced;
    private boolean visible;

    @Override
    public void write(ByteBufWrapper out) {
        out.writeString(this.name);
        out.writeString(this.world);
        out.getBuf().writeInt(this.color);
        out.getBuf().writeInt(this.x);
        out.getBuf().writeInt(this.y);
        out.getBuf().writeInt(this.z);
        out.getBuf().writeBoolean(this.forced);
        out.getBuf().writeBoolean(this.visible);
    }

    @Override
    public void read(ByteBufWrapper in) {
        this.name = in.readString();
        this.world = in.readString();
        this.color = in.getBuf().readInt();
        this.x = in.getBuf().readInt();
        this.y = in.getBuf().readInt();
        this.z = in.getBuf().readInt();
        this.forced = in.getBuf().readBoolean();
        this.visible = in.getBuf().readBoolean();
    }

    @Override
    public void process(ICBNetHandler handler) {
        handler.handleAddWaypoint(this);
    }
}
