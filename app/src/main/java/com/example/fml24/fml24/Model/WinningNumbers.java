package com.example.fml24.fml24.Model;

import java.util.ArrayList;

/**
 * Created by adu on 16-07-16.
 */
public class WinningNumbers {
    private ArrayList<String> numbers;
    private String time;

    public WinningNumbers(String time, ArrayList<String> numbers){
        this.numbers = numbers;
        this.time = time;
    }

    public WinningNumbers(){
        this.numbers = null;
        this.time = "";
    }

    public ArrayList<String> getNumbers(){
        return numbers;
    }

    public String getTime(){
        return time;
    }


}
