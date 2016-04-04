package com.example.yatzy.main.score;

class Ones extends Score{
    
    public Ones(){
      super.setMaxScore(5);
      super.setName("Ones");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[1] > 0){
            score = 1 * array[1];
        }
        return score;
    }
    
}