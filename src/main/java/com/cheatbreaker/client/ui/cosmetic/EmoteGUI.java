package com.cheatbreaker.client.ui.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Emote;
import net.minecraft.client.Minecraft;

import java.util.Objects;
import java.util.stream.Collectors;

public class EmoteGUI extends WheelGUI {
    public EmoteGUI(int n) {
        super(n, CheatBreaker.getInstance().getEmoteManager().getEmotes().stream()
                .map(CheatBreaker.getInstance().getEmoteManager()::getEmote).filter(Objects::nonNull).limit(8L)
                .map(emote -> new IconButton(emote, emote.getName(), emote.getResourceLocation())).collect(Collectors.toList()));
        this.consumer = (iconButton -> {
            if (iconButton != null) {
                CheatBreaker.getInstance().getEmoteManager().playEmote(Minecraft.getMinecraft().thePlayer, (Emote) iconButton.getObject());
            }
        });
    }

    @Override
    public void drawScreen(int n, int n2, float n3) {
        super.drawScreen(n, n2, n3);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
 