package com.example.yatzy.main;

public class Dices {

	private Dice[] dices = new Dice[5];
    private int[] sortedResult = new int[7];
    private String sortedResultString = "";
    
   
	public Dices(){
        for(int i = 0; i < dices.length; i++){
			dices[i] = new Dice(i);
			dices[i].roll();
        }
    }
/**
	 * 
	 * @return 
	 */
	public void rollDices() {
		for(int i = 0; i < sortedResult.length; i++){
            sortedResult[i] = 0;               
        }
		
        for(int i = 0; i < dices.length; i++){
            dices[i].roll();
            sortedResult[dices[i].getValue()] = sortedResult[dices[i].getValue()] +1 ;               
        }
        //Observer.notifyGui(Observer.UPDATEDICES);
	}
	
	public void resetDices(boolean roll){
		for(int i = 0; i < dices.length; i++){
			dices[i].unLock();
		}
		if(roll){
			rollDices();
		}		
		Observer.notifyGui(Observer.RESETDICES);
	}

	public void lockDice(int i){
		dices[i].lock();
	}
	
	public void unlockDice(int i){
		dices[i].unLock();
	}

	public Dice getDice(int i) {
		return dices[i];
	}
	
	public Dice[] getDices() {
		return dices;
	}
	
    public int [] getDicesSortedResults() {
    return sortedResult;
	}
    
    public String getSortedResultString() {
    	sortedResultString = "";
    	for(int i = 0; i < sortedResult.length; i++){
    		if(i > 0){
    			sortedResultString += "|";
    		}
    		sortedResultString += sortedResult[i];
    	}
		return sortedResultString;
	}
	public void setSortedResultString(String sortedResultString) {
		this.sortedResultString = sortedResultString;
	}
}