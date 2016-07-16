package com.example.fml24.fml24.Model;

/**
 * Created by adu on 16-07-16.
 */
public class MyNumbers {
    private String numbers;
    private String time;
    private String state;

    public MyNumbers(String title, String time, String state){
        this.numbers = title;
        this.time = time;
        this.state = state;
    }

    public MyNumbers(){
        this.numbers = "";
        this.time = "";
        this.state = "";
    }

    public String getNumbers(){
        return numbers;
    }

    public String getTime(){
        return time;
    }

    public String getState() { return state; }

}
