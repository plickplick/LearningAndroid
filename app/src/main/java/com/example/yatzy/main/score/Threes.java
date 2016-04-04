package com.example.yatzy.main.score;

class Threes extends Score{
    
    public Threes(){
      super.setMaxScore(15);
      super.setName("Threes");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[3] > 0){
            score = 3 * array[3];
        }
        return score;
    }
 
}