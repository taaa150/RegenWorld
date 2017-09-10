package io.monchi.regenworld.controller;

import org.bukkit.Location;

/**
 * @author Mon_chi
 */
public interface WorldController {

    /**
     * Check to see if the world is controllable
     * @param name name of the world to check
     * @return true if controllable, false if uncontrollable
     */
    boolean isControllable(String name);

    /**
     * Regenerate a world
     * @param name name of the world to regenerate
     */
    void regenWorld(String name);

    /**
     * Set spawn location of specific world
     * @param world name of world to set spawn location
     * @param location spawn location to set
     */
    void setSpawnLocation(String world, Location location);

}
