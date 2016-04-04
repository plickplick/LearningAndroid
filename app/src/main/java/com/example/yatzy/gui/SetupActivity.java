package com.example.yatzy.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Button setPCPlayer = (Button) findViewById(R.id.AddPCPlayerB);
        setPCPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GameBoard.getInstance().createPlayerProfile("PC", true);
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
            //if(null == GameBoard.getInstance().getGameEngine().getdBase()){

            //}
            GameBoard.getInstance().createDb(this);
            GameBoard.getInstance().setContextSetup(this);
            GameBoard.getInstance().updatePlayerList();
    }




}

/*


@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button next = (Button) findViewById(R.id.Button01);
    next.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent myIntent = new Intent(view.getContext(), Activity2.class);
            startActivityForResult(myIntent, 0);
        }

    });
}
}



@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button next = (Button) findViewById(R.id.Button01);
    next.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent myIntent = new Intent(view.getContext(), Activity2.class);
            startActivityForResult(myIntent, 0);
        }

    });
}
}

main.xml will contain:
<?xml version="d1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:background="#ffffff"  >

    <TextView
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:textColor="#000000"
    android:text="This is Activity d1" />

       <Button android:text="Next"
        android:id="@+id/Button01"
        android:layout_width="250px"
            android:textSize="18px"
        android:layout_height="55px">
    </Button>

</LinearLayout>



main2.xml will contain:
<?xml version="d1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:background="#ffffff"  >

    <TextView
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:textColor="#000000"
    android:text="This is Activity d2" />

       <Button android:text="Previous"
        android:id="@+id/Button02"
        android:layout_width="250px"
            android:textSize="18px"
        android:layout_height="55px">
    </Button>

</LinearLayout>



So each Activity will have a text that says “This is Activity x” and a button to switch the Activity.

d5. Add the second Activity to the main manifest file. Open AndroidManifest.xml and add:
        <activity android:name=".Activity2"></activity>

The final result will look similar to this:
<?xml version="d1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
      package="com.warriorpoint.taxman2"
      android:versionCode="d1"
      android:versionName="d1.0">
    <application android:icon="@drawable/icon" android:label="@string/app_name">
        <activity android:name=".Activity1"
                  android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".Activity2"></activity>
    </application>
    <uses-sdk android:minSdkVersion="d3" />
</manifest>


* */

