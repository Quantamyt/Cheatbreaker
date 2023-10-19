package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.multiplayer.WorldClient;

@AllArgsConstructor @Getter
public class LoadWorldEvent extends EventBus.Event {
    private WorldClient worldClient;
}
