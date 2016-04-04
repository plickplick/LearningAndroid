package com.example.yatzy.main;

import com.example.yatzy.main.score.Score;

public class AIPlayer extends Player {
	private final int ones = 0;
	private final int twos = 1;
	private final int threes = 2;
	private final int fours = 3;
	private final int fives = 4;
	private final int sixes = 5;
	private final int bonus =6;
	private final int onePair = 7;
	private final int smallStraight = 8;
	private final int threeOfAKind = 9;
	private final int largeStraight = 10;
	private final int twoPair = 11;
	private final int fourOfAKind = 12;
	private final int fullHouse = 13;
	private final int chanse = 14;
	private final int yathzee = 15;
	private final int totalScore = 16;
	
	private int nrOfRolls = 0;
	private int myTurns = 0;
	private boolean rollAgain = true;
	private GameBoard gameBoard;
	
	public AIPlayer(int id, String name){
		super.setName(name);
    	super.setId(id);
    	super.setPc(true);
	}
	
	
	public String playTurns(GameBoard gameBoard){
		this.gameBoard = gameBoard;
		gameBoard.getGameEngine().rollDices();
		nrOfRolls += 1;
		firstLock();
		gameBoard.getGameEngine().rollDices();
		nrOfRolls += 1;
		firstLock();
		gameBoard.getGameEngine().rollDices();
		String string = gameBoard.getGameEngine().getDices().getSortedResultString();
		setScore();
		this.gameBoard = null;
		return string;
	}
	
	private void firstLock(){	
		Score[] scores = super.getScoreCard().getScores();
		Dices dices = gameBoard.getGameEngine().getDices();
		Dice[] diceArr =dices.getDices();
		int[] dicesSort = dices.getDicesSortedResults();
		//If all dices are set do not go on 
		boolean goOn = true;
		//Checking small straight
		
		if(!super.getScoreCard().getScore(smallStraight).isPlayed() && 0 < super.getScoreCard().getScore(smallStraight).isSelectable(dicesSort)){
			for(int i = 0; i < 5; i++){
				gameBoard.lockDice(String.valueOf(i));
			}			
			goOn = false;
		//Checking large straight
		}else if(!super.getScoreCard().getScore(largeStraight).isPlayed() && 0 < super.getScoreCard().getScore(largeStraight).isSelectable(dicesSort)){
			for(int i = 0; i < 5; i++){
				gameBoard.lockDice(String.valueOf(i));
			}			
			goOn = false;
		//Checking possibility for yathzee
		}else if(!super.getScoreCard().getScore(yathzee).isPlayed()){
			int temp = 0;
			int value = 0;
			//Checking which dice i have most of and how many
			for(int i = 0; i < 7; i++){
				if(dicesSort[i] >= temp){
					temp = dicesSort[i];
					value = i;
				}
			}
			
			if(temp > 3){
				//if I have more than d3 go for yathzee
				//locking dices
				for(int i = 0; i < 5; i++){
					if(value == gameBoard.getGameEngine().getDices().getDice(i).getValue()){
						gameBoard.lockDice(String.valueOf(i));
					}
				}
				goOn = false;
			}
		//End checking and setting yathzee
		}
		
		//If not small/large straight or yarhzee
		//Checking and locking for upper part of board
		if(goOn){
			//Check if upper half is all done.
			int[] tempScores = new int[7];
			int count = 0;
			for(int i = 0; i < bonus; i++){
				if(!super.getScoreCard().getScore(i).isPlayed()){
					tempScores[(i+1)] = 1;
					count += 1;
				}else{
					tempScores[(i+1)] = 0;
				}
			}
			//upper half not done iff count larger than 0 not done see what to set.
			int nrOfDice = 0;
			int value = 0;
			if(count > 0){
				for(int i = 0; i < 7; i++){
					if(1 == tempScores[i] && 0 < dicesSort[i] && dicesSort[i] >= nrOfDice){
						nrOfDice = dicesSort[i];
						value = i;
					}
				}
			}
			//if nrOfDices d2 or more lock them if numberOfRolls is one or two
			if(nrOfDice > 1 && nrOfRolls < 3){
				for(int i = 0; i < 5; i++){
					if(diceArr[i].getValue() == value){
						gameBoard.lockDice(String.valueOf(i));
					}					
				}
				goOn = false;
			}
		}
		
		//Lock highest number of dice
		if(goOn){
			int temp = 0;
			int value = 0;
			//Checking which dice i have most of and how many
			for(int i = 0; i < 7; i++){
				if(dicesSort[i] >= temp){
					temp = dicesSort[i];
					value = i;
				}
			}
			
			for(int i = 0; i < 5; i++){
				if(diceArr[i].getValue() == value){
					gameBoard.lockDice(String.valueOf(i));
				}
			}
		}

	}
	
