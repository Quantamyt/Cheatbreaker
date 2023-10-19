package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import lombok.Setter;
import net.minecraft.MinecraftMovementInputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.audio.ITickableSound;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Module - ModuleHearingAssistance
 * @see AbstractSimpleHudModule
 *
 * This module shows sounds for hearing-impaired users or users to understnad what sounds are playing
 * This module had too many features to be implemented as a setting
 */
public class SimpleModuleHearingAssistance extends AbstractSimpleHudModule {
    private String soundText = "Current Sound: %SOUND%";

    @Setter
    private Long lastUpdated = 0L;

    private Setting fadeAmount;

    @Setter
    private String currentSound = null;


    public SimpleModuleHearingAssistance() {
        super("Hearing Assistance", "[Current Sound: Footsteps]", 1.0F, false, false);
        this.setDefaultState(false);
        this.notRenderHUD = false;
        this.setDescription("Shows the current sound playing on your Minecraft client");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(() ->
        {
            if (System.currentTimeMillis() - lastUpdated >= TimeUnit.SECONDS.toMillis(fadeAmount.getIntegerValue()))
            {
                currentSound = null;
            }
        }, 0L, 1L, TimeUnit.SECONDS);

    }

    @Override
    public String getValueString() {

        return currentSound == null ?
                soundText.replaceAll("%SOUND%", "None") :
                soundText.replaceAll("%SOUND%", currentSound);
    }

    public String getPreviewString() {
        return this.soundText;
    }

    @Override
    public String getLabelString() {
        return "Current Sound";
    }

    public String customString() {
        return "%VALUE%";
    }

    public void getExtraSettings() {
        new Setting(this, "label").setValue("Hearing Assistance Options");
        fadeAmount = new Setting(this, "Clear Out Time (Seconds)", "Change the amount of time required for sound to be cleared").setValue(4).setMinMax(1, 10).setUnit("s").setCustomizationLevel(CustomizationLevel.SIMPLE);
    }

    public void getExtraFormatSettings() {

    }
}
