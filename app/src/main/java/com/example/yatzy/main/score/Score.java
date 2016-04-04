package com.example.yatzy.main.score;

import com.example.yatzy.main.Dice;

public abstract class Score {

	
    private boolean played = false;
    private boolean locked = false;
    private int points = 0;
    private int maxScore;
    private String name;
	private int turn;
	private int[] dices = new int[5];
	private int id;
	

    public void Score(int id){
    	this.id = id;
    }
    
    public abstract int isSelectable(int[] array);

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed() {
        this.played = true;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }
    
    public void unlock() {
        this.locked = false;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getTurn(){
    	return this.turn;
    }
    
    public void setTurn(int turn){
    	this.turn = turn;
    }
    
    public int[] getDices(){
    	return dices;
    }
    
    public void setDices(Dice[] result){
    	for(int i = 0; i < result.length; i++)
    		dices[i] = result[i].getValue();
    	}
    }
    
