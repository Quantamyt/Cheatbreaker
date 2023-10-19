package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.PacketClientVoice;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceChannelSwitch;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceMute;

public interface ICBNetHandlerServer extends ICBNetHandler {
    void handleClientVoice(PacketClientVoice var1);

    void handleVoiceChannelSwitch(PacketVoiceChannelSwitch var1);

    void handleVoiceMute(PacketVoiceMute var1);
}
