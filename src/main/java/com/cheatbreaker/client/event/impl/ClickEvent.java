package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.event.impl.eventhandler.Cancelable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter @Cancelable
public class ClickEvent extends EventBus.EventData {
    private final int mouseButton;
}
