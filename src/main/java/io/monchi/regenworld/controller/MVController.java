package io.monchi.regenworld.controller;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Location;

/**
 * @author Mon_chi
 */
public class MVController implements WorldController{

    private MultiverseCore core;

    public MVController(MultiverseCore core) {
        this.core = core;
    }

    @Override
    public boolean isControllable(String name) {
        MultiverseWorld world = core.getMVWorldManager().getMVWorld(name);
        return world != null;
    }

    @Override
    public void regenWorld(String name) {
        core.getMVWorldManager().regenWorld(name, true, true, "");
    }

    @Override
    public void setSpawnLocation(String world, Location location) {
        core.getMVWorldManager().getMVWorld(world).setSpawnLocation(location);
    }
}
