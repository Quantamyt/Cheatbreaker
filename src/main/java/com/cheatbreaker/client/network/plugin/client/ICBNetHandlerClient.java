package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.server.*;

public interface ICBNetHandlerClient extends ICBNetHandler {
    void handleCooldown(PacketCooldown var1);

    void handleNotification(PacketNotification var1);

    void handleStaffModState(PacketStaffModState var1);

    void handleUpdateNametags(PacketUpdateNametags var1);

    void handleTeammates(PacketTeammates var1);

    void handleOverrideNametags(PacketOverrideNametags var1);

    void handleAddHologram(PacketAddHologram var1);

    void handleUpdateHologram(PacketUpdateHologram var1);

    void handleRemoveHologram(PacketRemoveHologram var1);

    void handleTitle(PacketTitle var1);

    void handleServerRule(PacketServerRule var1);

    void handleVoice(PacketVoice var1);

    void handleVoiceChannel(PacketVoiceChannel var1);

    void handleVoiceChannelUpdate(PacketVoiceChannelUpdate var1);

    void handleDeleteVoiceChannel(PacketDeleteVoiceChannel var1);

    void handleUpdateWorld(PacketUpdateWorld var1);

    void handleServerUpdate(PacketServerUpdate var1);

    void handleWorldBorder(PacketWorldBorder var1);

    void handleWorldBorderUpdate(PacketWorldBorderUpdate var1);

    void handleWorldBorderRemove(PacketWorldBorderRemove var1);
}
