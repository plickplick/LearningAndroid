package com.example.yatzy.main;

import java.util.Random;

public class Dice {

	private int value;
	private int id;
	private boolean isLocked;
    private Random random;
    
    public Dice(int id){
    	this.id = id;
    	this.isLocked = false;
    	this.random = new Random();
    }
	
	public int getValue() {
            return value;
	}

	public boolean getIsLocked() {
		return isLocked;
	}

	public void roll() {
		if(!isLocked){
			value = random.nextInt(6)+1;
		}
	}
            
	public void lock() {
		this.isLocked = true;
	}
	
	public void unLock() {
		this.isLocked = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}