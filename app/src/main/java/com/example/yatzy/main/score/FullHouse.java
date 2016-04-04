package com.example.yatzy.main.score;

class FullHouse extends Score{
    
    public FullHouse(){
         super.setMaxScore(28);
      super.setName("FullHouse");
    }
    
    public int isSelectable(int [] array){
        int score = 0;
        boolean pair = false;
        boolean threeOfAKind = false;
        
        for(int i = 1; i < array.length; i++){
            if(array[i] == 2){
                pair =  true;
                score = i * 2 + score;
            }else if(array[i] == 3){
            	threeOfAKind =  true;
                score = i * 3 + score;
            }
            
        }
        
        if(!pair || !threeOfAKind){
            score = -99;
        }
        
        return score;
    }
 
}