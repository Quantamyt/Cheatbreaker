package com.cheatbreaker.client.module.impl.staff.impl;

import com.cheatbreaker.client.module.impl.staff.StaffMod;
import net.minecraft.client.Minecraft;
import com.cheatbreaker.client.module.data.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StaffModuleXray extends StaffMod {
    private final List<Integer> blocks;
    public Setting opacity;

    public StaffModuleXray(String string) {
        super(string);
        this.setStaffModule(true);
        this.blocks = new ArrayList<>();
        Collections.addAll(this.blocks, 14, 15, 16, 15, 56, 129, 52);
        this.opacity = new Setting(this, "Opacity").setValue(45).setMinMax(15, 255);
    }

    @Override
    public void addAllEvents() {
        super.addAllEvents();
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
    }

    @Override
    public void removeAllEvents() {
        super.removeAllEvents();
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
    }

    public boolean shouldRender(int blockID, boolean vanillaRender) {
        return !this.isEnabled() ? vanillaRender : this.blocks.contains(blockID);
    }

    public List<Integer> getBlocks() {
        return this.blocks;
    }
}
