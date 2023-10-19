package com.cheatbreaker.client.event.impl.network;

import com.cheatbreaker.client.event.EventBus;
import net.minecraft.network.play.server.S02PacketChat;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This is fired when the player sends a chat message or receives a chat message.
 */
public class ChatPacketEvent extends EventBus.Event {
    private final S02PacketChat packetIn;

    public ChatPacketEvent(S02PacketChat packetIn) {
        this.packetIn = packetIn;
    }

    public S02PacketChat getPacketIn() {
        return this.packetIn;
    }
}
