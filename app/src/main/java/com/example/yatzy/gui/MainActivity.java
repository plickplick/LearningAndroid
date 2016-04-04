package com.example.yatzy.gui;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.yatzy.R;
import com.example.yatzy.main.Dice;
import com.example.yatzy.main.GameBoard;
import com.example.yatzy.main.GameEngine;
import com.example.yatzy.main.Player;
import java.lang.reflect.Field;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final int rows = 18;
    private static final int nr = 4;
    private TextView[][] tvs;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        GameBoard.getInstance().setContextMain(this);
        setSupportActionBar(toolbar);
        populateScoreCard();

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARG");
        GameBoard.getInstance().initGame();
        settingUpDice();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void populateScoreCard() {
        final LinkedList<Player> pList = GameBoard.getInstance().getGameEngine().getPlayers().getPlayersIn();
        tvs = new TextView[pList.size()][17];
        int idNr = 1;
        for (int p = 0; p < pList.size(); p++) {
            for (int r = 0; r < rows; r++) {
                String rowId = "row" + r;
                int id = getResId(rowId, R.id.class);
                TableRow row = (TableRow) findViewById(id);
                TextView tt = new TextView(this);
                tt.setFreezesText(true);

                //tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams. , TableRow.LayoutParams.WRAP_CONTENT));

                if (r == 0) {
                    tt.setBackgroundResource(R.drawable.border);
                    tt.setText(pList.get(p).getName());
                    tt.setPadding(10, 3, 10, 3);
                } else {

                    tt.setPadding(3, 3, 3, 3);
                    tt.setId(idNr);
                    idNr++;
                    tvs[p][(r - 1)] = tt;
                    tt.setGravity(Gravity.CENTER);
                    if(7 == r || 17 == r){
                        tt.setText(" 0 ");
                    }
                    tt.setClickable(true);
                    //tt.setLineSpacing(0, 0);
                    tt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView temp = ((TextView) view);
                            int id = temp.getId();
                            System.out.println("%%%%%% TextView " + temp);
                            int p = id / 17;
                            if ((id % 17) == 0) {
                                p = p - 1;
                            }
                            int score = id - (p * 17);
                            score = score -1;
                            int tempScore = GameBoard.getInstance().setScore(score + "|" + pList.get(p).getId(), 1);
                            System.out.println("%%%%%% TempScore " + tempScore);
                            if (tempScore > -99) {
                                temp.setText("" + tempScore);
                            }
                            System.out.println("CLICK %%%%%% " + id + " %%%%%% " + p + " %%%%%% " + score);
                        }
                    });
                    tt.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            TextView temp = ((TextView) view);
                            System.out.println("%%%%%% TextView " + temp);
                            int id = temp.getId();

                            int p = id / 17;
                            if ((id % 17) == 0) {
                                p = p - 1;
                            }
                            int score = id - (p * 17);
                            score = score -1;
                            int tempScore = GameBoard.getInstance().setScore(score + "|" + pList.get(p).getId(), 2);
                            System.out.println("%%%%%% TempScore " + tempScore);
                            if (tempScore > -99) {
                                temp.setText("" + tempScore);
                            }
                            System.out.println("LOOONGCLICK %%%%%% " + id + " %%%%%% " + p + " %%%%%% " + score);
                            return true;
                        }
                    });
                }
                row.addView(tt);

            }
        }
    }
                /*
                 DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                break;

                case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
                }
                }
                };

                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                 .setNegativeButton("No", dialogClickListener).show();
                 */



    private void settingUpDice(){
        ImageView iv0 = (ImageView) findViewById(R.id.image0);
        ImageView iv1 = (ImageView) findViewById(R.id.image1);
        ImageView iv2 = (ImageView) findViewById(R.id.image2);
        ImageView iv3 = (ImageView) findViewById(R.id.image3);
        ImageView iv4 = (ImageView) findViewById(R.id.image4);

        //GameBoard.getInstance().getGameEngine().getDices().rollDices();
        GameEngine gb = GameBoard.getInstance().getGameEngine();
        Dice[] dice = gb.getDices().getDices();
        int nrOfRolls = gb.getNumberOfRolls();
        for(int i = 0; i < dice.length; i++){
            System.out.println(dice[i].getValue());
        }

        Button button = ((Button)this.findViewById(R.id.BRollDice));
        int defColor = new Button(this).getTextColors().getDefaultColor();


        if(GameBoard.getInstance().getGameEngine().getCurrentPlayer().isPc()){
            button.setText(this.getResources().getString(R.string.PCRollDice));
            button.setTextColor(Color.RED);
        }else{
            button.setText(this.getResources().getString(R.string.RollDice) + " ("+nrOfRolls+")");
            button.setTextColor(defColor);
        }

        iv0.setImageResource(getResId("d"+dice[0].getValue(), R.drawable.class));
        iv1.setImageResource(getResId("d"+dice[1].getValue(), R.drawable.class));
        iv2.setImageResource(getResId("d"+dice[2].getValue(), R.drawable.class));
        iv3.setImageResource(getResId("d"+dice[3].getValue(), R.drawable.class));
        iv4.setImageResource(getResId("d"+dice[4].getValue(), R.drawable.class));

    }
    public void rollDice(View view) {

        GameBoard.getInstance().rollDices();
        settingUpDice();
        System.out.println("nisse rolls dice");

    }

    public void lockDice(View view) {
        ImageView iv = (ImageView)view;
        String name = this.getResources().getResourceEntryName(iv.getId());
        String diceNr = name.substring(name.length() - 1);

        GameBoard.getInstance().lockDice(diceNr);
        Dice d = GameBoard.getInstance().getGameEngine().getDices().getDice(Integer.parseInt(diceNr));
        if(d.getIsLocked()){
            iv.setImageAlpha(127);
            System.out.println("nisse setting alpha 1");
        }else{
            iv.setImageAlpha(255);
            System.out.println("nisse setting alpha 255");
        }
    }
    public void resetDice(){
        ImageView[] ivArr = {(ImageView) findViewById(R.id.image0),
                (ImageView) findViewById(R.id.image1),
                (ImageView) findViewById(R.id.image2),
                (ImageView) findViewById(R.id.image3),
                (ImageView) findViewById(R.id.image4)};

        Dice[] d = GameBoard.getInstance().getGameEngine().getDices().getDices();

        for(int i = 0; i < ivArr.length; i++){
            if(d[i].getIsLocked()){
                ivArr[i].setImageAlpha(127);
            }else{
                ivArr[i].setImageAlpha(255);
            }
        }
    }

    private static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_newGame) {
            GameBoard.restart();
            Intent myIntent = new Intent(this, SetupActivity.class);
            startActivity(myIntent);
        }
        if (id == R.id.action_highScore) {
            GameBoard.getInstance().showHighScores();
        }

        return super.onOptionsItemSelected(item);
    }



}
