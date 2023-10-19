package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

import java.awt.*;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @Module - ModulePing
 * @see AbstractSimpleHudModule
 *
 * This module shows the current ping to the current server you're on.
 */
public class SimpleModulePing extends AbstractSimpleHudModule {
    private final OldServerPinger checkPing = new OldServerPinger();

    private Setting refresher;
    private Setting badPingRange;
    private Setting hideInSingleplayer;
    private Setting hideIfNoConnection;
    public Setting dynamicColorRange;

    private long getPing;
    private long pingNumber = -1L;

    public SimpleModulePing() {
        super("Ping", "[19 ms]");
        setDescription("Displays your server latency.");
    }

    @Override
    public String getValueString() {
        ServerData pingVar = mc.getCurrentServerData();
        this.getPing(pingVar);
        if (pingVar != null && pingVar.pingToServer > 0L) {
            this.pingNumber = pingVar.pingToServer;
        }
        if (!this.mc.isIntegratedServerRunning() && this.mc.theWorld != null || !(Boolean) this.hideInSingleplayer.getValue()) {
            if (this.pingNumber != -1L || !(Boolean) this.hideIfNoConnection.getValue()) {
                return this.pingNumber + " ms";
            }
        }
        return null;
    }

    public String getPreviewString() {
        return "119 ms";
    }

    @Override
    public String getLabelString() {
        return "Ping";
    }

    public String customString() {
        return "%VALUE%";
    }

    public boolean includeIcon() {
        return false;
    }

    public int getColor() {
        if ((Boolean) this.dynamicColorRange.getValue()) {
            if (getValueString() != null) {
                return Color.getHSBColor(Math.max((125.0F - pingNumber * 10.0F / (float) this.badPingRange.getValue()) / 360.0F, 0.0F), 1.0F, 1.0F).getRGB();
            } else {
                return Color.getHSBColor(Math.max((125.0F - 119 * 10.0F / (float) this.badPingRange.getValue()) / 360.0F, 0.0F), 1.0F, 1.0F).getRGB();
            }

        }
        return this.textColor.getColorValue();
    }

    public void getExtraSettings() {
        this.refresher = new Setting(this, "Refresh Time", "Changes how many seconds the mod should refresh itself.").setUnit("s").setValue(15).setMinMax(10, 90).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.hideInSingleplayer = new Setting(this, "Hide in Singleplayer", "Hid the mod in Singleplayer.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.hideIfNoConnection = new Setting(this, "Hide if no connection", "Hide the mod when no connection is found.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.dynamicColorRange = new Setting(this, "Dynamic Color Range", "Determine if the text color should change dynamically based on ping amount.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.badPingRange = new Setting(this, "Bad Ping Range Threshold").setValue(25.0F).setMinMax(10.0F, 100.0F).setCondition(() -> (Boolean) this.dynamicColorRange.getValue());
    }

    private void getPing(ServerData var1) {
        if (!this.mc.isIntegratedServerRunning() && this.mc.theWorld != null) {
            if (var1 != null && System.currentTimeMillis() - this.getPing >= TimeUnit.SECONDS.toMillis((long) (Integer) this.refresher.getValue())) {
                (new Thread(() -> {
                    try {
                        this.checkPing.ping(var1);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                })).start();
                this.getPing = System.currentTimeMillis();
            }
        } else {
            this.pingNumber = -1;
        }
    }
}

