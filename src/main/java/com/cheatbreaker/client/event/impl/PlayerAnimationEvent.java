package com.cheatbreaker.client.event.impl;

import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.cosmetic.data.AnimationStage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

@AllArgsConstructor @Getter
public class PlayerAnimationEvent extends EventBus.Event {
    private final AnimationStage stage;
    private final AbstractClientPlayer player;
    private final ModelBiped model;
    private final float partialTicks;
}
