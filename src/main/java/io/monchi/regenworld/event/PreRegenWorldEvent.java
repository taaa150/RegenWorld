package io.monchi.regenworld.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Mon_chi
 */
public class PreRegenWorldEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;
    private String worldName;

    public PreRegenWorldEvent(String worldName) {
        this.isCancelled = false;
        this.worldName = worldName;
    }

    public String getWorldName() {
        return this.worldName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
