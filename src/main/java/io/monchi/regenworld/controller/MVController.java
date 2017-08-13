package io.monchi.regenworld.controller;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiversePlugin;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;

/**
 * @author Mon_chi
 */
public class MVController implements WorldController{

    private MultiverseCore core;

    public MVController(MultiversePlugin mvPlugin) {
        this.core = mvPlugin.getCore();
    }

    @Override
    public boolean isControllable(String name) {
        MultiverseWorld world = core.getMVWorldManager().getMVWorld(name);
        return world != null;
    }

    @Override
    public void regenWorld(String name) {
        core.getMVWorldManager().regenWorld(name, false, true, "");
    }
}
