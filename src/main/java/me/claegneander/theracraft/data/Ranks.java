package me.claegneander.theracraft.data;

import me.claegneander.theracraft.Main;
import me.claegneander.theracraft.misc.Use;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Ranks {

    private final Main instance = Main.getPlugin(Main.class).getInstance();
    private final ConsoleCommandSender console = instance.getConsole();
    private final List<Rank> ranks;

    public Ranks(Main plugin) {
        FileConfiguration fc = plugin.getInstance().getConfig();
        ranks = new ArrayList<>();
        List<String> temp = new ArrayList<>(Objects.requireNonNull(fc.getConfigurationSection("ranks")).getKeys(false));
        for (String s : temp) {
            Component name = Component.text(s);
            Component display = Component.text(Objects.requireNonNull(fc.getString("ranks." + s + ".display")));
            long playTime = fc.getLong("ranks." + s + ".playtime");
            HashMap<Material, Integer> materials = new HashMap<>();
            try {
                Material material;
                int amount;
                for (String m : Objects.requireNonNull(fc.getConfigurationSection("ranks." + s + ".materials")).getKeys(false)) {
                    if (m != null) {
                        material = Material.matchMaterial(m);
                        amount = fc.getInt("ranks." + s + ".materials." + m);
                        if (material != null) {
                            materials.put(material, amount);
                        }
                    } else {
                        plugin.getConsole().sendMessage(Component.text("WARN: Material type == null; in config.yml.")
                                .color(TextColor.fromHexString(Color.INFO.getHEX())));
                    }
                }
            } catch (Exception e) {
                console.sendMessage(Component.text("[WARN]: Missing material data for: " + s)
                        .color(TextColor.fromHexString(Color.INFO.getHEX())));
            }
            Rank r = new Rank(name, display, playTime, materials);
            ranks.add(r);
        }
    }

    public List<Rank> getRanks() {
        return ranks;
    }
}
