package me.cadox8.sb.island;

import me.cadox8.sb.api.SBUser;

import java.util.ArrayList;
import java.util.List;

public class IslandManager {

    private final List<Island> islands;

    public IslandManager() {
        islands = new ArrayList<>();
    }

    public boolean registerIsland(Island island) {
        if (islandExists(island.getUUID())) return false;
        return islands.add(island);
    }

    public void loadIslands() {

    }

    public Island getIslandByUUID(String uuid) {
        return islands.stream().filter(i -> i.getUUID().equalsIgnoreCase(uuid)).findAny().orElse(null);
    }
    public Island getIslandByOwner(SBUser owner) {
        return islands.stream().filter(i -> i.getOwner().getUuid().equals(owner.getUuid())).findAny().orElse(null);
    }

    public boolean islandExists(String uuid) {
        return islands.stream().anyMatch(i -> i.getUUID().equalsIgnoreCase(uuid));
    }
}
