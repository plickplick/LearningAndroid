package com.example.yatzy.main;

public class Observer {
	
	public static final int UPDATELIST = 0;
	public static final int UPDATEDICES = 1;
	public static final int UPDATECHANGEDPLAYER = 2;
	public static final int RESETDICES = 3;
	public static final int ANIMATEDICES = 4;
	public static final int UPDATESCORES = 5;
	public static final int SETWINNER = 6;
	public static final int PCSCORE = 7;
	private static GameBoard gameBoard;
	
	private Observer(){
		
	}
	
	public static void notifyGui(int update){
		switch (update) {
			case UPDATELIST :
				gameBoard.updatePlayerList();
		        break;
		  	case UPDATEDICES:
			  
		        gameBoard.updateDices();
		        break;
		  	case UPDATECHANGEDPLAYER:
				gameBoard.updateChangedPlayer();
		        break;
		  	case RESETDICES:
		    	gameBoard.resetGuiDices();
		        break;
		  	case ANIMATEDICES:
		     
				//TODO gameBoard.animateDices();
		        break;
		  	case UPDATESCORES:
		    	gameBoard.updateScores();
		        break;
			case SETWINNER:
				gameBoard.setWinner();
				break;
			case PCSCORE:
				gameBoard.updatePCScores();
				break;
		  default:
		        
		        break;
		}
	}
	
	public static void setGameBoard(GameBoard gB){
		gameBoard = gB;
	}
}
