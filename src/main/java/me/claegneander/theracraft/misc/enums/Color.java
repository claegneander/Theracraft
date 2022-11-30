package me.claegneander.theracraft.misc.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Color {
    /* These are colors we use for general stuff. */
    DEFAULT("#999999"),
    INFO("#F7EA4A"),
    ERROR("#9A2A2A"),
    SUCCESS("#28DD86");
    private final String HEX;

    Color(String hex){
        this.HEX = hex;
    }
    public static String getRandomColor(){
        List<String> colors = new ArrayList<>();
        for(Color c : Color.values()){
            colors.add(c.getHEX());
        }
        Random random = new Random();
        int x = random.nextInt(colors.size());
        return colors.get(x);
    }
    public String getHEX(){
        return HEX;
    }
}
