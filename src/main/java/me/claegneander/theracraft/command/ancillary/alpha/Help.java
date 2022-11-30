package me.claegneander.theracraft.command.ancillary.alpha;

import me.claegneander.theracraft.Main;
import me.claegneander.theracraft.command.Theracraft;
import me.claegneander.theracraft.command.ancillary.Ancillary;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Help implements Ancillary {
    private final Main plugin = Main.getPlugin(Main.class).getInstance();
    private final ConsoleCommandSender console = plugin.getConsole();
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            console.sendMessage(Component.text("You must be a player to use this command.")
                    .color(TextColor.fromHexString(Color.ERROR.getHEX())));
            return;
        }
        if (!player.hasPermission(getPermission())) {
            player.sendMessage(Component.text("Missing permission: " + getPermission())
                    .color(TextColor.fromHexString(Color.ERROR.getHEX())));
            return;
        }
        Theracraft theracraft = new Theracraft();
        for(String s : theracraft.getPermissions().keySet()){
            if(player.hasPermission(theracraft.getPermissions().get(s))){
                player.sendMessage(Component.text(theracraft.getDescriptions().get(s))
                        .color(TextColor.fromHexString(Color.INFO.getHEX())));
                player.sendMessage(Component.text(theracraft.getUsages().get(s))
                        .color(TextColor.fromHexString(Color.INFO.getHEX())));
            }
        }
    }
    @Override
    public String getPermission() {
        return "theracraft.commands.help";
    }

    @Override
    public String getUsage() {
        return "/theracraft help";
    }

    @Override
    public String getDescription() {
        return "Shows information about our commands.";
    }
}
