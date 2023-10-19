package com.cheatbreaker.client.module.impl.staff.impl;

import com.cheatbreaker.client.event.impl.CollisionEvent;
import com.cheatbreaker.client.module.impl.staff.StaffMod;

public class StaffModuleNoclip extends StaffMod {
    public StaffModuleNoclip(String name) {
        super(name);
        this.setStaffModule(true);
        this.addEvent(CollisionEvent.class, this::onCollisionEvent);
    }

    private void onCollisionEvent(CollisionEvent collisionEvent) {
        collisionEvent.setCanceled(true);
    }
}
