package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;

/**
 * @Module - ModuleNametag
 * @see AbstractModule
 *
 * This module changes the Vanilla Minecraft NameTag for players.
 */
public class ModuleNameTag extends AbstractModule {

    private final String[] disallowedCharacters = new String[]{ " ", "[", "]", "!", "=", "-", "@", "#", "$", "%", "^", "&", "*", "(", ")", "~", "`", "|", ";", ":", "'", "\"", ",", "<", ">", ".", "?", "/" };

    public Setting hideNamePlatesKeybind;
    public Setting generalOptionsLabel;
    public Setting background;
    public Setting textShadow;
    public Setting showCheatBreakerLogo;
    public Setting showOwnNametag;
    public Setting showNametagsInF1;

    public Setting colorOptionsLabel;
    public Setting customTextColor;
    public Setting textColor;
    public Setting backgroundColor;

    public ModuleNameTag() {
        super("Nametag");
        this.setDefaultState(true);
        this.hideNamePlatesKeybind = new Setting(this, "Hide Nametags Keybind").setValue(0).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.generalOptionsLabel = new Setting(this, "label").setValue("General Options");
        this.background = new Setting(this, "Show Background").setValue(true);
        this.textShadow = new Setting(this, "Text Shadow").setValue(false);
        this.showCheatBreakerLogo = new Setting(this, "Show CheatBreaker Logo").setValue(true);
        this.showOwnNametag = new Setting(this, "Shown own Nametag").setValue(false);
        this.showNametagsInF1 = new Setting(this, "Show Nametags when GUI is hidden").setValue(false);

        this.colorOptionsLabel = new Setting(this, "label").setValue("Color Options");
        this.customTextColor = new Setting(this, "Custom Text Color").setValue(false);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.customTextColor.getValue());
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x40000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.background.getValue());
        this.setDescription("Displays the username above players.");
    }

    public boolean meetsIconRequirements(String username)
    {
        for (String str : this.disallowedCharacters) {
            if (username.contains(str)) return false;
        }
        return true;
    }
}
