package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;

import java.beans.ConstructorProperties;

public class RenderWorldEvent extends EventBus.Event {
    private final float partialTicks;

    public float getPartialTicks() {
        return this.partialTicks;
    }

    @ConstructorProperties(value={"partialTicks"})
    public RenderWorldEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
