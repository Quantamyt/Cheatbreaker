package com.cheatbreaker.client.ui.cosmetic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;

@AllArgsConstructor
@Getter
public class IconButton {
    private final Object object;
    private final String name;
    private final ResourceLocation image;
}
