package me.claegneander.theracraft.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class Use {
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
