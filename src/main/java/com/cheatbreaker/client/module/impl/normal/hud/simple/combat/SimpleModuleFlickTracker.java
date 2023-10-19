package com.cheatbreaker.client.module.impl.normal.hud.simple.combat;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Module - SimpleFlickTrackerModule
 * @see AbstractSimpleHudModule
 *
 * This module shows the amount of distance flicked from the last target hit.
 * @using general Pythagorean Therum methods of sine, cosine, and tangent
 */
public class SimpleModuleFlickTracker extends AbstractSimpleHudModule {
    private String flickText = "Distance Flicked: %COUNTER%";

    @Setter
    private Long lastHit = 0L;

    @Setter
    private double lastHitYaw;

    @Setter
    private double lastHitPitch;

    private Setting fadeAmount;
    private Setting requiredFlickDistance;


    @Getter @Setter
    private double distance = 0;

    @Getter @Setter
    private int counter = 0;


    public SimpleModuleFlickTracker() {
        super("Flick Tracker", "[Distance Flicked: 4.21]", 1.0F, false, false);
        this.setDefaultState(false);
        this.notRenderHUD = false;
        this.setDescription("Shows the amount you flick for each hit");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(() ->
        {
            if (System.currentTimeMillis() - lastHit >= TimeUnit.SECONDS.toMillis(fadeAmount.getIntegerValue()))
            {
                distance = 0.0;
                counter = 0;
            }
        }, 0L, 1L, TimeUnit.SECONDS);

    }

    @Override
    public String getValueString() {

        return counter == 0 ?
                flickText.replaceAll("%COUNTER%", "0") :
                flickText.replaceAll("%COUNTER%", String.valueOf(distance));
    }

    public String getPreviewString() {
        return this.flickText;
    }

    @Override
    public String getLabelString() {
        return "Flick Amount";
    }

    public String customString() {
        return "%VALUE%";
    }

    public void getExtraSettings() {
        new Setting(this, "label").setValue("Flick Tracker Options");
        fadeAmount = new Setting(this, "Clear Out Time (Seconds)", "Change the amount of time required for value to be cleared").setValue(4).setMinMax(1, 10).setUnit("s").setCustomizationLevel(CustomizationLevel.SIMPLE);
        requiredFlickDistance = new Setting(this, "Required Flick Distance", "How large of a flick should be counted").setValue(4).setMinMax(1, 10).setUnit(" distance").setCustomizationLevel(CustomizationLevel.SIMPLE);
    }

    public void getExtraFormatSettings() {

    }

    public Double calculate() {
        Double yawDifference = Math.abs(lastHitYaw + Minecraft.getMinecraft().thePlayer.cameraYaw);
        Double pitchDifference = Math.abs(lastHitPitch - Minecraft.getMinecraft().thePlayer.cameraPitch);

        double hypot = Math.sqrt((pitchDifference * pitchDifference) + (yawDifference * yawDifference));

        System.out.println(hypot);

        if (hypot > requiredFlickDistance.getIntegerValue()) {
            counter++;
        }

        return hypot;
    }
}
