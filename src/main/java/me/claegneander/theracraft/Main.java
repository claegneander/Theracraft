package me.claegneander.theracraft;

import me.claegneander.theracraft.command.NV;
import me.claegneander.theracraft.command.Theracraft;
import me.claegneander.theracraft.data.Ranks;
import me.claegneander.theracraft.event.Player_Join;
import me.claegneander.theracraft.event.World_Change;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private Main instance;
    private ConsoleCommandSender console;
    private Ranks ranks;
    @Override
    public void onEnable() {
        instance = this;
        console = instance.getServer().getConsoleSender();
        ranks = new Ranks(instance);

        saveDefaultConfig();

        registerCommands();
        registerEvents();

        console.sendMessage(Component.text("[Theracraft]: Enabled.")
                .color(TextColor.fromHexString(Color.SUCCESS.getHEX())));
    }
    @Override
    public void onDisable() {
        ranks = null;
        console.sendMessage(Component.text("[Theracraft]: Disabled.")
                .color(TextColor.fromHexString(Color.ERROR.getHEX())));
        console = null;
        instance = null;
    }
    public void registerCommands(){
        try{
            Objects.requireNonNull(instance.getCommand("theracraft")).setExecutor(new Theracraft());
            Objects.requireNonNull(instance.getCommand("nv")).setExecutor(new NV());
            console.sendMessage(Component.text("[Theracraft]: Commands registered.")
                    .color(TextColor.fromHexString(Color.INFO.getHEX())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void registerEvents(){
        PluginManager pm = this.getServer().getPluginManager();
        try{
            List<Listener> listeners = new ArrayList<>();
            listeners.add(new Player_Join());
            listeners.add(new World_Change());
            for(Listener l : listeners){
                pm.registerEvents(l, instance);
                console.sendMessage(Component.text("[Theracraft]: Registering the listener: " + l)
                        .color(TextColor.fromHexString(Color.INFO.getHEX())));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Main getInstance(){
        return instance;
    }
    public ConsoleCommandSender getConsole() {
        return console;
    }

    public Ranks getRanks() {
        return ranks;
    }
}
