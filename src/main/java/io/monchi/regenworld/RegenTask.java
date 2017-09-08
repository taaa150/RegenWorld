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
        Bukkit.getScheduler().runTask(RegenWorld.getInstance(), () -> {
            RegenWorldHandler handler = RegenWorld.getInstance().getHandler();
            RwConfig config = RegenWorld.getInstance().getRwConfig();
            config.getWorlds().stream()
                    .filter(s -> handler.getController().isControllable(s))
                    .forEach(handler::regenWorld);

            ZonedDateTime date = config.getNextRegenDate().plusMinutes(config.getRegenInterval());
            handler.scheduleTask(date);
        });
    }
}
