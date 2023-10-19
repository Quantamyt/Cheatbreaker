package com.cheatbreaker.client.util.sessionserver;

public class SessionServer {
    private final String name;
    private final String url;
    private StatusColor status = StatusColor.UNKNOWN;

    public SessionServer(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public StatusColor getStatus() {
        return this.status;
    }

    public void setStatus(StatusColor sessionServerStatusColor) {
        this.status = sessionServerStatusColor;
    }

    public String getName() {
        return this.name;
    }

    public String getURL() {
        return this.url;
    }
    
    public enum StatusColor {
        UP("green"),
        DOWN("red"),
        BUSY("yellow"),
        UNKNOWN("unknown");

        private final String color;

        public String getIdentifier() {
            return this.color;
        }
        
        StatusColor(final String color) {
            this.color = color;
        }

        public static StatusColor getStatusByName(String string) {
            for (StatusColor status : StatusColor.values()) {
                if (!status.getIdentifier().equalsIgnoreCase(string)) continue;
                return status;
            }
            return null;
        }
    }
}
