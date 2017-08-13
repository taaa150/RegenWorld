package io.monchi.regenworld;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.monchi.regenworld.command.RegenWorldCommand;
import io.monchi.regenworld.controller.MVController;
import io.monchi.regenworld.controller.WorldController;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RegenWorld extends JavaPlugin {

    private static RegenWorld instance;
    private RwConfig rwConfig;
    private WorldController controller;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        this.rwConfig = new RwConfig(new File(getDataFolder(), "config.yml"));
        rwConfig.load();

        if (getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
            this.controller = new MVController((MultiverseCore)getServer().getPluginManager().getPlugin("Multiverse-Core"));
        }
        else {
            getLogger().severe(ChatColor.RED + "No world management plugin has been found");
            getLogger().severe(ChatColor.RED + "WorldRegen will be disabled");
            getServer().getPluginManager().disablePlugin(this);
        }

        getServer().getPluginCommand("regenworld").setExecutor(new RegenWorldCommand());
    }

    @Override
    public void onDisable() {
    }

    public RwConfig getRwConfig() {
        return rwConfig;
    }

    public WorldController getController() {
        return controller;
    }

    public static RegenWorld getInstance() {
        return instance;
    }
}
