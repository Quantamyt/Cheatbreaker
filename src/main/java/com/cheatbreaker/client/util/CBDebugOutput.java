package com.cheatbreaker.client.util;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

public class CBDebugOutput {
    // This will eventually be replaced with a permission from the Player Asset Server
    public static final Set<UUID> uuids =
            Sets.newHashSet(
                    UUID.fromString("d8f72541-823d-4ded-9f7f-b67fdb34f43c"),
                    UUID.fromString("285c25e3-74f6-47e0-81a6-4e74ceb54ed3"),
                    UUID.fromString("2beaac97-f357-493a-9b8c-f7f1976a8f0d"),
                    UUID.fromString("d9c7b72a-d236-484a-b622-ddf6edea6d79")
            );
}
