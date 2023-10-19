package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class PluginMessageEvent extends EventBus.Event {
    private String channelName;
    private byte[] bufferData;
}
