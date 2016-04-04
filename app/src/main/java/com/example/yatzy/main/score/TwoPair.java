package com.example.yatzy.main.score;

class TwoPair extends Score{
    
    public TwoPair(){         
      super.setMaxScore(22);
      super.setName("TwoPair");
    }
    
    public int isSelectable(int [] array){
        int score = 0;
        int count = 0;
        
        for(int i = 1; i < array.length; i++){
            if(array[i] == 2){
                count = count + 1;
                score = i * 2 + score;
            }
        }
        
        if(count != 2){
            score = -99;
        }
        
        return score;
    }
 
}