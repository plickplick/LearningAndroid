package com.example.yatzy.main;

import java.util.LinkedList;
import com.example.yatzy.db.DataBase;
import java.util.*;

public class Players {

	private LinkedList<Player> playersIn = new LinkedList<Player>();
	private LinkedList<Player> playersEx = new LinkedList<Player>();
	private int numberOfPlayers;
	private int nextPlayer;
	private int currentPlayer;
	private DataBase dBase = null;

		public Players(DataBase dBase){
			playersIn  = new LinkedList<Player>();
			playersEx = new LinkedList<Player>();
			nextPlayer = 0;
			this.dBase = dBase;
			playersEx = dBase.getPlayers();
		}
        
     public void createPlayer(String name, boolean computer, GameBoard gameBoard){
    	 if(computer){
	    	 int playerId = dBase.savePlayer(name);
	    	 if(playerId > 0){ 
	    		 Player player = new AIPlayer(playerId, name);
	    		 playersIn.addLast(player);
	    		 //Observer.notifyGui(Observer.UPDATELIST);
	    		 
	    	 }else{
	    		 movePlayerByName(name, computer, gameBoard);
	    	 }
	    	 
    	 }else if("PC".compareToIgnoreCase(name)!=0){
    		 //DataBase dBase = new DataBase();
			 System.out.println(dBase+" ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
	    	 int playerId = dBase.savePlayer(name);
	    	 if(playerId > 0){
	    		 Player player = new Player(playerId, name);
	    		 playersIn.addLast(player);
	    		 //Observer.notifyGui(Observer.UPDATELIST);
	    		 
	    	 }else{
	    		 movePlayerByName(name, computer, gameBoard);
	    	 }
    	 }
    	 numberOfPlayers = playersIn.size();
     }
     
     private void movePlayerByName(String name, boolean computer, GameBoard gameBoard){
    	 
		 for(int i = 0; i < playersEx.size(); i++){
			 if(playersEx.get(i).getName().compareToIgnoreCase(name) == 0){
				 Player player;
				 if(computer){
					 player = new AIPlayer(playersEx.get(i).getId(),playersEx.get(i).getName());
				 }else{
					 player = playersEx.get(i);
				 }				 
				 playersIn.addLast(player);
				 playersEx.remove(i);
			 }
		 }
		 numberOfPlayers = playersIn.size();
     }
     
     
     public void movePlayer(Player player){
    	 boolean found = false;
    	 for(int i = 0; i < playersIn.size(); i++){
    		 if(playersIn.get(i).getId() == player.getId()){
    			 playersIn.remove(i);
    			 playersEx.add(player);
    			 //Observer.notifyGui(Observer.UPDATELIST);
    			 numberOfPlayers = playersIn.size();
    			 found = true;
    		 }
    	 }
    	 if (!found){
	    	 for(int i = 0; i < playersEx.size(); i++){
	    		 if(playersEx.get(i).getId() == player.getId()){
	    			 playersEx.remove(i);
	    			 playersIn.add(player);
	    			 //Observer.notifyGui(Observer.UPDATELIST);
	    			 numberOfPlayers = playersIn.size();
	    		 }
	    	 }
    	 }
     }
     
	/*public void createPlayer(String name) {
		
           boolean success = false;
        
           if(null != name && "".compareToIgnoreCase(name) != 0){
	           if(playersIn.isEmpty()){
	           	   playersIn.add(new Player(name));
	        	   success = true;
	           }else{
	        	   boolean test = true;
	        	   for(int i = 0; i < playersIn.size(); i++){
	        		   if(((Player)playersIn.get(i)).getName().compareToIgnoreCase(name) == 0){
	        			   test = false;
	        		   }
	        	   }
	        	   if(test){
	           		   playersIn.addLast(new Player(name));
	        		   success = true;
	        	   }
	           }
           }
           if(success){
        	   Observer.notifyGui(Observer.UPDATELIST);
           }
           numberOfPlayers = playersIn.size();
      	}*/
        
	
	
        
		public LinkedList<Player> getPlayersIn() {
			return playersIn;
		}
        
		public LinkedList<Player> getPlayersEx() {
			return playersEx;
		}
		
        public Player getNextPlayer(){
        	Player player = null;
        	if(nextPlayer < numberOfPlayers){
        		player = playersIn.get(nextPlayer);
        		currentPlayer = nextPlayer;
        		nextPlayer++;
        	}else{
        		nextPlayer = 0;
        		player = playersIn.get(nextPlayer);
        		currentPlayer = nextPlayer;
        		nextPlayer++;
        	}
        	//Observer.notifyGui(Observer.UPDATECHANGEDPLAYER);
        	return player;
        }
        
        
		public int getCurrentPlayer() {
			return currentPlayer;
		}
		
		public Player getLast(){
			return playersIn.getLast();
		}

}