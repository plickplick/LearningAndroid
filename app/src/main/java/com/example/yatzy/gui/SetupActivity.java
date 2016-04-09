package com.example.yatzy.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yatzy.R;
import com.example.yatzy.main.GameBoard;
import com.example.yatzy.main.Player;

import java.util.LinkedList;


/**
 * Created by Patrik on 2016-03-23.
 */
public class SetupActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Button setPlayer = (Button) findViewById(R.id.AddPlayerB);
        setPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = ((TextView)findViewById(R.id.AddPlayer)).getText().toString();
                GameBoard.getInstance().createPlayerProfile(name, false);
                ((TextView)findViewById(R.id.AddPlayer)).setText("");
            }

        });


        Switch s = (Switch) findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked){
                if(isChecked) {
                    GameBoard.getInstance().createPlayerProfile("PC", true);
                } else {
                    GameBoard.getInstance().movePCPlayer();
                }

            }

        });


        Button startGame = (Button) findViewById(R.id.StartGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LinkedList<Player> pL =GameBoard.getInstance().getGameEngine().getPlayers().getPlayersIn();
                if(pL != null && pL.size() != 0) {
                    GameBoard.getInstance().getGameEngine().setRun(true);
                    Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }

        });
        EditText editText = (EditText) findViewById(R.id.AddPlayer);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String name = ((TextView)findViewById(R.id.AddPlayer)).getText().toString();
                    GameBoard.getInstance().createPlayerProfile(name, false);
                    ((TextView)findViewById(R.id.AddPlayer)).setText("");
                }
                return false;
            }
        });


            GameBoard.getInstance().createDb(this);
            GameBoard.getInstance().setContextSetup(this);
            GameBoard.getInstance().updatePlayerList();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}



