package com.example.fml24.fml24.Model;

/**
 * Created by adu on 16-07-16.
 */
public class WinningNumbers {
    private String numbers;
    private String time;

    public WinningNumbers(String time, String numbers){
        this.numbers = numbers;
        this.time = time;
    }

    public WinningNumbers(){
        this.numbers = "";
        this.time = "";
    }

    public String getNumbers(){
        return numbers;
    }

    public String getTime(){
        return time;
    }


}
