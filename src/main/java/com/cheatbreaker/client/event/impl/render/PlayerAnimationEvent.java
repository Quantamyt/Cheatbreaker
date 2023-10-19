package com.cheatbreaker.client.event.impl.render;

import com.cheatbreaker.client.cosmetic.data.AnimationStage;
import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * Used for emotes and emote rendering.
 */
@AllArgsConstructor
@Getter
public class PlayerAnimationEvent extends EventBus.Event {
    private final AnimationStage stage;
    private final AbstractClientPlayer player;
    private final ModelPlayer model;
    private final float partialTicks;
}
