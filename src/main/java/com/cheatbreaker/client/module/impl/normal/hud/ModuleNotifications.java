package com.cheatbreaker.client.module.impl.normal.hud;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.tick.KeepAliveEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Module - ModuleNotifications
 * @see AbstractModule
 *
 * This module displays custom Notifications for the CheatBreaker Client.
 */
public class ModuleNotifications extends AbstractModule {
    public long time = System.currentTimeMillis();
    private final List<Notification> notifications = new ArrayList<>();

    public ModuleNotifications() {
        super("Notifications");
        this.addEvent(KeepAliveEvent.class, this::onKeepAlive);
        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
        this.setDefaultState(true);
    }

    private void onKeepAlive(KeepAliveEvent event) {
        this.time = System.currentTimeMillis();
    }

    private void onTick(TickEvent event) {
        Iterator<Notification> iterator = this.notifications.iterator();
        while (iterator.hasNext()) {
            Notification notification = iterator.next();
            notification.onTick();
            long l = notification.ticks + notification.duration - System.currentTimeMillis();
            if (l > 0L) continue;
            int n = notification.scaledWidth;
            for (Notification notification2 : this.notifications) {
                if (notification2.scaledWidth >= notification.scaledWidth) continue;
                notification2.activeTicks = 0;
                notification2.scaledHeight = n;
                n = notification2.scaledWidth;
            }
            iterator.remove();
        }
    }

    private void onGuiDraw(GuiDrawEvent event) {
        for (Notification notification : this.notifications) {
            notification.render(event.getScaledResolution().getScaledWidth());
        }
    }

    public void send(String notificationType, String message, long duration) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        if (duration < 2000L) {
            duration = 2000L;
        }

        message = message.replaceAll("&([abcdefghijklmrABCDEFGHIJKLMNR0-9])|(&$)", "§$1");
        Type type;
        switch (notificationType.toLowerCase()) {
            case "info":
                type = Type.INFO;
                break;
            case "error":
                type = Type.ERROR;
                break;
            default:
                type = Type.NEUTRAL;
        }

        Notification var11 = new Notification(this, scaledResolution, type, message, duration);
        int var12 = var11.scaledHeight - var11.height - 2;

        for (int var9 = this.notifications.size() - 1; var9 >= 0; --var9) {
            Notification var10 = this.notifications.get(var9);
            var10.activeTicks = 0;
            var10.scaledHeight = var12;
            var12 -= 2 + var10.height;
        }

        this.notifications.add(var11);
    }

    class Notification {
        public Type type;
        public String message;
        public long duration;
        public long ticks = System.currentTimeMillis();
        public int scaledWidth;
        public int scaledHeight;
        public int height;
        public int activeTicks = 0;
        final ModuleNotifications notificationsMod;

        Notification(ModuleNotifications notificationsMod, ScaledResolution scaledResolution, Type type, String message, long duration) {
            this.notificationsMod = notificationsMod;
            this.type = type;
            this.message = message;
            this.duration = duration;
            this.height = type == Type.NEUTRAL ? 16 : 20;
            this.scaledHeight = scaledResolution.getScaledHeight() - 14 - this.height;
            this.scaledWidth = scaledResolution.getScaledHeight() + this.height;
        }

        public void onTick() {
            if (this.scaledHeight != -1) {
                ++this.activeTicks;
                float f = (float) this.activeTicks * ((float) this.activeTicks / (float) 5) / (float) 7;
                if (this.scaledWidth > this.scaledHeight) {
                    if ((float) this.scaledWidth - f < (float) this.scaledHeight) {
                        this.scaledWidth = this.scaledHeight;
                        this.scaledHeight = -1;
                    } else {
                        this.scaledWidth = (int) ((float) this.scaledWidth - f);
                    }
                } else if (this.scaledWidth < this.scaledHeight) {
                    if ((float) this.scaledWidth + f > (float) this.scaledHeight) {
                        this.scaledWidth = this.scaledHeight;
                        this.scaledHeight = -1;
                    } else {
                        this.scaledWidth = (int) ((float) this.scaledWidth + f);
                    }
                } else if (this.scaledWidth == this.scaledHeight) {
                    this.scaledHeight = -1;
                }
            }
        }

        public void render(int n) {
            CBFontRenderer cBFontRenderer = CheatBreaker.getInstance().playRegular16px;
            int n2 = this.scaledWidth;
            float f = cBFontRenderer.getStringWidth(this.message);
            int n3 = (int) (this.type == Type.NEUTRAL ? f + (float) 10 : f + (float) 30);
            Gui.drawRect(n - 5 - n3, n2, n - 5, n2 + this.height, -1358954496);
            switch (this.type) {

                case ERROR:
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderUtil.renderIcon(new ResourceLocation("client/icons/error-64.png"), (float) 6, (float) (n - 10 - n3 + 9), (float) (n2 + 4));
                    Gui.drawRect((float) (n - 10) - f - 4.5f, n2 + 4, (float) (n - 10) - f - 4.0F, n2 + this.height - 4, -1342177281);
                    break;

                case INFO:
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.65F);
                    RenderUtil.renderIcon(new ResourceLocation("client/icons/info-64.png"), (float) 6, (float) (n - 10 - n3 + 9), (float) (n2 + 4));
                    Gui.drawRect((float) (n - 10) - f - 4.5f, n2 + 4, (float) (n - 10) - f - 4.0F, n2 + this.height - 4, -1342177281);
            }

            long l = this.duration - (this.ticks + this.duration - System.currentTimeMillis());
            if (l > this.duration) {
                l = this.duration;
            }
            if (l < 0L) {
                l = 0L;
            }
            float f2 = f * ((float) l / (float) this.duration * (float) 100 / (float) 100);
            Gui.drawRect((float) (n - 10) - f, (float) (n2 + this.height) - 56.46667f * 0.077922076f, (float) (n - 10) - f + f, n2 + this.height - 4, 0x30666666);
            Gui.drawRect((float) (n - 10) - f, (float) (n2 + this.height) - 2.2f * 2.0f, (float) (n - 10) - f + f2, n2 + this.height - 4, -1878982912);
            cBFontRenderer.drawString(this.message, (float) (n - 10) - f, (float) (n2 + (this.type == Type.NEUTRAL ? 2 : 4)), -1);
        }
    }

    enum Type {
        INFO,
        ERROR,
        NEUTRAL
    }
}
