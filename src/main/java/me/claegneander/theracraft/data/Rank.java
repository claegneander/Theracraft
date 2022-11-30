package me.claegneander.theracraft.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.HashMap;

public class Rank {

    private Component name, display;
    private long playTime;
    HashMap<Material, Integer> materials;

    public Rank(Component rank, Component display, long playTime, HashMap<Material, Integer> materials){
        this.name = rank;
        this.display = display;
        this.playTime = playTime;
        this.materials = materials;
    }
    public Component getName() {
        return name;
    }
    public void setName(Component name) {
        this.name = name;
    }
    public Component getDisplay() {
        return display;
    }
    public void setDisplay(Component display) {
        this.display = display;
    }
    public long getPlayTime() {
        return playTime;
    }
    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }
    public HashMap<Material, Integer> getMaterials() {
        return materials;
    }
    public void setMaterials(HashMap<Material, Integer> materials) {
        this.materials = materials;
    }
}
