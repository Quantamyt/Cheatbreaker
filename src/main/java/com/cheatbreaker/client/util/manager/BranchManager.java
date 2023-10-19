package com.cheatbreaker.client.util.manager;

import com.cheatbreaker.client.CheatBreaker;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BranchManager {

    @Setter private Branch currentBranch;

    public BranchManager() {
        CheatBreaker.getInstance().getLogger().info(CheatBreaker.getInstance().getLoggerPrefix() + "Created Branch Manager");
    }


    public enum Branch {
        DEVELOPMENT("?"),
        BETA("beta"),
        MASTER("master"),
        UNKNOWN("unknown");

        @Getter
        private final String name;
        Branch(String name) {
            this.name = name;
        }

        public static Branch getBranchByName(String name)
        {
            for (Branch value : Branch.values())
            {
                if (!value.getName().equalsIgnoreCase(name)) continue;
                return value;
            }
            return UNKNOWN;
        }

        public boolean isAboveOrEqual(Branch rank)
        {
            return rank.ordinal() >= this.ordinal();
        }
    }
}