	private void setScore(){
		nrOfRolls = 0;
		Score[] scores = super.getScoreCard().getScores();
		boolean goOn = true;
		
		//if exist set yatzy
		if(scores[yathzee].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults()) > 0){
			if(!scores[yathzee].isPlayed()){
				gameBoard.setScore((yathzee+"|"+this.getId()), 2);
				goOn = false;
			}
		}
		
		//Setting top half but only if 60% or higher
		if(goOn){
		for(int i = sixes; i > -1; i--){
			int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
			int max = scores[i].getMaxScore();
			int percent = calcPercent(points, max);
			if(percent > 59 && goOn  && !scores[i].isPlayed()){
				gameBoard.setScore(i+"|"+(super.getId()), 2);
				goOn = false;
			}
		}
		}
		//Setting bottom half if top not selectable att 60% decreasing persent to 10%
		if(goOn){
			int limit = 100;
			for(int limt = 100; limit > 0;){
				for(int i = yathzee; i > bonus; i--){
					limit -= 10;
					int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
					int max = scores[i].getMaxScore();
					int percent = calcPercent(points, max);
					if(percent > limit && goOn  && !scores[i].isPlayed()){
						gameBoard.setScore(i+"|"+this.getId(), 2);
						goOn = false;
					}
				}
			}
		}
		//setting upper half at 40% but only to threes
		if(goOn){
			for(int i = 0; i < fours; i++){
				int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
				int max = scores[i].getMaxScore();
				int percent = calcPercent(points, max);
				if(percent > 40 && goOn && !scores[i].isPlayed()){
					gameBoard.setScore((i+"|"+this.getId()), 2);
					goOn = false;
				}
			}
		}
		//Canceling bottom half scores from onePair up.
		if(goOn){		
			for(int i = onePair; i > totalScore; i++){				
				int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
				int max = scores[i].getMaxScore();
				if(goOn  && !scores[i].isPlayed()){
					gameBoard.setScore((i+"|"+this.getId()), 2);
					goOn = false;
				}
			}			
		}
		
		//setting upper half from 40% from fours to sixes
		if(goOn){
			int limit = 39;
				for(int i = fours; i < bonus; i++){
					int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
					int max = scores[i].getMaxScore();
					int percent = calcPercent(points, max);
					if(percent > limit && goOn && !scores[i].isPlayed()){
						gameBoard.setScore((i+"|"+this.getId()), 2);
						goOn = false;
					}
				}			
		}
		
		if(goOn){
			
			for(int k = 100; k >= 0;){
				if(goOn){
					for(int i = onePair; i < totalScore; i++){
						
						int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
						int max = scores[i].getMaxScore();
						int per = calcPercent(points, max);
						if( per >= k && !scores[i].isPlayed()){
							gameBoard.setScore((i+"|"+this.getId()), 2);
							goOn = false;
							break;
						}
					}
				}
				if(goOn){
					for(int i = ones; i < bonus; i++){
						
						int points = scores[i].isSelectable(gameBoard.getGameEngine().getDices().getDicesSortedResults());
						int max = scores[i].getMaxScore();
						int per = calcPercent(points, max);
						if( per >= k && !scores[i].isPlayed()){
							gameBoard.setScore((i+"|"+this.getId()), 2);
							goOn = false;
							break;
						}
					}
				}
				if(!goOn){
					break;
				}
				k = k-5;
			}
		
		}
		Observer.notifyGui(Observer.PCSCORE);
	}
	
	
	private int calcPercent(int points, int max){
		float fmax = max;
		float fpoints = points;
		
		float percent = (points*100) / max;
		int per = Math.round(percent);
		if(per < 0){
			per = 0;
		}
		return per;
	}
}
