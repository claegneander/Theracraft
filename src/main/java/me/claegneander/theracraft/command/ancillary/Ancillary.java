package me.claegneander.theracraft.command.ancillary;

import org.bukkit.command.CommandSender;

public interface Ancillary {
    /* This is just a simple interface for our ancillary commands. */
    void execute(CommandSender sender, String[] args);
    String getPermission();
    String getUsage();
    String getDescription();
}
