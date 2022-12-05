package me.claegneander.theracraft.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class World_Change implements Listener {

    @EventHandler
    public void onEvent(PlayerChangedWorldEvent event){
        if(event.getPlayer().getLocation().getWorld().getName().equals("Creative")){
            event.getPlayer().setGameMode(GameMode.CREATIVE);
        }
    }
}
