package me.claegneander.theracraft.data;

import me.claegneander.theracraft.Main;
import me.claegneander.theracraft.misc.Use;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Setup {
    /* This class handles our interactions with the ranks. */
    private final Main instance = Main.getPlugin(Main.class).getInstance();
    private final Ranks ranks = instance.getRanks();
    private final PDCs pdc = new PDCs();

    private final String RANK_KEY = "rank";
    private final String PERMISSION_PREFIX = "theracraft.ranks.";

    public String getRank(Player player){
        String rank = Use.key(ranks.getRanks().get(0).getName());
        if(pdc.hasPDCString(player, RANK_KEY)){
            rank = pdc.getPDCString(player, RANK_KEY);
        }else{
            boolean isOP = player.isOp();
            if(isOP){
                player.setOp(false);
            }
            try{
                for(Rank r : ranks.getRanks()){
                    if(player.hasPermission(PERMISSION_PREFIX + Use.key(r.getName()))){
                        rank = Use.key(ranks.getNames().get(r));
                    }
                }
            }catch (Exception e){
                instance.getConsole().sendMessage(Component.text("No rank found.")
                        .color(TextColor.fromHexString(Color.ERROR.getHEX())));
                e.printStackTrace();
            }
            if(isOP){
                player.setOp(true);
            }
        }
        return rank;
    }
    public void setRank(Player player, String rank){
        try{
            pdc.setPDCString(player, RANK_KEY, rank);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeRank(Player player){
        if(pdc.hasPDCString(player, RANK_KEY)){
            pdc.removePDC(player, RANK_KEY);
            removeMaterials(player);
        }
    }
    public void checkRank(Player player){

    }
    public void updateRank(Player player){
        String currentRank = getRank(Player);
        String nextRank = currentRank;
        for(Component c : ranks.getNames().values()){
            if(Use.key(c).equals(currentRank)){
                int x = ranks.getNames().indexOf(c) - 1;
                if(x <= 0){break;}
                if(x >= ranks.getNames().size()){break;}
                nextRank = ranks.getNames().get(x);
            }
        }
        if(!Objects.equals(currentRank, nextRank)) {
            if(isPromotable(player)){
                try {
                    removeRank(player);
                    removeMaterials(player);
                    setRank(player, nextRank);
                    addMaterials(player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
           }else {
                player.sendMessage(Component.text("You do not meet all the requirements to rank up.").color(TextColor.fromHexString(Color.ERROR.getHEX()));
                player.sendMessage(Component.text("To see your requirements; \"/theracraft check\".").color(TextColor.fromHexString(Color.INFO.getHEX()));
            }
        }else{
            player.sendMessage(Component.text("You are already at the highest rank.").color(TextColor.fromHexString(Color.INFO.getHEX()));
        }
    }
    public void addMaterials(Player player){

    }
    public void removeMaterials(Player player){
        for(Material material : Material.values()){
            if(pdc.hasPDCInteger(player, String.valueOf(material))){
                pdc.removePDC(player, String.valueOf(material));
            }
        }
    }
    public void submitMaterials(Player player){

    }
    public boolean isPromotable(Player player){
        return false;
    }
}
