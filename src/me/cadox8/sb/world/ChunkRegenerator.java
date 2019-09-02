package me.cadox8.sb.world;

import jdk.internal.jline.internal.Nullable;
import lombok.NonNull;
import me.cadox8.sb.SkyBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChunkRegenerator {

    private final SkyBlock plugin;
    private final ChunkGenerator chunkGen;
    private final World world;
    private BukkitTask task;

    public ChunkRegenerator(@NonNull World world) {
        Validate.notNull(world, "World cannot be null");

        this.plugin = SkyBlock.getInstance();
        this.world = world;
        this.chunkGen = plugin.getDefaultWorldGenerator(world.getName(), "");
    }

    public void regenerateChunks(@NonNull List<Chunk> chunkList, @Nullable Runnable onCompletion) {
        Validate.notNull(chunkList, "ChunkList cannot be empty");

        final int CHUNKS_PER_TICK = 5;
        final BukkitScheduler scheduler = plugin.getServer().getScheduler();
        task = scheduler.runTaskTimer(plugin, () -> {
            for (int i = 0; i < CHUNKS_PER_TICK; i++) {
                if (!chunkList.isEmpty()) {
                    final Chunk chunk = chunkList.remove(0);
                    regenerateChunk(chunk);
                } else {
                    if (onCompletion != null) {
                        scheduler.runTaskLater(plugin, onCompletion, 1L);
                    }
                    task.cancel();
                }
            }
        }, 0L, 1L);
    }

    public void regenerateChunk(@NonNull Chunk chunk) {
        final Random random = new Random();
        final ChunkGenerator.BiomeGrid biomeGrid = new DefaultBiomeGrid(world.getEnvironment());
        final ChunkGenerator.ChunkData chunkData = chunkGen.generateChunkData(world, random, chunk.getX(), chunk.getZ(), biomeGrid);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.getBlock(x, 0, z).setBiome(biomeGrid.getBiome(x, z));
                for (int y = 0; y < chunk.getWorld().getMaxHeight(); y++) {
                    chunk.getBlock(x, y, z).setBlockData(chunkData.getBlockData(x, y, z));
                }
            }
        }

        removeEntities(chunk);
    }

    private void removeEntities(@NonNull Chunk chunk) {
        Arrays.stream(chunk.getEntities()).filter(entity -> !(entity instanceof Player)).forEach(Entity::remove);
    }

    class DefaultBiomeGrid implements ChunkGenerator.BiomeGrid {
        private Biome defaultBiome;

        DefaultBiomeGrid(World.Environment env) {
            switch (env) {
                case THE_END:
                    defaultBiome = Biome.THE_END;
                    break;
                case NETHER:
                    defaultBiome = Biome.NETHER;
                    break;
                default:
                    defaultBiome = Biome.OCEAN;
                    break;
            }
        }

        @NonNull
        @Override
        public Biome getBiome(int x, int z) {
            return defaultBiome;
        }

        @Override
        public void setBiome(int x, int z, @NonNull Biome bio) {
            defaultBiome = bio;
        }
    }
}
