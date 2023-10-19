package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@AllArgsConstructor @Getter
public class PreviewDrawEvent extends EventBus.Event {
    private ScaledResolution scaledResolution;
}
