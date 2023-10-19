package com.cheatbreaker.client.event.impl.network;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.multiplayer.WorldClient;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This is fired when loading the world in, and when you change dimensions.
 */
@Getter
@AllArgsConstructor
public class LoadWorldEvent extends EventBus.Event {
    private WorldClient worldClient;
}
