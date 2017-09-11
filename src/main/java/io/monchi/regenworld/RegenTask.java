package io.monchi.regenworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.time.ZonedDateTime;
import java.util.TimerTask;

/**
 * @author Mon_chi
 */
public class RegenTask extends TimerTask{

    private RegenWorld instance;
    private RegenWorldHandler handler;
    private RwConfig config;
    private int count;

    public RegenTask() {
        this.instance = RegenWorld.getInstance();
        this.handler = instance.getHandler();
        this.config = instance.getRwConfig();
        this.count = 10;
    }

    @Override
    public void run() {
        if (count == 10 || count == 5 || count == 3 || count == 1) {
            Bukkit.broadcastMessage(config.getWarnMessage().replaceAll("%minute%", String.valueOf(count)));
        }
        else if (count == 0) {
            Bukkit.getScheduler().runTask(instance, () -> {
                config.getWorlds().stream()
                        .filter(s -> instance.getController().isControllable(s))
                        .forEach(handler::regenWorld);

                ZonedDateTime date = config.getNextRegenDate().plusMinutes(config.getRegenInterval());
                handler.scheduleTask(date);
            });
            this.cancel();
        }
        count -= 1;
    }
}
