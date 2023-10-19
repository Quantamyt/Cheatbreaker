package com.cheatbreaker.client.audio.music.threads;


import com.cheatbreaker.client.audio.music.data.Station;
import net.minecraft.client.Minecraft;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Used for queuing songs.
 */
public class DashQueueThread extends Thread {
    private final Queue<Station> queueList = new LinkedList<>();

    public void run() {
        try {
            while (true) {
                synchronized (this.queueList) {
                    this.queueList.wait();
                    Station var2 = this.queueList.poll();
                    if (var2 != null) {
                        var2.getData();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Offers a song to the queue.
     */
    public void offerStation(Station station) {
        synchronized (this.queueList) {
            this.queueList.offer(station);
            this.queueList.notify();
        }
    }
}
