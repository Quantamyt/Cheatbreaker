package com.cheatbreaker.client.module.command;

import com.cheatbreaker.client.module.AbstractModule;
import lombok.*;

@Getter @AllArgsConstructor
public abstract class ModuleCommand {
    public final AbstractModule mod;
    private final String command;

    /*
        Handles the command.
    */
    public abstract void handle();
}
