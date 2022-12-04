package me.claegneander.theracraft.data;

import me.claegneander.theracraft.Main;
import me.claegneander.theracraft.misc.Use;
import me.claegneander.theracraft.misc.enums.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Setup {
    /* This class handles our interactions with the ranks. */
    private final Main instance = Main.getPlugin(Main.class).getInstance();
    private final Ranks ranks = instance.getRanks();
    private final PDCs pdc = new PDCs();
    private final Timing timing = new Timing();

    public final String RANK_KEY = "rank";

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
                    String PERMISSION_PREFIX = "theracraft.ranks.";
                    if(player.hasPermission(PERMISSION_PREFIX + Use.key(r.getName()))){
                        rank = Use.key(r.getName());
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
            removeRank(player);
            removeMaterials(player);
            pdc.setPDCString(player, RANK_KEY, rank);
            addMaterials(player);
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
    public String getNextRank(Player player){
        String currentRank = getRank(player);
        String nextRank = currentRank;
        for(Rank r : ranks.getRanks()){
            String rank = Use.key(r.getName());
            if(rank.equals(currentRank)){
                int x = ranks.getRanks().indexOf(r) + 1;
                if(x >= ranks.getRanks().size()){break;}
                nextRank = Use.key(ranks.getRanks().get(x).getName());
            }
        }
        return nextRank;
    }
    public Rank getRankFromString(String string){
        for(Rank r : ranks.getRanks()){
            if(Use.key(r.getName()).equalsIgnoreCase(string)){
                return r;
            }
        }
        return null;
    }
    public void checkRank(Player player){
        String nextRank = getNextRank(player);
        Rank rank = getRankFromString(nextRank);
        player.sendMessage(Component.text("-------------------------------")
                .color(TextColor.fromHexString(Color.getRandomColor())));
        player.sendMessage(Component.text(Messages.sendMultiColoredMessage(
                "&9You are viewing the path to: ", "&6" + nextRank, "&9!")));
        player.sendMessage(Component.text("-------------------------------")
                .color(TextColor.fromHexString(Color.getRandomColor())));
        long requiredTime = rank.getPlayTime();
        long playedTime = timing.getPlayedTime(player);
        float percentage = (float) playedTime/requiredTime;
        int rounded = (int) percentage;
        if(timing.checkTime(player, requiredTime)){
            player.sendMessage(Component.text(Messages.sendMultiColoredMessage(
                    "&9Play for ", "&6"+timing.format(requiredTime), "&9: ", "&a (" + rounded + "%)")));
        }else{
            player.sendMessage(Component.text(Messages.sendMultiColoredMessage(
                    "&9Play for ", "&6"+timing.format(requiredTime), "&9: ", "&c (" + rounded + "%)")));
        }
        for(Material m : rank.materials.keySet()){
            if(pdc.hasPDCInteger(player, String.valueOf(m))){
                int count = pdc.getPDCInteger(player, String.valueOf(m));
                int amount = rank.materials.get(m);
                int x = amount - count;
                if(count == 0){
                    player.sendMessage(Component.text(Messages.sendMultiColoredMessage(
                            "&9Submit ", "&6"+m, "&9: ", "&a"+x, "&9/", "&a"+amount)));
                }else{
                    player.sendMessage(Component.text(Messages.sendMultiColoredMessage(
                            "&9Submit ", "&6"+m, "&9: ", "&c"+x, "&9/", "&c"+amount)));
                }
            }
        }
    }
    public void updateRank(Player player){
        String currentRank = getRank(player);
        String nextRank = getNextRank(player);
        if(!Objects.equals(currentRank, nextRank)) {
            if(isPromotable(player)){
                try {
                    removeRank(player);
                    removeMaterials(player);
                    setRank(player, nextRank);
                    addMaterials(player);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " promote players");
                    Bukkit.getServer().broadcast(Component.text(Messages.sendMultiColoredMessage("&9The player ",
                            "&a" + player.getName(), " &9has ranked up to ", "&a" + nextRank, "&9...?!")));
                    player.sendMessage(Component.text("You have ranked up!")
                            .color(TextColor.fromHexString(Color.SUCCESS.getHEX())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
           }else {
                player.sendMessage(Component.text("You do not meet all the requirements to rank up.")
                        .color(TextColor.fromHexString(Color.ERROR.getHEX())));
                player.sendMessage(Component.text(Messages.sendMultiColoredMessage("&9To see your requirements; ",
                        "&6\"/theracraft check\".")));
            }
        }else{
            player.sendMessage(Component.text("You are already at the highest rank.")
                    .color(TextColor.fromHexString(Color.INFO.getHEX())));
        }
    }
    public void addMaterials(Player player){
        Rank rank = getRankFromString(getNextRank(player));
        for(Material m : rank.getMaterials().keySet()){
            if(!pdc.hasPDCInteger(player, String.valueOf(m))){
                pdc.setPDCInteger(player, String.valueOf(m), rank.materials.get(m));
            }
        }
    }
    public void removeMaterials(Player player){
        for(Material material : Material.values()){
            if(pdc.hasPDCInteger(player, String.valueOf(material))){
                pdc.removePDC(player, String.valueOf(material));
            }
        }
    }
    public void submitMaterials(Player player){
        Rank rank = getRankFromString(getNextRank(player));
        for(Material m : rank.materials.keySet()){
            if(pdc.hasPDCInteger(player, String.valueOf(m))){
                int amount = pdc.getPDCInteger(player, String.valueOf(m));
                removeItem(player, m, amount);
            }
        }
    }
    public boolean isPromotable(Player player){
        Rank rank = getRankFromString(getNextRank(player));
        long requiredTime = rank.getPlayTime();
        long playedTime = timing.getPlayedTime(player);
        boolean time = playedTime > requiredTime;
        int count = 0;
        for(Material m : rank.getMaterials().keySet()){
            if(m != null) {
                if (pdc.hasPDCInteger(player, String.valueOf(m))) {
                    int amount = pdc.getPDCInteger(player, String.valueOf(m));
                    count += amount;
                }
            }
        }
        boolean materials = count == 0;
        return time && materials;
    }
    public void removeItem(Player player, Material material, int amount){
        ItemStack item = new ItemStack(material);
        Inventory i = player.getInventory();
        boolean isRemoved = false;
        int removed = amount;
        for(ItemStack x : i.getContents()) {
            if(amount == 0 ){return;}
            if (x != null) {
                if (x.isSimilar(item)) {
                    if (pdc.hasPDCInteger(player, String.valueOf(material))) {
                        if (x.getAmount() > amount) {
                            x.setAmount(x.getAmount() - amount);
                            amount = 0;
                            pdc.setPDCInteger(player, String.valueOf(material), 0);
                        } else {
                            removed = x.getAmount();
                            amount -= removed;
                            x.setAmount(0);
                            pdc.setPDCInteger(player, String.valueOf(material), amount);
                        }
                        isRemoved = true;
                    }
                    if(isRemoved){
                        player.sendMessage(Component.text(Messages.sendMultiColoredMessage(
                                "&9Removed ", "&6"+removed+" "+material,"&9 from your inventory.")));

                    }
                }
            }
        }
    }
}