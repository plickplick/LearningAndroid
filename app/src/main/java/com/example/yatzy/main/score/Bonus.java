package com.example.yatzy.main.score;

class Bonus extends Score{
    
    public Bonus(){         
        super.setMaxScore(50);
        super.setName("Bonus");
        super.lock();
        super.setPlayed();
    }
    
    public int isSelectable(int [] array){
        return -99;
    }
 
}