package com.example.fml24.fml24.Model;

/**
 * Created by adu on 16-07-15.
 */
public class QuestionAnswer {
    private String question;
    private String answer;
    private String body;

    public QuestionAnswer(String title, String time){
        this.question = title;
        this.answer = time;
    }

    public QuestionAnswer(){
        this.question = "";
        this.answer = "";
        this.body = "";
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }

    public String getBody(){
        return body;
    }
}
