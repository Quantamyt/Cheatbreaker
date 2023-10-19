package com.cheatbreaker.client.ui.module;

public enum Position {
    BOTTOM("BOTTOM"),
    TOP("TOP"),
    CENTER("CENTER"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private final String identifier;

    Position(String identifier) {
        this.identifier = identifier;
    }


    public String getIdentifier() {
        return this.identifier;
    }
}
