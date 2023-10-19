package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;

public class ModuleHitboxes extends AbstractModule {

    public final Setting showOwnHitbox;
    public final Setting ownHitboxColor;
    public final Setting color;

    public ModuleHitboxes() {
        super("Hitboxes");
        this.showOwnHitbox = new Setting(this, "Show Own Hitbox").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.ownHitboxColor = new Setting(this, "Own Hitbox Color").setValue(-1)
                .setCondition(this.showOwnHitbox::getBooleanValue).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.color = new Setting(this, "Hitbox Color").setValue(-1).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.setPreviewLabel("Hitboxes", 1.0F);
    }

}