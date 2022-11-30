package me.claegneander.theracraft.command;

import me.claegneander.theracraft.command.ancillary.Ancillary;
import me.claegneander.theracraft.command.ancillary.alpha.Help;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Theracraft implements CommandExecutor{
    private final Map<String, Ancillary> commands;
    private final Map<String, String> permissions;
    private final Map<String, String> usages;
    private final Map<String, String> descriptions;

    public Theracraft(){
        commands = new HashMap<>();
        permissions = new HashMap<>();
        usages = new HashMap<>();
        descriptions = new HashMap<>();

        commands.put("help", new Help());

        for(String s : commands.keySet()){
            permissions.put(s, commands.get(s).getPermission());
            usages.put(s, commands.get(s).getUsage());
            descriptions.put(s, commands.get(s).getDescription());
        }
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if (args.length >= 1) {
                try {
                    Ancillary a = commands.get(args[0].toLowerCase());
                    if (a != null) {
                        a.execute(sender, args);
                    } else {
                        player.sendMessage(Component.text("Invalid ancillary command.").color(TextColor.fromHexString(Color.ERROR.getHEX())));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    public Map<String, Ancillary> getCommands() {
        return commands;
    }

    public Map<String, String> getPermissions() {
        return permissions;
    }

    public Map<String, String> getUsages() {
        return usages;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }
}
