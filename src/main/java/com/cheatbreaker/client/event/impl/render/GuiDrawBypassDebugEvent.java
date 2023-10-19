package com.cheatbreaker.client.event.impl.render;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * Used for the scoreboard.
 */
@AllArgsConstructor
@Getter
public class GuiDrawBypassDebugEvent extends EventBus.Event {
    private ScaledResolution scaledResolution;
}
