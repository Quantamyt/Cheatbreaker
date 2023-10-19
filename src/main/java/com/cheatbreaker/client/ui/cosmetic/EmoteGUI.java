package com.cheatbreaker.client.ui.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Emote;
import net.minecraft.client.Minecraft;

import java.util.Objects;
import java.util.stream.Collectors;

public class EmoteGUI extends WheelGUI {
    public EmoteGUI(int n) {
        super(n, CheatBreaker.getInstance().getCosmeticManager().getEmotes().stream()
                .map(CheatBreaker.getInstance().getCosmeticManager()::getEmoteById).filter(Objects::nonNull).limit(8L)
                .map(emote -> new IconButton(emote, emote.getName(), emote.getResourceLocation())).collect(Collectors.toList()));

        this.consumer = (iconButton -> {
            Emote emote = CheatBreaker.getInstance().getCosmeticManager().getEmoteById(n);
            System.out.println("Playing Emote: " + emote.getName());
            CheatBreaker.getInstance().getCosmeticManager().playEmote(Minecraft.getMinecraft().thePlayer, emote);
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