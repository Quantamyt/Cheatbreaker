package com.cheatbreaker.client.audio;

public class Microphone {
    private final String description;
    private final String name;

    public Microphone(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }
}
