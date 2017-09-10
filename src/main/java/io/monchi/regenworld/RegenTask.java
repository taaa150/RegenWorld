package io.monchi.regenworld;

import org.bukkit.Bukkit;

import java.time.ZonedDateTime;
import java.util.TimerTask;

/**
 * @author Mon_chi
 */
public class RegenTask extends TimerTask{
    @Override
    public void run() {
        RegenWorld instance = RegenWorld.getInstance();
        RegenWorldHandler handler = instance.getHandler();
        RwConfig config = instance.getRwConfig();
        Bukkit.getScheduler().runTask(instance, () -> {
            config.getWorlds().stream()
                    .filter(s -> instance.getController().isControllable(s))
                    .forEach(handler::regenWorld);

            ZonedDateTime date = config.getNextRegenDate().plusMinutes(config.getRegenInterval());
            handler.scheduleTask(date);
        });
    }
}
