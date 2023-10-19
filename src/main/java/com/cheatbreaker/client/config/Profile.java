package com.cheatbreaker.client.config;

public class Profile {
    private String name;
    private boolean notEditable = false;
    public int index = 0;

    public Profile(String name, boolean notEditable) {
        this.name = name;
        this.notEditable = notEditable;
    }

    public String getName() {
        return this.name;
    }

    public boolean isNotEditable() {
        return this.notEditable;
    }

    public void setName(String name) {
        this.name = name;
    }
}
