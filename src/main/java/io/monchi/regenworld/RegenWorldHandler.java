package io.monchi.regenworld;

import io.monchi.regenworld.controller.WorldController;
import io.monchi.regenworld.event.PreRegenWorldEvent;
import io.monchi.regenworld.event.RegenWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Mon_chi
 */
public class RegenWorldHandler implements CommandExecutor {

    private RegenWorld instance;
    private WorldController controller;
    private Timer timer;
    private TimerTask task;

    public RegenWorldHandler(WorldController controller) {
        this.instance = RegenWorld.getInstance();
        this.controller = controller;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            runCommand(sender, CommandType.HELP, args);
        } else {
            for (CommandType type : CommandType.values()) {
                if (type.getName().equalsIgnoreCase(args[0])) {
                    if (args.length < type.getArgsLength()) {
                        sender.sendMessage(ChatColor.RED + "Incorrect usage!");
                        sender.sendMessage(ChatColor.RED + type.getUsage());
                    } else {
                        runCommand(sender, type, args);
                    }
                    return true;
                }
            }
            runCommand(sender, CommandType.HELP, args);
        }
        return true;
    }

    public ZonedDateTime scheduleTask(ZonedDateTime date) {
        resetTask();
        timer = new Timer();
        task = new RegenTask();
        timer.scheduleAtFixedRate(task, Date.from(date.minusMinutes(10).toInstant()), TimeUnit.MINUTES.toMillis(1));
        instance.getLogger().info("Next regeneration will run at " + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + date.format(DateTimeFormatter.ISO_LOCAL_TIME));
        instance.getRwConfig().setNextRegenDate(date);
        instance.getRwConfig().save();
        return date;
    }

    public void resetTask() {
        if (timer != null)
            timer.cancel();
        if (task != null)
            task.cancel();
    }

    private void runCommand(CommandSender sender, CommandType command, String[] args) {
        switch (command) {
            case HELP:
                for (CommandType type : CommandType.values()) {
                    sender.sendMessage(ChatColor.GOLD + type.getUsage() + ": " + ChatColor.WHITE + type.getDescription());
                }
                break;
            case REGEN:
                if (Bukkit.getWorld(args[1]) == null) {
                    sender.sendMessage(ChatColor.RED + "The world is not found: " + args[1]);
                } else if (!controller.isControllable(args[1])) {
                    sender.sendMessage(ChatColor.RED + "The world is not controllable: " + args[1]);
                } else {
                    sender.sendMessage(ChatColor.GREEN + " Start regenerating the world");
                    sender.sendMessage(ChatColor.GREEN + "It may take few seconds...");
                    regenWorld(args[1]);
                    sender.sendMessage(ChatColor.GREEN + "Regeneration has been completed!");
                }
                break;
            case SCHEDULE:
                ZonedDateTime date = scheduleTask(ZonedDateTime.now().plusMinutes(RegenWorld.getInstance().getRwConfig().getRegenInterval()));
                sender.sendMessage("Next regeneration will run at " + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + date.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
    }

    public void regenWorld(String name) {
        PreRegenWorldEvent preEvent = new PreRegenWorldEvent(name);
        Bukkit.getPluginManager().callEvent(preEvent);
        if (preEvent.isCancelled())
            return;
        
        controller.regenWorld(name);

        Bukkit.getPluginManager().callEvent(new RegenWorldEvent(name));
    }

    enum CommandType {

        HELP("help", "/rw help", "See list of commands", 1),
        REGEN("regen", "/rw regen <world>", "Regenerate the world", 2),
        SCHEDULE("schedule", "/rw schedule", "Schedule next regeneration according to interval", 1);

        private String name;
        private String usage;
        private String description;
        private int argsLength;

        CommandType(String name, String usage, String description, int argsLength) {
            this.name = name;
            this.usage = usage;
            this.description = description;
            this.argsLength = argsLength;
        }

        public String getName() {
            return name;
        }

        public String getUsage() {
            return usage;
        }

        public String getDescription() {
            return description;
        }

        public int getArgsLength() {
            return argsLength;
        }
    }
}
