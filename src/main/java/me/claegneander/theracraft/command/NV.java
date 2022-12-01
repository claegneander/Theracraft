package me.claegneander.theracraft.command;

import me.claegneander.theracraft.Main;
import me.claegneander.theracraft.command.ancillary.Ancillary;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class NV implements Ancillary, CommandExecutor {
    private final Main plugin = Main.getPlugin(Main.class).getInstance();
    private final ConsoleCommandSender console = plugin.getConsole();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        execute(sender, args);
        return true;
    }
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
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(Component.text("Night vision disabled.")
                    .color(TextColor.fromHexString(Color.ERROR.getHEX())));
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60000, 1));
            player.sendMessage(Component.text("Night vision enabled.")
                    .color(TextColor.fromHexString(Color.SUCCESS.getHEX())));
        }
    }
    @Override
    public String getPermission() {
        return "theracraft.commands.nv";
    }
    @Override
    public String getUsage() {
        return "/nv";
    }
    @Override
    public String getDescription() {
        return "Toggles night vision.";
    }


}
