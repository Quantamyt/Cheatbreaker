package com.cheatbreaker.client.audio.music;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.audio.music.data.Station;
import com.cheatbreaker.client.audio.music.threads.DashQueueThread;
import com.cheatbreaker.client.audio.music.threads.DashThread;
import com.cheatbreaker.client.audio.music.util.DashUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Manages anything to do with the in-game dash music player.
 */
@Getter
public class DashManager {
    private final List<Station> stations = DashUtil.dashHelpers();
    private final DashQueueThread dashQueueThread = new DashQueueThread();
    private final DashThread dashThread;
    @Setter private Station currentStation;

    public DashManager() {
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created Dash Manager");

        this.dashQueueThread.start();
        this.dashThread = new DashThread();
        this.dashThread.start();
        if (this.stations.size() > 0) {
            this.currentStation = this.stations.get(0);
            this.dashQueueThread.offerStation(this.currentStation);
        }
    }
}
