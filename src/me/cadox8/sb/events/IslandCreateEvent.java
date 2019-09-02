package me.cadox8.sb.events;

import me.cadox8.sb.island.Island;
import org.bukkit.event.HandlerList;

public class IslandCreateEvent extends IslandEvent {

    public IslandCreateEvent(Island island) {
        super(island);
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
