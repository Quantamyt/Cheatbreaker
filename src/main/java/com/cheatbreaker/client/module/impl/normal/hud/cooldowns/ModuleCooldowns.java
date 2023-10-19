package com.cheatbreaker.client.module.impl.normal.hud.cooldowns;

import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.PreviewDrawEvent;
import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.module.ModulePlaceGui;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * This mod shows custom cooldowns sent by the server utilizing the client Bukkit API.
 * <p>
 * Users can also show the Ender Pearl cooldown if the bukkit API does not exist for their connected server.
 * @see AbstractModule
 */
public class ModuleCooldowns extends AbstractModule {
    private static final List<CooldownRenderer> normal = new ArrayList<>();
    public Setting theme;
    private final Setting mode;
    public Setting useEXP;
    public Setting decimals;
    private final Setting color;
    private final List<CooldownRenderer> preview = new ArrayList<>();

    public ModuleCooldowns() {
        super("Cooldowns", "Normal");
        this.setDefaultAnchor(GuiAnchor.MIDDLE_TOP);
        this.setDefaultTranslations(0.0f, 5);
        this.theme = new Setting(this, "Color Theme").setValue("Bright").acceptedStringValues("Bright", "Dark", "Colored").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.mode = new Setting(this, "List Mode").setValue("horizontal").acceptedStringValues("vertical", "horizontal").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.useEXP = new Setting(this, "Use Pearl Cooldown via XP").setValue(true);
        this.decimals = new Setting(this, "Decimals").setValue(1).setMinMax(0, 3).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.color = new Setting(this, "Colored color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.theme.getValue().equals("Colored"));
        this.setDescription("Allows servers to display items or abilities that are on cooldown.");
        this.setCreators("OpTic");
        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
        this.setDefaultState(true);
    }

    public void onTick(TickEvent tickEvent) {
        if (!normal.isEmpty()) {
            normal.removeIf(CooldownRenderer::isTimeOver);
        }
        if (!this.preview.isEmpty()) {
            this.preview.removeIf(CooldownRenderer::isTimeOver);
        }
    }

    @Subscribe
    public void onPreviewDraw(PreviewDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        if (normal.isEmpty()) {
            GL11.glPushMatrix();
            if (this.preview.isEmpty()) {
                this.preview.add(new CooldownRenderer("CombatTag", 283, 30000L));
                this.preview.add(new CooldownRenderer("EnderPearl", 368, 12000L));
            }
            this.scaleAndTranslate(event.getScaledResolution());
            boolean isVertical = ((String)this.mode.getValue()).equalsIgnoreCase("vertical");
            int n = 36;
            int n2 = 36;
            int n3 = isVertical ? n : this.preview.size() * n;
            int n4 = isVertical ? this.preview.size() * n2 : n2;
            this.setDimensions((int)((float)n3), (int)((float)n4) - 1);
            for (int cooldownList = 0; cooldownList < this.preview.size(); ++cooldownList) {
                CooldownRenderer cooldownRenderer = this.preview.get(cooldownList);
                if (((String)this.mode.getValue()).equalsIgnoreCase("vertical")) {
                    cooldownRenderer.render(this.theme, this.width / 2.0f - (float)(n / 2), cooldownList * n2, this.color.getColorValue());
                    continue;
                }
                cooldownRenderer.render(this.theme, cooldownList * n, 0.0f, this.color.getColorValue());
            }
            GL11.glPopMatrix();
        }
    }

    @Subscribe
    public void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        GL11.glPushMatrix();
        if (normal.size() > 0) {
            this.scaleAndTranslate(event.getScaledResolution());
            boolean isVertical = ((String)this.mode.getValue()).equalsIgnoreCase("vertical");
            int size = 36;
            int size2 = 36;
            int n3 = isVertical ? size : normal.size() * size;
            int n4 = isVertical ? normal.size() * size2 : size2;
            this.setDimensions(n3, n4 - 1);
            for (int cooldownList = 0; cooldownList < normal.size(); ++cooldownList) {
                CooldownRenderer cooldownRenderer = normal.get(cooldownList);
                if (((String)this.mode.getValue()).equalsIgnoreCase("vertical")) {
                    cooldownRenderer.render(this.theme, this.width / 2.0f - (float)(size / 2), cooldownList * size2, this.color.getColorValue());
                    continue;
                }
                cooldownRenderer.render(this.theme, cooldownList * size, 0.0f, this.color.getColorValue());
            }
        } else if (!(this.mc.currentScreen instanceof HudLayoutEditorGui) && !(this.mc.currentScreen instanceof ModulePlaceGui)) {
            this.setDimensions(50, 24);
            this.scaleAndTranslate(event.getScaledResolution());
        }
        GL11.glPopMatrix();
    }

    public static void addCooldown(String cooldownID, long duration, int itemID) {
        for (CooldownRenderer cooldownRenderer : normal) {
            if (!cooldownRenderer.getCooldownID().equalsIgnoreCase(cooldownID) || cooldownRenderer.getItemID() != itemID) continue;
            cooldownRenderer.getTime();
            cooldownRenderer.setDuration(duration);
            return;
        }
        normal.add(new CooldownRenderer(cooldownID, itemID, duration));
    }
}
