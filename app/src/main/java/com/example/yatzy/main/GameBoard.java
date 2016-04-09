/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.yatzy.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.example.yatzy.R;
import com.example.yatzy.db.DataBase;
import com.example.yatzy.gui.MainActivity;
import com.example.yatzy.gui.SetupActivity;
import com.example.yatzy.main.score.Score;
import com.example.yatzy.main.score.ScoreCard;

public class GameBoard {

    private static GameBoard instance = null;
    private GameFactory gameFactory;
    private GameEngine gameEngine;
    private MainActivity guiMain;
    private SetupActivity guiSetup;
    private DataBase dBase;
    private Context contextSetup = null;
    private Context contextMain = null;
    private ListView lv1;
    private ListView lv2;

    private GameBoard(){
    	this.gameFactory = new GameFactory();
        this.gameEngine = gameFactory.getGameEngine(1);
        Observer.setGameBoard(this);
    }

    public static GameBoard getInstance() {
        if(instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    public static void restart() {
        instance = null;
    }

    /*public GameBoard(MainActivity guiMain, SetupActivity guiSetup){
        this.guiMain = guiMain;
        this.guiSetup = guiSetup;
    	this.gameFactory = new GameFactory();
        this.gameEngine = gameFactory.getGameEngine(d1);
        Observer.setGameBoard(this);
    }*/


    public String initGame(){
    	return gameEngine.init();
    }
    
    
    public GameEngine getGameEngine(){
    	return gameEngine;
    }
    
    public boolean lockDice(String s){
    	return gameEngine.lockDice(Integer.parseInt(s)); 
    }
    
    public void unlockDice(String s){
    	gameEngine.getDices().unlockDice(Integer.parseInt(s));
    }
    
    public void rollDices(){
        Player player = gameEngine.getCurrentPlayer();

        if(player.isPc()){
            ((AIPlayer)player).playTurns(this);;
        }else{
            gameEngine.rollDices();;
        }

    }
        
    public int setScore(String scoreId, int click){
    	String[] id =  scoreId.split("[|]");
    	int points = gameEngine.setScore(Integer.parseInt(id[0]),Integer.parseInt(id[1]), click);
    	return points;
    }
    
    public void createPlayerProfile(String name, boolean computer){
        if(null != name && "".compareToIgnoreCase(name) != 0){
            gameEngine.getPlayers().createPlayer(name, computer, this);
            updatePlayerList();
        }

    }
    
    //////////////////Observer methods
    public void updatePlayerList(){
        lv1 = (ListView)  ((Activity)contextSetup).findViewById(R.id.listView1);
        lv2 = (ListView) ((Activity)contextSetup).findViewById(R.id.listView2);
        //lv1.clearChoices();
        //lv2.clearChoices();
    	LinkedList<Player> pI = gameEngine.getPlayers().getPlayersIn();
    	LinkedList<Player> pE = gameEngine.getPlayers().getPlayersEx();

        List<Player> arrayList1 = new ArrayList<Player>();
        List<Player> arrayList2 = new ArrayList<Player>();


    	for(int i = 0; i < pI.size(); i++){
    		arrayList1.add(pI.get(i));
    	}
    	for(int i = 0; i < pE.size(); i++){
            if(!pE.get(i).isPc()) {
                arrayList2.add(pE.get(i));

            }else{

            }
    	}

        ArrayAdapter<Player> arrayAdapter1 = new ArrayAdapter<Player>(
                contextSetup,
                R.layout.listviewtext,
                arrayList1 );
        ArrayAdapter<Player> arrayAdapter2 = new ArrayAdapter<Player>(
                contextSetup,
                R.layout.listviewtext,
                arrayList2 );
        lv1.setAdapter(arrayAdapter1);
        lv2.setAdapter(arrayAdapter2);

        lv1.setEmptyView(((Activity)contextSetup).findViewById(R.id.empty_list_item1));
        lv2.setEmptyView(((Activity)contextSetup).findViewById(R.id.empty_list_item2));


       /* if(arrayAdapter1.getCount() > 5){
            View item = arrayAdapter1.getView(0, null, lv1);
            item.measure(0, 0);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
            lv1.setLayoutParams(params);
        }
        if(arrayAdapter2.getCount() > 5){
            View item = arrayAdapter2.getView(0, null, lv2);
            item.measure(0, 0);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
            lv2.setLayoutParams(params);
        }*/

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                System.out.println("Nu ska jag flyttaPC " + ((Player)lv1.getItemAtPosition(position)).isPc());
                movePlayer(((Player)lv1.getItemAtPosition(position)));
                updatePlayerList();
            }
        });
    	lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                movePlayer(((Player)lv2.getItemAtPosition(position)));
                updatePlayerList();
            }
        });


    }

    public Context getContextSetup() {
        return contextSetup;
    }

    public void setContextSetup(Context contextSetup) {
        this.contextSetup = contextSetup;
    }

    public Context getContextMain() {
        return contextMain;
    }


    public void setContextMain(Context contextMain) {
        this.contextMain = contextMain;
    }

    public void movePCPlayer(){
        LinkedList<Player> pList = gameEngine.getPlayers().getPlayersIn();
        for(int i = 0; i < pList.size(); i++){
            if(pList.get(i).isPc()){
                gameEngine.getPlayers().movePlayer(pList.get(i));
                updatePlayerList();
            }
        }
    }

    public void movePlayer(Player player){
        if(player.isPc()) {
           ((CompoundButton) ((Activity) contextSetup).findViewById(R.id.switch1)).setChecked(false);
        }else{
            gameEngine.getPlayers().movePlayer(player);
        }
        updatePlayerList();
    }
    
    public void updateChangedPlayer(){
        int player = gameEngine.getPlayers().getCurrentPlayer();
        LinkedList<Player> pList = gameEngine.getPlayers().getPlayersIn();
        int numberOfFields = pList.size()*17+1;
        int idMin = player*17;
        int idMax = player*17+18;

        for(int i = 1; i < numberOfFields; i++) {
            if(i > idMin && i < idMax){
                ((TextView)((Activity) contextMain).findViewById(i)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView)((Activity) contextMain).findViewById(i)).setBackgroundResource(R.drawable.border_turn);

            }else{
                ((TextView)((Activity) contextMain).findViewById(i)).setTypeface(Typeface.DEFAULT);
                ((TextView)((Activity) contextMain).findViewById(i)).setBackgroundResource(R.drawable.border_notturn);
            }
        }

        Button button = ((Button)((Activity) contextMain).findViewById(R.id.BRollDice));
        int defColor = new Button(contextMain).getTextColors().getDefaultColor();

        if(gameEngine.getCurrentPlayer().isPc()){
            button.setText(contextMain.getResources().getString(R.string.PCRollDice));
            button.setTextColor(Color.RED);
        }else{
            int rolls = gameEngine.getNumberOfRolls();
            button.setText(contextMain.getResources().getString(R.string.RollDice) + " ("+rolls+")");
            button.setTextColor(defColor);
        }
    }
    
    public synchronized void updateDices(){
    	Dice[] dices = gameEngine.getDices().getDices();
    	//gui.updateDiceImages(dices);
    }

    public void createDb(Context context){
        this.dBase = new DataBase(context);
        gameEngine.setDb(dBase);
    }

    public void resetGuiDices(){
        ((MainActivity)contextMain).resetDice();
    }
    
    public void updateScores(){
    	ScoreCard scoreCard = gameEngine.getCurrentPlayer().getScoreCard();
        int player = gameEngine.getPlayers().getCurrentPlayer();
        LinkedList<Player> pList = gameEngine.getPlayers().getPlayersIn();
        int base = player*17;

        ((TextView)((Activity) contextMain).findViewById(base+7)).setText(String.valueOf(scoreCard.getScore(6).getPoints()));
        ((TextView)((Activity) contextMain).findViewById(base+17)).setText(String.valueOf(scoreCard.getScore(16).getPoints()));
    }

    public void setWinner(){
        int winner = 0;
        LinkedList<Player> pList = gameEngine.getPlayers().getPlayersIn();

        for(int i = 0; i < pList.size(); i++){
            if(pList.get(i).isWinner()) {
                winner = i;
            }
        }

        int numberOfFields = pList.size()*17+1;
        int idMin = winner*17;
        int idMax = winner*17+18;

        for(int i = 1; i < numberOfFields; i++) {
            if(i > idMin && i < idMax){
                ((TextView)((Activity) contextMain).findViewById(i)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView)((Activity) contextMain).findViewById(i)).setBackgroundResource(R.drawable.border_winner);
            }else{
                ((TextView)((Activity) contextMain).findViewById(i)).setTypeface(Typeface.DEFAULT);
                ((TextView)((Activity) contextMain).findViewById(i)).setBackgroundResource(R.drawable.border_notturn);
            }

        }

    }
    public void updatePCScores(){
        LinkedList<Player> pList = gameEngine.getPlayers().getPlayersIn();
        int pNr = 0;
        Score[] score = null;

        for(int i = 0; i < pList.size(); i++){
            if(pList.get(i).isPc()){
                pNr = i;
                score = gameEngine.getPlayers().getPlayersIn().get(i).getScoreCard().getScores();
            }
        }

        int start = 17 * pNr +1;

        for(int i = 0; i < score.length; i++){
            System.out.println("Player nr: "+pNr+" start: "+start);
            TextView tt = (TextView)((Activity) contextMain).findViewById(start);
            System.out.println("TextView: "+tt);
            if(score[i].isPlayed()){
                tt.setText(String.valueOf(score[i].getPoints()));
            }

            start++;
        }
    }

    public void showHighScores(){
        AlertDialog.Builder builder = new AlertDialog.Builder(contextMain);
        builder.setCancelable(true);
        //String title = ((Activity)contextMain).findViewById().toString();
        builder.setTitle(R.string.highScoreBox);
        String highScores = gameEngine.getdBase().getHighScores();
        builder.setMessage(highScores);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() { // define the 'Cancel' button
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
