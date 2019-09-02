package me.cadox8.sb.island;

import lombok.Getter;
import me.cadox8.sb.api.SBUser;
import me.cadox8.sb.world.ChunkRegenerator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Island {

    @Getter private final String UUID;
    @Getter private final SBUser owner;

    private Location spawnLoc;

    private List<Chunk> affectedChunks;

    public Island(String UUID, SBUser owner) {
        this.UUID = UUID;
        this.owner = owner;

        spawnLoc = null;
        affectedChunks = new ArrayList<>();
    }

    public void safeTeleport() {
        if (spawnLoc.getBlock().getType() != Material.AIR || spawnLoc.subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
            // ToDo: Search nearest safe point
        }
        owner.teleport(spawnLoc);
    }

    private void gen(Location loc) {
        spawnLoc = loc;


    }

    public void remove() {
        final ChunkRegenerator cr = new ChunkRegenerator(spawnLoc.getWorld());

        cr.regenerateChunks(affectedChunks, null);
    }
}
