package com.example.yatzy.main.score;

class SmallStraight extends Score{
    
    public SmallStraight(){
      super.setMaxScore(15);
      super.setName("SmallStraight");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[1] == 1 && array[2] == 1 && array[3] == 1 && array[4] == 1 && array[5] == 1){
            score = 15;
        }
        return score;
    }
}
