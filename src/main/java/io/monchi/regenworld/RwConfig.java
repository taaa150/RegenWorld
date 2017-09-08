package io.monchi.regenworld;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Mon_chi
 */
public class RwConfig {

    private File file;
    private YamlConfiguration config;

    private int regenInterval;
    private ZonedDateTime nextRegenDate;
    private List<String> worlds;

    private List<String> beforeCommands;
    private List<String> afterCommands;

    public RwConfig(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        this.regenInterval = config.getInt("periodical.interval");
        this.nextRegenDate = ZonedDateTime.parse(config.getString("periodical.date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.worlds = config.getStringList("periodical.worlds");
        this.beforeCommands = config.getStringList("command.before");
        this.afterCommands = config.getStringList("command.after");
    }

    public void save() {
        config.set("periodical.date", nextRegenDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRegenInterval() {
        return regenInterval;
    }

    public ZonedDateTime getNextRegenDate() {
        return nextRegenDate;
    }

    public void setNextRegenDate(ZonedDateTime nextRegenDate) {
        this.nextRegenDate = nextRegenDate;
    }

    public List<String> getWorlds() {
        return worlds;
    }

    public List<String> getBeforeCommands() {
        return beforeCommands;
    }

    public List<String> getAfterCommands() {
        return afterCommands;
    }
}
