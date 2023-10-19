package com.cheatbreaker.client.util.render;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RGBData {

    private final int red;
    private final int green;
    private final int blue;
    private int alpha = 255;

}