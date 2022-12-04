package me.claegneander.theracraft.data;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class Messages {

    //Each string needs the color code at the front and are appended.
    public static String sendMultiColoredMessage(String... strings){
        StringBuilder builder = new StringBuilder();
        for(String s : strings){
            builder.append(ChatColor.translateAlternateColorCodes('&', s));
        }
        return builder.toString();
    }
}
