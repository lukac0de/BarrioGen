package com.lukaa.barriogen.config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final FileConfiguration cfg;

    public ConfigManager(FileConfiguration cfg) {
        this.cfg = cfg;
    }

    public String getWorldName() {
        return cfg.getString("world.name", "barriogen_world");
    }

    public long getSeed() {
        return cfg.getLong("world.seed", 12345L);
    }

    public int getGroundY() {
        return cfg.getInt("world.ground-y", 64);
    }

    public int getTerrainVariation() {
        return cfg.getInt("world.terrain-variation", 4);
    }
}