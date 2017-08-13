package io.monchi.regenworld.controller;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiversePlugin;

/**
 * @author Mon_chi
 */
public class MVController implements WorldController{

    private MultiverseCore core;

    public MVController(MultiversePlugin mvPlugin) {
        this.core = mvPlugin.getCore();
    }

    @Override
    public void regenWorld(String name) {
        core.getMVWorldManager().regenWorld(name, false, true, "");
    }
}
