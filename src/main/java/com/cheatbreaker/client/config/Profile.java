package com.cheatbreaker.client.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Defines the module Profile Object.
 */
@Getter @Setter
public class Profile {
    private String name;
    private boolean notEditable;
    public int index = 0;

    public Profile(String name, boolean notEditable) {
        this.name = name;
        this.notEditable = notEditable;
    }
}
