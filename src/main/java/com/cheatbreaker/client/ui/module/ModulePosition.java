package com.cheatbreaker.client.ui.module;

import com.cheatbreaker.client.module.AbstractModule;

public class ModulePosition {
    protected AbstractModule module;
    protected float x;
    protected float y;

    ModulePosition(AbstractModule abstractModule, float f, float f2) {
        this.module = abstractModule;
        this.x = f;
        this.y = f2;
    }
}
