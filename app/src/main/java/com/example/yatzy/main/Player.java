package com.example.yatzy.main;

import com.example.yatzy.main.score.ScoreCard;
import com.example.yatzy.main.score.*;

public class Player{
    private ScoreCard scoreCard = new ScoreCard();
    
    private int id;
    private String name;
    private boolean pc;
    private boolean winner = false;
    private boolean myTurn = false;
    public Player(){
    	
    }
    
    public Player(int id, String name){
    	this.id = id;
    	this.name = name;
    	this.pc = false;
    }
    
    public Player(String name){
    	this.name = name;
    }
    
     public ScoreCard getScoreCard(){
         return scoreCard;
     }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    public String toString(){
    	return name;
    }

	public boolean isPc() {
		return pc;
	}

	public void setPc(boolean pc) {
		this.pc = pc;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
    
    
}
