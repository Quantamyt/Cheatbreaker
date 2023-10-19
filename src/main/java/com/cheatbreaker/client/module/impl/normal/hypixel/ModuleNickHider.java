package com.cheatbreaker.client.module.impl.normal.hypixel;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.profile.ClientProfile;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.client.Minecraft;

public class ModuleNickHider extends AbstractModule {

    public Setting hideRealName;
    public Setting customNameString;

    public Setting hideSkin;
    public Setting hideOtherSkins;

    public ModuleNickHider() {
        super("Nick Hider");

        new Setting(this, "label").setValue("Skin Options");
        this.hideSkin = new Setting(this, "Hide Own Skin").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.hideOtherSkins = new Setting(this, "Hide Other Skins").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);

        new Setting(this, "label").setValue("Name Options");
        this.hideRealName = new Setting(this, "Hide Real Name").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.customNameString = new Setting(this, "Custom Name String").setValue(Minecraft.getMinecraft().getSession().getUsername()).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.hideRealName.getBooleanValue());

        this.setPreviewLabel("Nick Hider", 1.5F);
        this.setDescription("Hides your identity client-sided.");
        this.setCreators("Sk1er");
        this.setDefaultState(false);
    }
}
