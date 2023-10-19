package com.cheatbreaker.client.util.render.title;

import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.TickEvent;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TitleManager {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final List<CBTitle> titles = Lists.newArrayList();

    public void onGuiDrawEvent(GuiDrawEvent event) {
        if (this.titles.size() == 0) return;

        GL11.glEnable(3042);
        for (CBTitle title : this.titles) {
            boolean isSubTitle = CBTitle.getCBTitleType(title) == TitleType.SUBTITLE;
            float height = isSubTitle ? (float)4 : 1.5f;
            float width = isSubTitle ? (float)-30 : (float)10;
            GL11.glScalef(height *= CBTitle.getTitleScale(title), height, height);
            float f3 = 255;
            if (title.isFading()) {
                long titleTime = CBTitle.getFadeInTimeMS(title) - (System.currentTimeMillis() - title.currentTimeMillis);
                f3 = 1.0f - titleTime / (float)CBTitle.getFadeInTimeMS(title);
            } else if (title.shouldDisplay()) {
                long titleTime = CBTitle.getDisplayTimeMs(title) - (System.currentTimeMillis() - title.currentTimeMillis);
                f3 = titleTime <= 0.0f ? 0.0f : titleTime / (float)CBTitle.getFadeOutTimeMs(title);
            }
            f3 = Math.min(1.0f, Math.max(0.0f, f3));
            if ((double)f3 <= 0.15) {
                f3 = 0.15f;
            }
            this.mc.fontRenderer.drawCenteredStringWithShadow(CBTitle.getTitleMessage(title),
                    (int)((float)(event.getScaledResolution().getScaledWidth() / 2) / height),
                    (int)(((float)(event.getScaledResolution().getScaledHeight() / 2 - this.mc.fontRenderer.FONT_HEIGHT / 2) + width) / height),
                    new Color(1.0f, 1.0f, 1.0f, f3).getRGB());
            GL11.glScalef(1.0f / height, 1.0f / height, 1.0f / height);
        }
        GL11.glDisable(3042);
    }

    public void onTickEvent(TickEvent event) {
        if (!this.titles.isEmpty()) {
            this.titles.removeIf(cBTitle -> cBTitle.currentTimeMillis + CBTitle.getDisplayTimeMs(cBTitle) < System.currentTimeMillis());
        }
    }

    public List<CBTitle> getTitles() {
        return this.titles;
    }
}
