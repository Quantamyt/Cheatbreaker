package com.cheatbreaker.client.event.impl.mouse;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This event is for getting the mouse button and state when the mouse is used.
 */
@Getter
@AllArgsConstructor
public class ClickStateEvent extends EventBus.Event {
    private final int mouseButton;
    private final boolean state;
}
