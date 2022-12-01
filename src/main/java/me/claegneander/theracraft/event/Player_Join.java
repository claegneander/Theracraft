package me.claegneander.theracraft.event;

import me.claegneander.theracraft.data.PDCs;
import me.claegneander.theracraft.data.Setup;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Player_Join implements Listener {

    private final Setup setup = new Setup();
    private final PDCs pdc = new PDCs();

    @EventHandler
    public void onEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!pdc.hasPDCString(player, setup.RANK_KEY)){
            setup.setRank(player, setup.getRank(player));
        }
    }
}
