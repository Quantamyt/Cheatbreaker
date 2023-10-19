package com.cheatbreaker.client.util.render.title;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.util.render.title.data.TitleType;
import com.google.common.collect.Lists;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class TitleManager {
    private final Minecraft mc = Minecraft.getMinecraft();
    @Getter private final List<CBTitle> titles = Lists.newArrayList();

    public TitleManager() {
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created Title Manager");
    }

    public void onGuiDrawEvent(GuiDrawEvent event) {
        GL11.glEnable(3042);
        for (CBTitle title : this.titles) {
            this.mc.cbLoadingScreen.addPhase();
            boolean isSubTitle = title.getTitletype() == TitleType.SUBTITLE;
            float height = isSubTitle ? (float) 4 : 1.5f;
            float width = isSubTitle ? (float) -30 : (float) 10;

            GL11.glScalef(height *= title.getScale(), height, height);

            float f3 = 255;
            if (title.isFading()) {
                long titleTime = title.getFadeInTimeMs() - (System.currentTimeMillis() - title.currentTimeMillis);
                f3 = 1.0f - titleTime / (float) title.getFadeInTimeMs();
            } else if (title.shouldDisplay()) {
                long titleTime = title.getDisplayTimeMs() - (System.currentTimeMillis() - title.currentTimeMillis);
                f3 = titleTime <= 0.0f ? 0.0f : titleTime / (float) title.getFadeOutTimeMs();
            }

            f3 = Math.min(1.0f, Math.max(0.0f, f3));
            if ((double) f3 <= 0.15) {
                f3 = 0.15f;
            }

            this.mc.fontRendererObj.drawCenteredStringWithShadow(title.getMessage(),
                    (int) ((float) (event.getScaledResolution().getScaledWidth() / 2) / height),
                    (int) (((float) (event.getScaledResolution().getScaledHeight() / 2 - this.mc.fontRendererObj.FONT_HEIGHT / 2) + width) / height),
                    new Color(1.0f, 1.0f, 1.0f, f3).getRGB());
            GL11.glScalef(1.0f / height, 1.0f / height, 1.0f / height);
        }
        GL11.glDisable(3042);
    }

    public void onTickEvent(TickEvent ignored) {
        if (!this.titles.isEmpty()) {
            this.titles.removeIf(cBTitle -> cBTitle.currentTimeMillis + cBTitle.getDisplayTimeMs() < System.currentTimeMillis());
        }
    }
}
