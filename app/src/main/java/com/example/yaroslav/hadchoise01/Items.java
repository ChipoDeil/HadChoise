package com.example.yaroslav.hadchoise01;

/**
 * Created by yaroslav on 28.04.2016.
 */
public class Items {
    String name;
    int score;
    public Items(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public String getName(){
        return name;
    }
    public Integer getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
}
