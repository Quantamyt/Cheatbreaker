package com.cheatbreaker.client.audio.music.threads;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.audio.music.data.Station;

import java.time.Duration;
import java.time.LocalDateTime;

public class DashThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    Station station;
                    if ((station = CheatBreaker.getInstance().getDashManager().getCurrentStation()) != null && station.getStartTime() != null && Duration.between(station.getStartTime(), LocalDateTime.now()).toMillis() / 1000L >= (long) (station.getDuration() + 2)) {
                        station.getData();
                        Thread.sleep(4000L);
                    }
                    Thread.sleep(1000L);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
