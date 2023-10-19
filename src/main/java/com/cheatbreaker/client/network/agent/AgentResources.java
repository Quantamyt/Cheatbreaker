package com.cheatbreaker.client.network.agent;

public class AgentResources {
    @AgentByteArrayReference
    public static native byte[] getBytesNative(String var0);

    @AgentBooleanReference
    public static native boolean existsBytesNative(String var0);

    public static boolean existsBytes(String string) {
        boolean bl = false;
        try {
            bl = AgentResources.existsBytesNative(string);
        } catch (UnsatisfiedLinkError ignore) {

        }
        return bl;
    }
}
