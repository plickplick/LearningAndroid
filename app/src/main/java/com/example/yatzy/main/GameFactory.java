package com.example.yatzy.main;

public class GameFactory {

	public GameFactory() {
		
	}

	public GameEngine getGameEngine(int type) {
            
            GameEngine gameEngine = null;
            
            switch (type){
                    case 1:
                        gameEngine = new GameOne();
                        break;
                    case 2:
                        gameEngine = new GameTwo();
                        break;
                    case 3:
                        gameEngine = new GameThree();
                        break;
                    default:
            }
            
            return gameEngine;
	}

}