package com.cheatbreaker.client.event.impl.network;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This is fired when the server sends a custom payload to the client.
 *
 * The payload can mainly be defined as a custom client packet for CB-Client.
 */
@Getter
@AllArgsConstructor
public class PluginMessageEvent extends EventBus.Event {
    private String channelName;
    private byte[] bufferData;
}
