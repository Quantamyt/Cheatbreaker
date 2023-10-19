package com.cheatbreaker.client.module.impl.staff;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;

/**
 * @see AbstractModule
 *
 * This Object defines the staff module variant of the com.cheatbreaker.client.module.AbstractModule Object.
 */
public class StaffMod extends AbstractModule {
    private final Setting keybind = new Setting(this, "Keybind").setValue(0);

    public Setting getKeybindSetting() {
        return this.keybind;
    }

    public StaffMod(String name) {
        super(name);
    }

    public void disableStaffModule() {
        if (this.isEnabled()) {
            this.setState(false);
        }
        this.setStaffModuleEnabled(false);
    }
}
