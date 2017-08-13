package io.monchi.regenworld;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.monchi.regenworld.command.RegenWorldCommand;
import io.monchi.regenworld.controller.MVController;
import io.monchi.regenworld.controller.WorldController;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RegenWorld extends JavaPlugin {

    private static RegenWorld instance;
    private WorldController controller;

    @Override
    public void onEnable() {
        instance = this;

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

    public WorldController getController() {
        return controller;
    }

    public static RegenWorld getInstance() {
        return instance;
    }
}
