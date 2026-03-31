package com.lukaa.barriogen;

import org.bukkit.plugin.java.JavaPlugin;

public class BarrioGenPlugin extends JavaPlugin {

    private static BarrioGenPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getLogger().info("=== BarrioGen activado correctamente ===");
    }

    @Override
    public void onDisable() {
        getLogger().info("=== BarrioGen desactivado ===");
    }

    public static BarrioGenPlugin getInstance() {
        return instance;
    }
}