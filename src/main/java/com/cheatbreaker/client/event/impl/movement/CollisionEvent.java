package com.cheatbreaker.client.event.impl.movement;


import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * @see com.cheatbreaker.client.event.EventBus.Event
 * This is used for player collision, most notably used in the NoClip staff module.
 */
@Getter
@AllArgsConstructor
public class CollisionEvent extends EventBus.EventData {
    private List<AxisAlignedBB> boxes;
    private double x;
    private double y;
    private double z;
}
