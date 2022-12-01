package me.claegneander.theracraft.command.ancillary.alpha;

import me.claegneander.theracraft.Main;
import me.claegneander.theracraft.command.ancillary.Ancillary;
import me.claegneander.theracraft.data.Setup;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Set_Rank implements Ancillary {
    private final Main plugin = Main.getPlugin(Main.class).getInstance();
    private final ConsoleCommandSender console = plugin.getConsole();
    private final Setup setup = new Setup();
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
        if(args.length > 2) {
            for(Player target : Bukkit.getOnlinePlayers()){
                if(args[1].equalsIgnoreCase(target.getName())){
                    setup.setRank(target, args[2]);
                }
            }
        }
    }
    @Override
    public String getPermission() {
        return "theracraft.commands.setrank";
    }

    @Override
    public String getUsage() {
        return "/theracraft setrank [Player] [Rank]";
    }

    @Override
    public String getDescription() {
        return "Sets the player to the specified rank.";
    }
}