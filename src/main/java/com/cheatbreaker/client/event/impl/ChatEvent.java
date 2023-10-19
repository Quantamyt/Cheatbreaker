package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import net.minecraft.network.play.server.S02PacketChat;


public class ChatEvent extends EventBus.Event {
    private final S02PacketChat packetIn;

    public ChatEvent(S02PacketChat packetIn) {
        this.packetIn = packetIn;
    }

    public S02PacketChat getPacketIn() {
        return this.packetIn;
    }
}
