package com.example.fml24.fml24.Model;

/**
 * Created by adu on 16-07-15.
 */
public class GameRule {
    private String title;
    private String description;

    public GameRule(String title, String time){
        this.title = title;
        this.description = time;
    }

    public GameRule(){
        this.title = "";
        this.description = "";
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

}
