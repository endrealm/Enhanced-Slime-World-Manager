package com.grinderwolf.eswm.plugin.commands.sub;

import org.bukkit.command.CommandSender;

public interface Subcommand {

    boolean onCommand(CommandSender sender, String[] args);

    String getUsage();
    String getDescription();

    default boolean inGameOnly() {
        return false;
    }

    default String getPermission() {
        return "";
    }
}
