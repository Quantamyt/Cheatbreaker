package com.cheatbreaker.client.util.thread;

import com.cheatbreaker.client.CheatBreaker;

import java.net.URISyntaxException;

public class AssetServerReconnectThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(25000L);
            CheatBreaker.getInstance().connectToAssetServer();
        } catch (InterruptedException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }
}
