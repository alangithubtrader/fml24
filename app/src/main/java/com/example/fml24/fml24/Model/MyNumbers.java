package com.example.fml24.fml24.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by adu on 16-07-16.
 */
public class MyNumbers {
    private ArrayList<String> numbers;
    private String time;
    private String state;

    public MyNumbers(ArrayList<String> title, String time, String state){
        this.numbers = title;
        this.time = time;
        this.state = state;
    }

    public MyNumbers(){
        this.time = "";
        this.state = "";
    }

    public ArrayList<String> getNumbers(){
        return numbers;
    }

    public String getTime(){
        return time;
    }

    public String getState() { return state; }

    public void setState(String setState)
    {
        state = setState;
    }

}
