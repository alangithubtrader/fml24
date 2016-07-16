package com.example.fml24.fml24;

/**
 * Created by adu on 16-07-15.
 */
public class News {
    private String title;
    private String time;
    private String body;

    public News(String title, String time, String body){
        this.title = title;
        this.time = time;
        this.body = body;
    }

    public News(){
        this.title = "";
        this.time = "";
        this.body = "";
    }

    public String getTitle(){
        return title;
    }

    public String getTime(){
        return time;
    }

    public String getBody(){
        return body;
    }
}
