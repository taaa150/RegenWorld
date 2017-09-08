package io.monchi.regenworld.listener;

import io.monchi.regenworld.RegenWorld;
import io.monchi.regenworld.event.PreRegenWorldEvent;
import io.monchi.regenworld.event.RegenWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Mon_chi
 */
public class RegenWorldListener implements Listener {

    @EventHandler
    public void onPreRegenWorld(PreRegenWorldEvent event) {
        for (String s : RegenWorld.getInstance().getRwConfig().getBeforeCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%world%", event.getWorldName()));
        }
    }

    @EventHandler
    public void onRegenWorld(RegenWorldEvent event) {
        for (String s : RegenWorld.getInstance().getRwConfig().getBeforeCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%world%", event.getWorldName()));
        }
    }
}
