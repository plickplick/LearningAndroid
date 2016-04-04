package com.example.yatzy.main.score;

class Chance extends Score{
    
    public Chance(){
        super.setMaxScore(30);
        super.setName("Chanse");
    }
    
    public int isSelectable(int [] array){
        int score = 0;
        
        for(int i = 1; i < array.length; i++){            
            score = i * array[i] + score;            
        }
        
        return score;
    }
 
}