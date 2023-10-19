package com.cheatbreaker.client.network.plugin;

import com.cheatbreaker.client.network.plugin.shared.PacketAddWaypoint;
import com.cheatbreaker.client.network.plugin.shared.PacketRemoveWaypoint;

public interface ICBNetHandler {
    void handleAddWaypoint(PacketAddWaypoint var1);

    void handleRemoveWaypoint(PacketRemoveWaypoint var1);
}
