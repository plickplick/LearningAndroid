package com.example.yatzy.main.score;

class TotalScore extends Score{
    
    
    public TotalScore(){
         super.setMaxScore(374);
         super.setName("TotalScore");
         super.lock();
         super.setPlayed();
    }
    
    public int isSelectable(int [] array){
        return -99;
    }
  
}