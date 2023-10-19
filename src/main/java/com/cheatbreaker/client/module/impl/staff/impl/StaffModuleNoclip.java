package com.cheatbreaker.client.module.impl.staff.impl;

import com.cheatbreaker.client.event.impl.movement.CollisionEvent;
import com.cheatbreaker.client.module.impl.staff.StaffMod;

/**
 * @Module - StaffModuleNoClip
 * @see StaffMod
 *
 * This staff module allows you to phase through the entire Minecraft World.
 */
public class StaffModuleNoClip extends StaffMod {

    public StaffModuleNoClip() {
        super("noclip");
        this.setStaffModule(true);
        this.addEvent(CollisionEvent.class, this::onCollisionEvent);
    }

    private void onCollisionEvent(CollisionEvent collisionEvent) {
        collisionEvent.setCanceled(true);
    }
}
