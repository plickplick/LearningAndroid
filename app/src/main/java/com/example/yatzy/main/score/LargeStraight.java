package com.example.yatzy.main.score;

class LargeStraight extends Score{
    
    public LargeStraight(){         
      super.setMaxScore(20);
      super.setName("LargeStraight");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[2] == 1 && array[3] == 1 && array[4] == 1 && array[5] == 1 && array[6] == 1){
            score = 20;
        }
        return score;
    }
}