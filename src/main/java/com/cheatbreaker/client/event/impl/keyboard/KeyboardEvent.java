package com.cheatbreaker.client.event.impl.keyboard;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This event is for when the keyboard is used.
 */
@Getter
@AllArgsConstructor
public class KeyboardEvent extends EventBus.Event {
    private int pressed;
}

