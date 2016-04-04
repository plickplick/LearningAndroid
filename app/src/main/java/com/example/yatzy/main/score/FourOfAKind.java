package com.example.yatzy.main.score;

class FourOfAKind extends Score{
    
    public FourOfAKind(){
         super.setMaxScore(24);
      super.setName("FourOfAKind");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        for(int i = 1; i < array.length; i++){
            if(array[i] > 3){
                score = i * 4;
            }
        }
        return score;
    }
    
}