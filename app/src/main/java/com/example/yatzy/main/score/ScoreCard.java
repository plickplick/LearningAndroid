package com.example.yatzy.main.score;

import java.util.LinkedList;
import com.example.yatzy.main.Dice;
import com.example.yatzy.main.Observer;
import com.example.yatzy.main.*;
import java.util.*;

public class ScoreCard {
    private final int upperStart = 0;
    private final int upperEnd = 5;
    private final int bonus = 6;
    private final int lowerStart = 6;
    private final int lowerEnd = 15;
    private final int total = 16;
    
    private int numberOfScoreSet = 0;
    
    private Score[] scores = new Score[17];

    public ScoreCard(){
        scores[0] = new Ones();
        scores[1] = new Twos();
        scores[2] = new Threes();
        scores[3] = new Fours();
        scores[4] = new Fives();
        scores[5] = new Sixes();
        scores[6] = new Bonus();
        scores[7] = new OnePair();
        scores[8] = new SmallStraight();
        scores[9] = new ThreeOfAKind();
        scores[10] = new LargeStraight();
        scores[11] = new TwoPair();        
        scores[12] = new FourOfAKind();
        scores[13] = new FullHouse();
        scores[14] = new Chance();
        scores[15] = new Yahtzee();
        scores[16] = new TotalScore();    
    }
    
    public void setScores(Score[] scores) {
		this.scores = scores;
	}

	/*public int setScore(Dice[] dices,  int[] dicesSort, int s, int click){
    	int tempScore = -99;

    	if(!scores[s].isLocked() && !scores[s].isPlayed()){
        	tempScore = scores[s].isSelectable(dicesSort);
        	if(tempScore > -99){
        		numberOfScoreSet += 1;
        		scores[s].setTurn(numberOfScoreSet);
        		scores[s].setPoints(tempScore);
        		scores[s].setDices(dices);
        		scores[s].setPlayed();
        		calculateScores();
            	//Observer.notifyGui(Observer.UPDATESCORES);
        	}else if((tempScore == -99 && click == 2 && !scores[s].isPlayed())){
        		tempScore = 0;
        		numberOfScoreSet += 1;
        		scores[s].setTurn(numberOfScoreSet);
        		scores[s].setPoints(tempScore);
        		scores[s].setDices(dices);
        		scores[s].setPlayed();
        		calculateScores();
            	//Observer.notifyGui(Observer.UPDATESCORES);
        		tempScore = 0;
        	}
    	}
    	return tempScore;
    }*/

    public int setScore(Dice[] dices,  int[] dicesSort, int s, int click){
        int tempScore = -99;

        if(!scores[s].isLocked() && !scores[s].isPlayed()){
            tempScore = scores[s].isSelectable(dicesSort);
            System.out.println("NU FÖRSÖKER JAG SÄTTA DETTA: "+ tempScore +" " +!scores[s].isLocked() +" " + !scores[s].isPlayed());
            if(tempScore > -99){
                numberOfScoreSet++;
                scores[s].setTurn(numberOfScoreSet);
                scores[s].setPoints(tempScore);
                scores[s].setDices(dices);
                scores[s].setPlayed();

                calculateScores();
                Observer.notifyGui(Observer.UPDATESCORES);
            }else if((tempScore == -99 && click == 2)){
                tempScore = 0;
                numberOfScoreSet++;
                scores[s].setTurn(numberOfScoreSet);
                scores[s].setPoints(tempScore);
                scores[s].setDices(dices);
                scores[s].setPlayed();

                calculateScores();
                Observer.notifyGui(Observer.UPDATESCORES);
            }
        }
        return tempScore;
    }

    private void calculateScores(){

        int tempScore = 0;

        for(int i = upperStart; i <= upperEnd; i++){
            tempScore += scores[i].getPoints();
        }

        if(tempScore > 62){
            scores[bonus].setPoints(50);
        }

        for(int i = lowerStart; i <= lowerEnd; i++){
            tempScore += scores[i].getPoints();
        }

        scores[total].setPoints(tempScore);
    }
    /*private void calculateScores(){
    	
    	int tempScore = 0;
    	
    	for(int i = upperStart; i <= upperEnd; i++){
    		tempScore += scores[i].getPoints();
    	}
    	
    	if(tempScore > 62){
    		scores[bonus].setPoints(50);
    	}
    	
    	for(int i = lowerStart; i <= lowerEnd; i++){
    		tempScore += scores[i].getPoints();
    	}
    	
    	scores[total].setPoints(tempScore);
    }*/
    
    public int getNumberOfScoreSet() {
		return numberOfScoreSet;
	}

	public Score[] getAvailableScores(int [] dices){
        return sort(isSelectable(dices));
    }
    
    public LinkedList<Score> isSelectable(int [] dices){
        LinkedList<Score> scoreListSelectable = new LinkedList<Score>();
        for(int i = 0; i < scores.length; i++){
            if(scores[i].isSelectable(dices) > 0){
                scoreListSelectable.add(scores[i]);
            }
        }        
        return scoreListSelectable;
    }
    
  
    public Score[] sort(LinkedList<Score> scoreListSelectable){
        Score[] orderedScores = new Score[scoreListSelectable.size()];
        
        for(int i = 0; i < orderedScores.length; i++){
           orderedScores[i] = scoreListSelectable.pop();        
        }
        for(int j = orderedScores.length-1; j > 0; j--){
            for(int i = 0; i < j; i++){
                if(orderedScores[i].   getMaxScore() > orderedScores[i+1].getMaxScore()){
                    swap(i, i+1,orderedScores );
                }
            }
        }
         
       
        return orderedScores;
    }
    private void swap(int a, int b, Score[] orderedScores){
        Score temp = orderedScores[a];
        orderedScores[a] = orderedScores[b];
        orderedScores[b] = temp;
    }
    
    
    public Score getScore(int i){
        return scores[i];
    }
    
    public Score[] getScores(){
    	return scores;
    }
}
    
