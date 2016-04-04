package com.example.yatzy.main.score;

class ThreeOfAKind extends Score{
    
    
    public ThreeOfAKind(){
         super.setMaxScore(18);
         super.setName("ThreeOfAKind");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        for(int i = 1; i < array.length; i++){
            if(array[i] > 2){
                score = i * 3;
            }
        }
        return score;
    }
   
}