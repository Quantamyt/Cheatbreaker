package com.cheatbreaker.client.util.render.worldborder;

import com.cheatbreaker.client.CheatBreaker;
import com.sun.javafx.geom.Vec2d;
import net.minecraft.entity.Entity;

import java.awt.Color;

public class WorldBorder {
    private final String id;
    private final String world;
    private final Color color;
    private Vec2d currentMin;
    private Vec2d currentMax;
    private Vec2d lastMin;
    private Vec2d lastMax;
    private Vec2d newMin;
    private Vec2d newMax;
    private final boolean canShrinkExpand;
    private final boolean cancelExit;
    private int moveTicks;
    private int durationTicks;
    final WorldBorderManager manager;

    WorldBorder(WorldBorderManager manager, String id, String world, int color, double minX, double minZ,
                double maxX, double maxZ, boolean canShrinkExpand, boolean cancelExit) {
        this.manager = manager;
        this.id = id;
        this.world = world;
        this.color = new Color(color, true);
        this.canShrinkExpand = canShrinkExpand;
        this.cancelExit = cancelExit;
        this.lastMin = this.currentMin = new Vec2d(minX, minZ);
        this.lastMax = this.currentMax = new Vec2d(maxX, maxZ);
    }

    boolean shouldMove() {
        return this.canShrinkExpand && this.durationTicks != 0 && this.moveTicks < this.durationTicks;
    }


    void tick() {
        if (this.shouldMove()) {
            double d = this.currentMin.x - this.newMin.x;
            double d2 = this.currentMax.x - this.newMax.x;
            double d3 = this.currentMin.y - this.newMin.y;
            double d4 = this.currentMax.y - this.newMax.y;
            double d5 = (float)this.moveTicks / (float)this.durationTicks;
            double d6 = this.currentMin.x - d * d5;
            double d7 = this.currentMax.x - d2 * d5;
            double d8 = this.currentMin.y - d3 * d5;
            double d9 = this.currentMax.y - d4 * d5;
            this.lastMin = new Vec2d(d6, d8);
            this.lastMax = new Vec2d(d7, d9);
            ++this.moveTicks;
        } else if (this.currentMax != this.lastMax || this.currentMin != this.lastMin) {
            this.currentMin = this.lastMin;
            this.currentMax = this.lastMax;
            this.lastMax = this.newMax;
            this.lastMin = this.newMin;
            this.durationTicks = 0;
            this.moveTicks = 0;
        }
    }

    boolean worldEqualsWorld() {
        return CheatBreaker.getInstance().getCbNetHandler().getWorldUID().equals(this.world);
    }

    double getStartingMaxX() {
        return this.lastMax.x;
    }

    double getStartingMaxZ() {
        return this.lastMax.y;
    }

    double getStartingMinX() {
        return this.lastMin.x;
    }

    double getStartingMinZ() {
        return this.lastMin.y;
    }

    double getClosestDistance(Entity entity) {
        return this.getClosestDistance(entity.posX, entity.posZ);
    }

    double getClosestDistance(double x, double z) {
        double d3 = z - this.getStartingMinZ();
        double d4 = this.getStartingMaxZ() - z;
        double d5 = x - this.getStartingMinX();
        double d6 = this.getStartingMaxX() - x;
        double d7 = Math.min(d5, d6);
        d7 = Math.min(d7, d3);
        return Math.min(d7, d4);
    }
    boolean doesCollide(double d, double d2) {
        return !this.cancelExit ||
                !this.world.equals(WorldBorderManager.getCheatBreaker(this.manager).getCbNetHandler().getWorldUID()) ||
                d + 1.0 > this.getStartingMinX() && d < this.getStartingMaxX() && d2 + 1.0 > this.getStartingMinZ() && d2 < this.getStartingMaxZ();
    }


    public String getId() {
        return this.id;
    }

    public String getWorld() {
        return this.world;
    }

    public Color getColor() {
        return this.color;
    }

    public Vec2d getCurrentMin() {
        return this.currentMin;
    }

    public Vec2d getCurrentMax() {
        return this.currentMax;
    }

    public Vec2d getStartingMin() {
        return this.lastMin;
    }

    public Vec2d getStartingMax() {
        return this.lastMax;
    }

    public Vec2d getNewMin() {
        return this.newMin;
    }

    public Vec2d getNewMax() {
        return this.newMax;
    }

    public boolean canShrinkExpand() {
        return this.canShrinkExpand;
    }

    public boolean doesCancelExit() {
        return this.cancelExit;
    }

    public int getMoveTicks() {
        return this.moveTicks;
    }

    public int getDurationTicks() {
        return this.durationTicks;
    }

    static String getId(WorldBorder worldBorder) {
        return worldBorder.id;
    }

    static Vec2d setNewMin(WorldBorder worldBorder, Vec2d vec2d) {
        worldBorder.newMin = vec2d;
        return worldBorder.newMin;
    }

    static Vec2d setNewMax(WorldBorder worldBorder, Vec2d vec2d) {
        worldBorder.newMax = vec2d;
        return worldBorder.newMax;
    }

    static int setMoveTicks(WorldBorder worldBorder, int n) {
        worldBorder.moveTicks = n;
        return worldBorder.moveTicks;
    }

    static int setDurationTicks(WorldBorder worldBorder, int durationTicks) {
        worldBorder.durationTicks = durationTicks;
        return worldBorder.durationTicks;
    }

    static boolean canShrinkExpand(WorldBorder worldBorder) {
        return worldBorder.canShrinkExpand;
    }

    static Color getColor(WorldBorder worldBorder) {
        return worldBorder.color;
    }
}
