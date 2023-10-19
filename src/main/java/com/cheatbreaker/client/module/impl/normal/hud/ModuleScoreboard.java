package com.cheatbreaker.client.module.impl.normal.hud;

import com.cheatbreaker.client.event.impl.GuiDrawBypassDebugEvent;
import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.KeyboardEvent;
import com.cheatbreaker.client.event.impl.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.MiniMapRules;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.scoreboard.*;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Allows the user to move and customize the Minecraft scoreboard.
 */
public class ModuleScoreboard extends AbstractModule {

    public Setting background;
    public Setting backgroundWidthPadding;
    public Setting border;
    public Setting borderThickness;

    public Setting removeNumbers;
    public Setting shadow;

    public Setting customTextColor;
    public Setting textColor;
    public Setting numbersColor;
    public Setting topBackgroundColor;
    public Setting backgroundColor;
    public Setting borderColor;

    public static MiniMapRules minimapRule = MiniMapRules.NEUTRAL;
    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-689A-E]");

    public ModuleScoreboard() {
        super("Scoreboard");
        this.setDefaultAnchor(GuiAnchor.RIGHT_MIDDLE);
        new Setting(this, "label").setValue("Background Options");
        this.background = new Setting(this, "Show Background").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.backgroundWidthPadding = new Setting(this, "Background Width Padding").setValue(4.0F).setMinMax(0.0F, 10.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.border = new Setting(this, "Show Border").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.borderThickness = new Setting(this, "Border Thickness").setValue(1.0F).setMinMax(0.25F, 3.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.border.getValue() && (Boolean) this.background.getValue());

        new Setting(this, "label").setValue("General Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.removeNumbers = new Setting(this, "Remove Scoreboard numbers").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.shadow = new Setting(this, "Text Shadow").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);

        new Setting(this, "label").setValue("Color Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.customTextColor = new Setting(this, "Strip Formatting Colors").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.customTextColor.getValue());
        this.numbersColor = new Setting(this, "Scoreboard Numbers Color").setValue(0xFFFF5555).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !(Boolean) this.removeNumbers.getValue());
        this.topBackgroundColor = new Setting(this, "Top Background Color").setValue(0x60000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.background.getValue());
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x50000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.background.getValue());
        this.borderColor = new Setting(this, "Border Color").setValue(0x80000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.border.getValue());

        this.setDescription("Move and customize the Minecraft scoreboard to your liking.");
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawBypassDebugEvent.class, this::onGuiDraw);
        this.setDefaultState(true);
    }

    private void onPreviewDraw(PreviewDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        if (this.mc.theWorld.getScoreboard().func_96539_a(1) != null) {
            return;
        }
        GL11.glPushMatrix();
        this.scaleAndTranslate(event.getScaledResolution());
        GL11.glTranslatef(this.removeNumbers() ? (float)-12 : 2.0f, this.height, 0.0f);
        Scoreboard scoreboard = new Scoreboard();
        ScoreObjective scoreObjective = new ScoreObjective(scoreboard, "CheatBreaker", IScoreObjectiveCriteria.field_96641_b);
        scoreObjective.setDisplayName(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Cheat" + EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "Breaker");
        scoreboard.func_96529_a("Steve", scoreObjective);
        scoreboard.func_96529_a("Alex", scoreObjective);
        this.renderObjective(scoreObjective, event.getScaledResolution().getScaledHeight(), event.getScaledResolution().getScaledWidth(), this.mc.fontRenderer, (Float) this.masterScale());
        GL11.glPopMatrix();
    }

    private void onGuiDraw(GuiDrawBypassDebugEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        GL11.glPushMatrix();
        this.scaleAndTranslate(event.getScaledResolution());
        GL11.glTranslatef(this.removeNumbers() ? (float)-12 : 2.0f, this.height, 0.0f);
        ScoreObjective scoreObjective = this.mc.theWorld.getScoreboard().func_96539_a(1);
        if (scoreObjective != null) {
            this.renderObjective(scoreObjective, event.getScaledResolution().getScaledHeight(),
                    event.getScaledResolution().getScaledWidth(), this.mc.fontRenderer, (Float) this.masterScale());
        }
        GL11.glPopMatrix();
    }

    private void renderObjective(ScoreObjective scoreObjective, int n, int n2, FontRenderer fontRenderer, float f) {
        GL11.glEnable(GL11.GL_BLEND);
        net.minecraft.scoreboard.Scoreboard scoreboard = scoreObjective.getScoreboard();
        Collection<Score> collection = scoreboard.func_96534_i(scoreObjective);
        boolean removeNumbers = this.removeNumbers();
        boolean isCustomTextColorEnabled = (Boolean)customTextColor.getValue();
        if (collection.size() <= 15) {
            int n3 = fontRenderer.getStringWidth(scoreObjective.getDisplayName());
            int n4 = n3 + 16;
            for (Score score : collection) {
                ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(score.getPlayerName());
                String string = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
                n3 = Math.max(n3, fontRenderer.getStringWidth(string));
            }
            int n5 = 0;
            int n6 = 3;
            int n7 = 0;
            int n8 = 0;
            float n9 = 0;
            float width = this.removeNumbers() ?  - 4 + ((float) backgroundWidthPadding.getValue()) : 0.0f;

            for (Score score : collection) {
                ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(score.getPlayerName());
                String string = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score.getPlayerName());
                String string2 = "" + score.getScorePoints();
                int n10 = n5 - ++n8 * fontRenderer.FONT_HEIGHT;
                int n11 = n3 + n6 + 6;
                if (n11 < n4) {
                    n11 = n4;
                }
                if ((Boolean) background.getValue()) {
                    Gui.drawRect(n7 - 2 + (removeNumbers ? 14 : 0), n10, n11 + width, n10 + fontRenderer.FONT_HEIGHT, backgroundColor.getColorValue());
                }
                n9 = n11 - (n7 - 2 + (removeNumbers ? 18 - ((float) backgroundWidthPadding.getValue()) : 0));
                GL11.glEnable(GL11.GL_BLEND);
                fontRenderer.drawString(isCustomTextColorEnabled ? stripColor(string) : string, n7 + (removeNumbers ? 14 + (float) backgroundWidthPadding.getValue() / 2 : 0), n10, isCustomTextColorEnabled ? textColor.getColorValue() : -1, (Boolean) shadow.getValue());
                if (!removeNumbers) {
                    fontRenderer.drawString(string2, n11 - fontRenderer.getStringWidth(string2) - 2, n10, numbersColor.getColorValue(), (Boolean) shadow.getValue());
                }
                if (n8 != collection.size()) continue;
                String string3 = scoreObjective.getDisplayName();
                if ((Boolean) background.getValue()) {
                    Gui.drawRect(n7 - 2 + (removeNumbers ? 14 : 0), n10 - fontRenderer.FONT_HEIGHT - 1, n11 + width, n10 - 1, topBackgroundColor.getColorValue());
                    Gui.drawRect(n7 - 2 + (removeNumbers ? 14 : 0), n10 - 1, n11 + width, n10, backgroundColor.getColorValue());
                }
                fontRenderer.drawString(isCustomTextColorEnabled ? stripColor(string3) : string3, n7 + n3 / 2 - fontRenderer.getStringWidth(string3) / 2 + (removeNumbers ? 12 + (float) backgroundWidthPadding.getValue() / 2 : 0), n10 - fontRenderer.FONT_HEIGHT, isCustomTextColorEnabled ? textColor.getColorValue() : -1, (Boolean) shadow.getValue());
                GL11.glDisable(GL11.GL_BLEND);
            }
            int var23 = n3 + n6 + 6;
            if (var23 < n4) {
                var23 = n4;
            }
            if ((Boolean) border.getValue()) {
                float bT = (Float)borderThickness.getValue();
                Gui.drawOutline((float)(n7 - 2 + (removeNumbers ? 14 : 0)) - bT, -(collection.size() * fontRenderer.FONT_HEIGHT + 10) - bT, var23 + bT + width, 0.0F + bT, bT, borderColor.getColorValue());
            }
            this.setDimensions(n9, collection.size() * fontRenderer.FONT_HEIGHT + 10);
        }
    }

    /**
     * @return True if Minimap Rule is set to Neutral and if the remove numbers option is set to true or if Minimap rule is set to Forced Off.
     */
    private boolean removeNumbers() {
        return minimapRule == MiniMapRules.NEUTRAL ? (Boolean)this.removeNumbers.getValue() : minimapRule == MiniMapRules.FORCED_OFF;
    }

    /**
     * Replaces all non-neutral color codes with §r
     * @param input String input
     */
    private String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("§r");
    }
}
