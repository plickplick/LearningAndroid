package com.example.yatzy.main.score;

class Yahtzee extends Score{
    
       
    public Yahtzee(){
        super.setMaxScore(50); 
        super.setName("Yahtzee");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        
        for(int i = 1; i < array.length; i++){
            if(array[i] == 5){
                score = 50;
            }
        }
        
        return score;
    }
   
}