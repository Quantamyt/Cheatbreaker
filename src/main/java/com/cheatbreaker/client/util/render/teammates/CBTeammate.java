package com.cheatbreaker.client.util.render.teammates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Vec3;

import java.awt.Color;
import java.beans.ConstructorProperties;

@AllArgsConstructor @Getter @Setter
public class CBTeammate {
    private String uuid;
    private boolean leader = false;
    private Vec3 position;
    private long lastUpdate;
    private Color color;
    private long lastMs;

    @ConstructorProperties({"uuid", "leader"})
    public CBTeammate(String uuid, boolean leader) {
        this.uuid = uuid;
        this.leader = leader;
        this.lastUpdate = System.currentTimeMillis();
    }

    public void updateTeammate(double xPos, double yPos, double zPos, long lastMs) {
        this.position = new Vec3(xPos, yPos, zPos);
        this.lastUpdate = System.currentTimeMillis();
        this.lastMs = lastMs;
    }
}
