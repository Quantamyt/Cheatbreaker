package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class KeyboardEvent extends EventBus.Event {
    private int pressed;
}
