package com.cheatbreaker.client.module.impl.normal.hud.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ModuleUtility  - SimpleHudModSize
 * @see AbstractSimpleHudModule
 *
 * This Object handles size for the Simple Modules.
 */
@Getter @AllArgsConstructor
public class SimpleHudModSize {
    public float minHeight;
    public float defaultHeight;
    public float maxHeight;
    public float minWidth;
    public float defaultWidth;
    public float maxWidth;
}

