package com.example.yatzy.main.score;

class Fours extends Score{
    
    public Fours(){
         super.setMaxScore(20);
         super.setName("Fours");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[4] > 0){
            score = 4 * array[4];
        }
        return score;
    }
}