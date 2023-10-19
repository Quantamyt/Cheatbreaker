package com.cheatbreaker.client.network.plugin;

import com.cheatbreaker.client.network.plugin.shared.CBPacketAddWaypoint;
import com.cheatbreaker.client.network.plugin.shared.CBPacketRemoveWaypoint;

public interface ICBNetHandler {

    void handleAddWaypoint(CBPacketAddWaypoint var1);

    void handleRemoveWaypoint(CBPacketRemoveWaypoint var1);

}
