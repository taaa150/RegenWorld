package io.monchi.regenworld;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.monchi.regenworld.controller.MVController;
import io.monchi.regenworld.controller.WorldController;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.ZonedDateTime;

public final class RegenWorld extends JavaPlugin {

    private static RegenWorld instance;
    private RwConfig rwConfig;
    private RegenWorldHandler handler;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        this.rwConfig = new RwConfig(new File(getDataFolder(), "config.yml"));
        rwConfig.load();

        WorldController controller;
        if (getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
            controller = new MVController((MultiverseCore)getServer().getPluginManager().getPlugin("Multiverse-Core"));
        }
        else {
            getLogger().severe(ChatColor.RED + "No world management plugin has been found");
            getLogger().severe(ChatColor.RED + "WorldRegen will be disabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.handler = new RegenWorldHandler(controller);
        getServer().getPluginCommand("regenworld").setExecutor(handler);

        ZonedDateTime date = rwConfig.getNextRegenDate();
        ZonedDateTime now = ZonedDateTime.now();
        if (date.isBefore(now)) {
            rwConfig.setNextRegenDate(now);
            new RegenTask().run();
        }
        else {
            handler.scheduleTask(date);
        }
    }

    @Override
    public void onDisable() {
    }

    public RwConfig getRwConfig() {
        return rwConfig;
    }

    public RegenWorldHandler getHandler() {
        return handler;
    }

    public static RegenWorld getInstance() {
        return instance;
    }
}
