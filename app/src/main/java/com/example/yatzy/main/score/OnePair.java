package com.example.yatzy.main.score;

class OnePair extends Score{
    
    public OnePair(){
         super.setMaxScore(12);
      super.setName("OnePair");
    }
    
    public int isSelectable(int [] array){
        int score = -99;
        for(int i = 1; i < array.length; i++){
            if(array[i] > 1){
                score = i * 2;
            }
        }
        return score;
    }
}