package io.monchi.regenworld;

import com.onarandombox.MultiverseCore.api.MultiversePlugin;
import io.monchi.regenworld.controller.MVController;
import io.monchi.regenworld.controller.WorldController;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RegenWorld extends JavaPlugin {

    public static RegenWorld instance;
    private WorldController controller;

    @Override
    public void onEnable() {
        instance = this;

        if (getServer().getPluginManager().isPluginEnabled("MultiVerse-Core")) {
            this.controller = new MVController((MultiversePlugin)getServer().getPluginManager().getPlugin("MultiVerse-Core"));
        }
        else {
            getLogger().severe(ChatColor.RED + "No world management plugin has been found");
            getLogger().severe(ChatColor.RED + "WorldRegen will be disabled");
        }
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
