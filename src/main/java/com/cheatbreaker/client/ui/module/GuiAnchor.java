package com.cheatbreaker.client.ui.module;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GuiAnchor {
    LEFT_TOP("LEFT_TOP"),
    LEFT_MIDDLE("LEFT_MIDDLE"),
    LEFT_BOTTOM("LEFT_BOTTOM"),
    MIDDLE_TOP("MIDDLE_TOP"),
    MIDDLE_MIDDLE("MIDDLE_MIDDLE"),
    MIDDLE_BOTTOM_LEFT("MIDDLE_BOTTOM_LEFT"),
    MIDDLE_BOTTOM_RIGHT("MIDDLE_BOTTOM_RIGHT"),
    RIGHT_TOP("RIGHT_TOP"),
    RIGHT_MIDDLE("RIGHT_MIDDLE"),
    RIGHT_BOTTOM("RIGHT_BOTTOM");

    private final String label;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */


    public String getLabel() {
        return this.label;
    }
}
