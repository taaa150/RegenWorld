package io.monchi.regenworld.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Mon_chi
 */
public class RegenWorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            runCommand(sender, CommandType.HELP, args);
        }
        else {
            for (CommandType type : CommandType.values()) {
                if (type.getName().equalsIgnoreCase(args[1])) {
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
        }
    }

    enum CommandType {

        HELP("help", "/rw help", "See list of commands", 1);

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
