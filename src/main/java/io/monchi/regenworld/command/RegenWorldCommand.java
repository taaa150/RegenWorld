package io.monchi.regenworld.command;

import io.monchi.regenworld.RegenWorld;
import io.monchi.regenworld.controller.WorldController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Mon_chi
 */
public class RegenWorldCommand implements CommandExecutor {

    private WorldController controller;

    public RegenWorldCommand() {
        this.controller = RegenWorld.getInstance().getController();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            runCommand(sender, CommandType.HELP, args);
        }
        else {
            for (CommandType type : CommandType.values()) {
                if (type.getName().equalsIgnoreCase(args[0])) {
                    if (args.length < type.getArgsLength()) {
                        sender.sendMessage(ChatColor.RED + "Incorrect usage!");
                        sender.sendMessage(ChatColor.RED + type.getUsage());
                    }
                    else {
                        runCommand(sender, type, args);
                    }
                    return true;
                }
            }
            runCommand(sender, CommandType.HELP, args);
        }
        return true;
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
                }
                else if (!controller.isControllable(args[1])) {
                    sender.sendMessage(ChatColor.RED + "The world is not controllable: " + args[1]);
                }
                else {
                    sender.sendMessage(ChatColor.GREEN + " Start regenerating the world");
                    sender.sendMessage(ChatColor.GREEN + "It may take few seconds...");
                    controller.regenWorld(args[1]);
                    sender.sendMessage(ChatColor.GREEN + "Regeneration has been completed!");
                }
        }
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
