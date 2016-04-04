package com.example.yatzy.main.score;

class Fives extends Score{
    
    public Fives(){         
        super.setMaxScore(25);
        super.setName("Fives");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        if(array[5] > 0){
            score = 5 * array[5];
        }
        return score;
    }
}