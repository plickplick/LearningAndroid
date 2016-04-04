package com.example.yatzy.main.score;

class Twos extends Score{
    
    
    public Twos(){         
       super.setMaxScore(10);
       super.setName("Twos");
    }
    
   public int isSelectable(int [] array){
        int score = -99;
        if(array[2] >0){
            score = 2 * array[2];
        }
        
        return score;
    }
 
}