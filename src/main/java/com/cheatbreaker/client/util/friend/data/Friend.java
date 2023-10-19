package com.cheatbreaker.client.util.friend.data;

import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.beans.ConstructorProperties;
import java.util.Objects;

public class Friend {
    private final String playerId;
    private String name;
    private String status;
    private String server;
    private boolean online;
    private long offlineSince;
    private Status onlineStatus = Status.ONLINE;

    public static int getStatusColor(Status playerStatus) {
        if (playerStatus == null) {
            return -13158601;
        }
        int color;
        switch (playerStatus) {
            case AWAY:
                color = new Color(-1722581).getRGB();
                break;
            case BUSY:
                color = new Color(-1758421).getRGB();
                break;
            case OFFLINE:
                color = new Color(-13158601).getRGB();
                break;
            default:
                color = -13369549;
                break;
        }
        return color;
    }

    public String getStatusString() {
        String playingText;
        if (this.online) {
            if (this.server != null && !Objects.equals(this.server, "")) {
                playingText = "Playing" + EnumChatFormatting.BOLD + " " + this.server;
            } else {
                switch (this.onlineStatus) {
                    case AWAY:
                        playingText = "Away";
                        break;
                    case BUSY:
                        playingText = "Busy";
                        break;
                    default:
                        playingText = "Online";
                        break;
                }
            }
        } else {
            long timeDiff = this.offlineSince;
            long seconds = 1000L;
            long minutes = seconds * 60L;
            long hours = minutes * 60L;
            long day = hours * 24L;
            long daysTime = timeDiff / day;
            long hoursTime = (timeDiff %= day) / hours;
            long minutesTime = (timeDiff %= hours) / minutes;
            playingText = daysTime > 0L ? "Offline for " + daysTime + (daysTime == 1L ? " day" : " days") :
                    (hoursTime > 0L ? "Offline for " + hoursTime + (hoursTime == 1L ? " hour" : " hours") : "Offline for " + minutesTime +
                            (minutesTime == 1L ? " minute" : " minutes"));
        }
        return playingText;
    }

    @ConstructorProperties(value = {"playerId", "name", "status", "server", "online", "offlineSince", "onlineStatus"})
    Friend(String string, String string2, String string3, String string4, boolean bl, long l, Status playerStatus) {
        this.playerId = string;
        this.name = string2;
        this.status = string3;
        this.server = string4;
        this.online = bl;
        this.offlineSince = l;
        this.onlineStatus = playerStatus;
    }

    public static FriendBuilder builder() {
        return new FriendBuilder();
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    public String getServer() {
        return this.server;
    }

    public boolean isOnline() {
        return this.online;
    }

    public long getOfflineSince() {
        return this.offlineSince;
    }

    public Status getOnlineStatus() {
        return this.onlineStatus;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setStatus(String string) {
        this.status = string;
    }

    public void setServer(String string) {
        this.server = string;
    }

    public void setOnline(boolean bl) {
        this.online = bl;
    }

    public void setOfflineSince(long l) {
        this.offlineSince = l;
    }

    public void setOnlineStatus(Status playerStatus) {
        this.onlineStatus = playerStatus;
    }

    public static class FriendBuilder {
        private String playerId;
        private String name;
        private String status;
        private String server;
        private boolean online;
        private long offlineSince;
        private Status onlineStatus;

        FriendBuilder() {
        }

        public FriendBuilder playerId(String string) {
            this.playerId = string;
            return this;
        }

        public FriendBuilder name(String string) {
            this.name = string;
            return this;
        }

        public FriendBuilder status(String string) {
            this.status = string;
            return this;
        }

        public FriendBuilder server(String string) {
            this.server = string;
            return this;
        }

        public FriendBuilder online(boolean bl) {
            this.online = bl;
            return this;
        }

        public FriendBuilder offlineSince(long l) {
            this.offlineSince = l;
            return this;
        }

        public FriendBuilder onlineStatus(Status playerStatus) {
            this.onlineStatus = playerStatus;
            return this;
        }

        public Friend build() {
            return new Friend(this.playerId, this.name, this.status, this.server, this.online, this.offlineSince, this.onlineStatus);
        }

        public String toString() {
            return "Friend.FriendBuilder(playerId=" + this.playerId + ", name=" + this.name + ", status=" + this.status + ", server=" + this.server + ", online=" + this.online + ", offlineSince=" + this.offlineSince + ", onlineStatus=" + this.onlineStatus + ")";
        }
    }

    public enum Status {
        ONLINE,
        AWAY,
        BUSY,
        OFFLINE
    }
}
