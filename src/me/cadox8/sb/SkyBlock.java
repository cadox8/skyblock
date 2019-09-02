package me.cadox8.sb;

import lombok.Getter;
import me.cadox8.sb.world.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlock extends JavaPlugin {

    @Getter private static SkyBlock instance;

    public void onEnable() {
        instance = this;

        generateWorld();
    }


    public void generateWorld() {
        if (Bukkit.getWorld("skyblock") == null) {
            Bukkit.getServer().createWorld(new WorldCreator("skyblock").generateStructures(false).type(WorldType.FLAT).generator(new WorldGenerator())).setDifficulty(Difficulty.NORMAL);
        }
    }
}
