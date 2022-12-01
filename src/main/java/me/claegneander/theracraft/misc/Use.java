package me.claegneander.theracraft.misc;

import me.claegneander.theracraft.data.PDCs;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Use {
    PDCs pdc = new PDCs();
    public static String strip(Component component){
        String s = PlainTextComponentSerializer.plainText().serialize(component);
        s = s.replaceAll("[!@#$%^&*()_\\-\\[\\]]", "");
        return s;
    }
    public static String key(Component component){
        String s = strip(component);
        s = s.replace(" ", "_");
        return s;
    }
}
