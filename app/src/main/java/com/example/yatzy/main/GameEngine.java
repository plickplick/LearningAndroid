package com.example.yatzy.main;

import java.util.LinkedList;
import com.example.yatzy.db.DataBase;


public class GameEngine {
	
    private Dices dices;
	private Players players;
    private Player currentPlayer;
    private int numberOfRolls;

	public void setRun(boolean run) {
		this.run = run;
	}

	private boolean run = true;

	public DataBase getdBase() {
		return dBase;
	}

	private DataBase dBase;
    private int gameType = 1;
	
    public GameEngine() {
	}

	public void setDb(DataBase dBase){
		this.dBase = dBase;
		this.dices = new Dices();
		this.players = new Players(dBase);
	}

    public String init(){
		String result = "success";
		if(run) {

			try{
				this.currentPlayer = players.getNextPlayer();
				this.currentPlayer.setMyTurn(true);
				numberOfRolls = 0;

				dices.resetDices(true);

			}catch(Exception e){
				result = "fail";
			}
			run = false;
		}
		Observer.notifyGui(Observer.UPDATECHANGEDPLAYER);
		Observer.notifyGui(Observer.RESETDICES);
    	return result;
    }
	
	public void rollDices() {
		System.out.println("null player " + currentPlayer);
		System.out.println("null player " + currentPlayer.getScoreCard());
		//System.out.println("null player " + currentPlayer);
		if(numberOfRolls < 3 && currentPlayer.getScoreCard().getNumberOfScoreSet() < 15){
			//Observer.notifyGui(Observer.ANIMATEDICES);
			dices.rollDices();
            numberOfRolls++;
		}
    }

	public Players getPlayers() {
		return players;
	}

	public Dices getDices() {
		return dices;
	}

	public void setDices(Dices dices) {
		this.dices = dices;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setPlayers(Players players) {
		this.players = players;
	}

	public boolean lockDice(int n) {
		
	        boolean locked = false;
	        /*if (this.numberOfRolls > 0 && !this.currentPlayer.isPc()) {
	            if (this.dices.getDice(n).getIsLocked()) {
	                this.dices.getDice(n).unLock();
	                locked = false;
	            } else {
	                this.dices.getDice(n).lock();
	                locked = true;
	            }
	        } else {
	            this.dices.getDice(n).lock();
	            locked = true;
	        }*/
	        if (this.dices.getDice(n).getIsLocked() && this.numberOfRolls > 0 && !this.currentPlayer.isPc()) {
                this.dices.getDice(n).unLock();
                locked = false;
            } else if(this.numberOfRolls > 0){
                this.dices.getDice(n).lock();
                locked = true;
            }
	        return locked;
	   
				
	}
    
    /*public Score[] getAvailableScores(){
        int[] dr = dices.getDicesSortedResults();
        players.getPlayerProfile(currentPlayer).getScorecard().getAvailableScores(dr);
        return  players.getPlayerProfile(currentPlayer).getScorecard().getAvailableScores(dr);
    }*/

	
	public void changeCurrentPlayer(){
		this.currentPlayer.setMyTurn(false);
		this.currentPlayer = players.getNextPlayer();
		this.currentPlayer.setMyTurn(true);
		dices.resetDices(false);
    	numberOfRolls = 0;
    	Observer.notifyGui(Observer.UPDATECHANGEDPLAYER);
    	if(players.getLast().getScoreCard().getNumberOfScoreSet() > 14){
			dBase.saveGame(players, gameType);
			setWinner();
		}
	}
	
	private void setWinner(){
		LinkedList<Player> playersIn = players.getPlayersIn();
		int higestScore = 0;
		for(int i = 0; i < playersIn.size(); i++){
			if(playersIn.get(i).getScoreCard().getScore(16).getPoints() > higestScore){
				higestScore = playersIn.get(i).getScoreCard().getScore(16).getPoints();
			}
		}
		
		for(int i = 0; i < playersIn.size(); i++){
			if(playersIn.get(i).getScoreCard().getScore(16).getPoints() == higestScore){
				playersIn.get(i).setWinner(true);
			}
		}

		Observer.notifyGui(Observer.SETWINNER);
	}
	
	/*public int setScore(int playerId, int s, int click) {
		int points = -99;
		if(playerId == currentPlayer.getId() && numberOfRolls > 0){
			points = currentPlayer.getScoreCard().setScore(dices.getDices(), dices.getDicesSortedResults(),s, click);
			if(points > -1){
				changeCurrentPlayer();
			}

		}
		return points;
	}*/

	public int setScore(int sId, int pId, int click) {
		int points = -99;
		System.out.println("-----%%%%%% sId|pId " + sId+"|"+pId+ " currPId " + currentPlayer.getId());
		if(pId == currentPlayer.getId() && numberOfRolls > 0){

			points = currentPlayer.getScoreCard().setScore(dices.getDices(), dices.getDicesSortedResults(),sId, click);

			if(-99 != points){
				changeCurrentPlayer();
			}
		}
		return points;
	}

	public int getNumberOfRolls() {
		return numberOfRolls;
	}

	public void setNumberOfRolls(int numberOfRolls) {
		this.numberOfRolls = numberOfRolls;
	}
           
}
