package com.cheatbreaker.client.event.impl;


import com.cheatbreaker.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

@AllArgsConstructor @Getter
public class CollisionEvent extends EventBus.EventData {
    private List<AxisAlignedBB> boxes;
    private double x;
    private double y;
    private double z;
}
