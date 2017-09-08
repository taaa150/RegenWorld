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

/**
 * @author Mon_chi
 */
public class RegenWorldHandler implements CommandExecutor {

    private RegenWorld instance;
    private WorldController controller;
    private Timer timer;

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

    public void scheduleTask(ZonedDateTime date) {
        if (timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new RegenTask(), Date.from(date.toInstant()));
        instance.getLogger().info("Next regeneration will run at " + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + date.format(DateTimeFormatter.ISO_LOCAL_TIME));
        instance.getRwConfig().setNextRegenDate(date);
        instance.getRwConfig().save();
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
        }
    }

    public void regenWorld(String name) {
        for (String s : instance.getRwConfig().getBeforeCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%world%", name));
        }
        PreRegenWorldEvent preEvent = new PreRegenWorldEvent(name);
        Bukkit.getPluginManager().callEvent(preEvent);
        if (preEvent.isCancelled())
            return;
        
        controller.regenWorld(name);
        for (String s : instance.getRwConfig().getAfterCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%world%", name));
        }

        Bukkit.getPluginManager().callEvent(new RegenWorldEvent(name));
    }

    public WorldController getController() {
        return controller;
    }

    enum CommandType {

        HELP("help", "/rw help", "See list of commands", 1),
        REGEN("regen", "/rw regen <world>", "Regenerate the world", 2);

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
