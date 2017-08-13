package io.monchi.regenworld;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * @author Mon_chi
 */
public class RwConfig {

    private YamlConfiguration config;

    private List<String> beforeCommands;
    private List<String> afterCommands;

    public RwConfig(File file) {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        this.beforeCommands = config.getStringList("command.before");
        this.afterCommands = config.getStringList("command.after");
    }

    public List<String> getBeforeCommands() {
        return beforeCommands;
    }

    public List<String> getAfterCommands() {
        return afterCommands;
    }
}
