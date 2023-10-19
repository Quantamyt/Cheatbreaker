package com.cheatbreaker.client.ui.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor // to be made
public abstract class AbstractTheme {

    private String name;
    private String description;
    private String[] authors;

}
