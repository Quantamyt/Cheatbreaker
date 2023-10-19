package com.cheatbreaker.client.util.thread;

import com.cheatbreaker.client.CheatBreaker;

import java.net.URISyntaxException;

public class CBAssestConnThread extends Thread {
    private final long reconnectTime = 25000L;

    @Override
    public void run() {
        try {
            Thread.sleep(this.reconnectTime);
            CheatBreaker.getInstance().connectToAssetServer();
        } catch (InterruptedException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }
}
