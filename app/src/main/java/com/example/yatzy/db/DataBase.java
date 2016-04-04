package com.example.yatzy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yatzy.main.AIPlayer;
import com.example.yatzy.main.Player;
import com.example.yatzy.main.Players;
import com.example.yatzy.main.score.Score;

import java.util.LinkedList;

/**
    * Created by Patrik on 2016-03-24.
    */
    public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Yatzy";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL("CREATE TABLE IF NOT EXISTS Player (playerID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Game (gameID INTEGER PRIMARY KEY AUTOINCREMENT, endDate DEFAULT CURRENT_DATE, gameType INTEGER NOT NULL CHECK(gameType > 0 AND gameType < 4));");
        db.execSQL("CREATE TABLE IF NOT EXISTS PlayersInGame(playersInGameID INTEGER PRIMARY KEY AUTOINCREMENT, player_ID INT NOT NULL, game_ID INT NOT NULL, totalScore INT NOT NULL, FOREIGN KEY(player_ID) REFERENCES Player (playerID) ON DELETE CASCADE, FOREIGN KEY(game_ID) REFERENCES Game (gameID) ON DELETE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Turn(turnID INTEGER PRIMARY KEY AUTOINCREMENT, playersInGame_ID INT NOT NULL, turnNumber INT NOT NULL, scoreType VARCHAR(50)NOT NULL, turnScore INT NOT NULL, FOREIGN KEY(playersInGame_ID) REFERENCES playersInGame (playersInGameID) ON DELETE CASCADE);");
        db.execSQL("CREATE TABLE IF NOT EXISTS DicesInTurn(dicesInTurnID INTEGER PRIMARY KEY AUTOINCREMENT, turn_ID INTEGER NOT NULL, value INTEGER NOT NULL CHECK (value > 0 AND value < 7), FOREIGN KEY(turn_ID) REFERENCES Turn (turnID) ON DELETE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Player;");
        db.execSQL("DROP TABLE IF EXISTS Game;");
        db.execSQL("DROP TABLE IF EXISTS PlayersInGame;");
        db.execSQL("DROP TABLE IF EXISTS Turn;");
        db.execSQL("DROP TABLE IF EXISTS DicesInTurn;");
        onCreate(db);
    }

    public int savePlayer(String name) {
        int playerId = -99;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        Long n = db.insert("Player", null, contentValues);
        if (-1 == n) {

        } else {
            String select = "SELECT last_insert_rowid() AS key;";
            Cursor cursor = db.rawQuery(select, null);

            if (cursor.moveToFirst()) {
                playerId = cursor.getInt(cursor.getColumnIndex("key"));
            }
            cursor.close();
        }

        return playerId;
    }

    public LinkedList<Player> getPlayers() {
        LinkedList<Player> list = new LinkedList<Player>();

        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * From Player;";
        Cursor cursor = db.rawQuery(select, null);


        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if(name.compareToIgnoreCase("PC") == 0){
                Player player = new AIPlayer(cursor.getInt(cursor.getColumnIndex("playerID")), name);
                list.addLast(player);
            }else{
                Player player = new Player(cursor.getInt(cursor.getColumnIndex("playerID")), cursor.getString(cursor.getColumnIndex("name")));
                System.out.println("ID: " + cursor.getInt(cursor.getColumnIndex("playerID")) + "  NAME: " + cursor.getString(cursor.getColumnIndex("name")));
                list.addLast(player);
            }
        }


        return list;
    }

    public void saveGame(Players players, int gameType) {
        SQLiteDatabase db = this.getWritableDatabase();

        LinkedList<Player> playersList = players.getPlayersIn();
        int numberOfPlayers = playersList.size();
        int gameId = 0;
        int[] playerID = new int[numberOfPlayers];
        int[] playersInGameId = new int[numberOfPlayers];

        db.beginTransaction();
        try {

            //Saving Game
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("GameType", 1);
            Long n = db.insert("Game", null, contentValues1);
            if (-1 == n) {

            } else {
                String select = "SELECT last_insert_rowid() AS key;";
                Cursor cursor = db.rawQuery(select, null);

                if (cursor.moveToFirst()) {
                    gameId = cursor.getInt(cursor.getColumnIndex("key"));
                }
                cursor.close();
            }

            //Retrieving playerIDs
            for (int i = 0; i < playersList.size(); i++) {
                playerID[i] = playersList.get(i).getId();
            }

            //Saving PlayersInGame
            for (int i = 0; i < playerID.length; i++) {
                Player player = playersList.get(i);
                Score[] score = player.getScoreCard().getScores();

                ContentValues contentValues2 = new ContentValues();

                contentValues2.put("player_ID", playerID[i]);
                contentValues2.put("game_ID", gameId);
                contentValues2.put("totalScore", score[16].getPoints());

                Long n1 = db.insert("PlayersInGame", null, contentValues2);

                if (-1 == n1) {

                } else {
                    String select = "SELECT last_insert_rowid() AS key;";
                    Cursor cursor = db.rawQuery(select, null);

                    if (cursor.moveToFirst()) {
                        playersInGameId[i] = cursor.getInt(cursor.getColumnIndex("key"));
                    }
                    cursor.close();
                }
            }

            //Saving Turns
            for (int i = 0; i < playersInGameId.length; i++) {
                Player player = playersList.get(i);
                Score[] score = player.getScoreCard().getScores();

                for (int j = 0; j < score.length; j++) {
                    if (j == 6 || j == 16) {

                    } else {
                        ContentValues contentValues3 = new ContentValues();
                        contentValues3.put("playersInGame_ID", playersInGameId[i]);
                        contentValues3.put("turnNumber", score[j].getTurn());
                        contentValues3.put("scoreType", score[j].getName());
                        contentValues3.put("turnScore", score[j].getPoints());

                        Long n2 = db.insert("Turn", null, contentValues3);
                        if (-1 == n2) {

                        } else {
                            String select = "SELECT last_insert_rowid() AS key;";
                            Cursor cursor = db.rawQuery(select, null);

                            if (cursor.moveToFirst()) {
                                int turnId = cursor.getInt(cursor.getColumnIndex("key"));
                                int[] dice = score[j].getDices();
                                //Saving Dices
                                for (int k = 0; k < dice.length; k++) {
                                    ContentValues contentValues4 = new ContentValues();
                                    contentValues4.put("turn_ID", turnId);
                                    contentValues4.put("value", dice[k]);

                                    Long n3 = db.insert("DicesInTurn", null, contentValues4);
                                }
                            }
                            cursor.close();
                        }

                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("FUDGE");
        } finally {
            db.endTransaction();
        }

    }

    public String getHighScores(){

        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT P.Name AS name, PIG.totalScore AS score FROM Player AS P JOIN playersInGame AS PIG ON P.playerID = PIG.player_ID ORDER BY totalScore DESC Limit 10;";
        Cursor cursor = db.rawQuery(select, null);

        String highScoreList = "";
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String score = cursor.getString(cursor.getColumnIndex("score"));
            highScoreList = highScoreList + name + ". . . . . . . . . . ." + score +"\n";
        }
        return highScoreList;
    }
}
