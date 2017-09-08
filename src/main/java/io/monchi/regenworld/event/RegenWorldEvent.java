package io.monchi.regenworld.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Mon_chi
 */
public class RegenWorldEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String worldName;

    public RegenWorldEvent(String worldName) {
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

}
