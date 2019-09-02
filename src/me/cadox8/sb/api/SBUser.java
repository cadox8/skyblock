package me.cadox8.sb.api;

import lombok.Getter;
import me.cadox8.sb.SkyBlock;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SBUser {

    private final SkyBlock plugin = SkyBlock.getInstance();

    @Getter private final UUID uuid;

    public SBUser(Player player) {
        this(player.getName());
    }
    public SBUser(String user) {
        this.uuid = plugin.getServer().getOfflinePlayer(user).getUniqueId();
    }

    public Player getPlayer() {
        return plugin.getServer().getPlayer(uuid);
    }
    public OfflinePlayer getOfflinePlayer() {
        return plugin.getServer().getOfflinePlayer(uuid);
    }
    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }
    public Location getLocation() {
        return getPlayer().getLocation();
    }
    public void teleport(Location loc) {
        getPlayer().teleport(loc);
    }
}
