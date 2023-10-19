package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.beans.ConstructorProperties;

@AllArgsConstructor @Getter
public class ClickStateEvent extends EventBus.Event {
    private final int mouseButton;
    private final boolean state;
}
