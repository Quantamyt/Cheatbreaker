package com.cheatbreaker.client.event.impl.network;

import com.cheatbreaker.client.event.EventBus;

import java.beans.ConstructorProperties;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This is fired when rendering the world, only in for World Border's though.
 */
public class RenderWorldEvent extends EventBus.Event {
    private final float partialTicks;

    public float getPartialTicks() {
        return this.partialTicks;
    }

    @ConstructorProperties(value = {"partialTicks"})
    public RenderWorldEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
