package me.cadox8.sb.events;

import lombok.RequiredArgsConstructor;
import me.cadox8.sb.island.Island;
import org.bukkit.event.Event;

@RequiredArgsConstructor
public abstract class IslandEvent extends Event {

    private final Island island;
}
