package com.example.pietjesbakspel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView unamePlayer1;
    private TextView unamePlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        unamePlayer1 = (TextView) findViewById(R.id.tv_username1);
        unamePlayer2 = (TextView) findViewById(R.id.tv_username2);

        Intent intentThatStartedThis = getIntent();

        //Check if we have the username of player1 and player2
        if (intentThatStartedThis.hasExtra("pietjesbakSpel.player1") && intentThatStartedThis.hasExtra("pietjesbakSpel.player2")) {

            String username1 = intentThatStartedThis.getStringExtra("pietjesbakSpel.player1");
            String username2 = intentThatStartedThis.getStringExtra("pietjesbakSpel.player2");


            unamePlayer1.setText(username1 + ": 7");
            unamePlayer2.setText(username2 + ": 7");
        }
    }
}
