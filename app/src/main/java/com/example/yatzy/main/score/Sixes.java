package com.example.yatzy.main.score;

class Sixes extends Score{
    
    public Sixes(){
        super.setMaxScore(30);
        super.setName("Sixes");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[6] > 0){
            score = 6 * array[6];
        }
        return score;
    }
}