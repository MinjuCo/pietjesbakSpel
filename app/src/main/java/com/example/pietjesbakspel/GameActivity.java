package com.example.pietjesbakspel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String PLAYER1_TURN = "player1_turn";
    private static final String DICE1_VALUE = "dice1_value";
    private static final String DICE2_VALUE = "dice2_value";
    private static final String DICE3_VALUE = "dice3_value";
    private static final String FIRST_PLAY = "first_play";
    private static final int PLAY_GAME_LOADER = 22;

    private TextView unamePlayer1, unamePlayer2, titleGame, player1Score, player2Score;
    private Button btnSkip, btnRoll, btnAzen, btn69, btnZand, btnZeven;
    private ImageView ivDice1, ivDice2, ivDice3;
    private LinearLayout testButtons;

    private boolean startPlayer1 = true;
    private boolean gameOver = false;

    int player1ScoreValue;
    int player2ScoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        unamePlayer1 = (TextView) findViewById(R.id.tv_username1);
        unamePlayer2 = (TextView) findViewById(R.id.tv_username2);
        titleGame = (TextView) findViewById(R.id.gameTitle);
        player1Score = (TextView) findViewById(R.id.tv_player1_score);
        player2Score = (TextView) findViewById(R.id.tv_player2_score);

        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnRoll = (Button) findViewById(R.id.btn_roll);
        btnAzen = (Button) findViewById(R.id.btn_azen);
        btn69 = (Button) findViewById(R.id.btn_soixanteneuf);
        btnZand = (Button) findViewById(R.id.btn_zand);
        btnZeven = (Button) findViewById(R.id.btn_zeven);

        ivDice1 = (ImageView) findViewById(R.id.dice1);
        ivDice2 = (ImageView) findViewById(R.id.dice2);
        ivDice3 = (ImageView) findViewById(R.id.dice3);

        testButtons = (LinearLayout) findViewById(R.id.testButtons);

        Intent intentThatStartedThis = getIntent();

        //Check if we have the username of player1 and player2
        if (intentThatStartedThis.hasExtra("pietjesbakSpel.player1") && intentThatStartedThis.hasExtra("pietjesbakSpel.player2")) {

            String username1 = intentThatStartedThis.getStringExtra("pietjesbakSpel.player1");
            String username2 = intentThatStartedThis.getStringExtra("pietjesbakSpel.player2");


            unamePlayer1.setText(username1 + ": 7");
            unamePlayer2.setText(username2 + ": 7");
        }

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Random random = new Random();
                int firstDice = random.nextInt(6) + 1;
                //setImageDices(firstDice);
                int secondDice = random.nextInt(6) + 1;
                int thirdDice = random.nextInt(6) + 1;
                setImageDices(firstDice, secondDice, thirdDice);

                getScore(firstDice, secondDice, thirdDice);

            }
        });

        LoaderManager.getInstance(this).initLoader(PLAY_GAME_LOADER, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ColorStateList oldColor = unamePlayer1.getTextColors();
        if(startPlayer1){
            unamePlayer2.setTextColor(oldColor);
            unamePlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else{
            unamePlayer1.setTextColor(oldColor);
            unamePlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void hideOnFirstPlay(){
        testButtons.setVisibility(View.GONE);
        btnSkip.setVisibility(View.GONE);
        ivDice2.setVisibility(View.GONE);
        ivDice3.setVisibility(View.GONE);
    }

    private void showGameEssentials(){
        testButtons.setVisibility(View.VISIBLE);
        btnSkip.setVisibility(View.VISIBLE);
        ivDice2.setVisibility(View.VISIBLE);
        ivDice3.setVisibility(View.VISIBLE);
    }

    private void setImageDices(int dice1){
        switch (dice1){
            case 1:
                ivDice1.setImageResource(R.drawable.dice1);
                break;
            case 2:
                ivDice1.setImageResource(R.drawable.dice2);
                break;
            case 3:
                ivDice1.setImageResource(R.drawable.dice3);
                break;
            case 4:
                ivDice1.setImageResource(R.drawable.dice4);
                break;
            case 5:
                ivDice1.setImageResource(R.drawable.dice5);
                break;
            case 6:
                ivDice1.setImageResource(R.drawable.dice6);
                break;
        }
    }

    private void setImageDices(int dice1, int dice2, int dice3){
        switch (dice1){
            case 1:
                ivDice1.setImageResource(R.drawable.dice1);
                break;
            case 2:
                ivDice1.setImageResource(R.drawable.dice2);
                break;
            case 3:
                ivDice1.setImageResource(R.drawable.dice3);
                break;
            case 4:
                ivDice1.setImageResource(R.drawable.dice4);
                break;
            case 5:
                ivDice1.setImageResource(R.drawable.dice5);
                break;
            case 6:
                ivDice1.setImageResource(R.drawable.dice6);
                break;
        }

        switch (dice2){
            case 1:
                ivDice2.setImageResource(R.drawable.dice1);
                break;
            case 2:
                ivDice2.setImageResource(R.drawable.dice2);
                break;
            case 3:
                ivDice2.setImageResource(R.drawable.dice3);
                break;
            case 4:
                ivDice2.setImageResource(R.drawable.dice4);
                break;
            case 5:
                ivDice2.setImageResource(R.drawable.dice5);
                break;
            case 6:
                ivDice2.setImageResource(R.drawable.dice6);
                break;
        }

        switch (dice3){
            case 1:
                ivDice3.setImageResource(R.drawable.dice1);
                break;
            case 2:
                ivDice3.setImageResource(R.drawable.dice2);
                break;
            case 3:
                ivDice3.setImageResource(R.drawable.dice3);
                break;
            case 4:
                ivDice3.setImageResource(R.drawable.dice4);
                break;
            case 5:
                ivDice3.setImageResource(R.drawable.dice5);
                break;
            case 6:
                ivDice3.setImageResource(R.drawable.dice6);
                break;
        }
    }

    private void getScore(int dice1, int dice2, int dice3){
        Bundle playBundle = new Bundle();
        //playBundle.putBoolean(PLAYER1_TURN, startPlayer1);

        playBundle.putInt(DICE1_VALUE, dice1);
        playBundle.putInt(DICE2_VALUE, dice2);
        playBundle.putInt(DICE3_VALUE, dice3);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        Loader<String> getScoreLoader = loaderManager.getLoader(PLAY_GAME_LOADER);
        if (getScoreLoader == null) {
            loaderManager.initLoader(PLAY_GAME_LOADER, playBundle, this);
        } else {
            loaderManager.restartLoader(PLAY_GAME_LOADER, playBundle, this);
        }
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {

        return new AsyncTaskLoader<String>(this) {
            String totalScore;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.i("Loader","Inside loader");
                if (args == null) {
                    Log.i("Loader","Stopped");
                    return;
                }

                if (totalScore != null) {
                    Log.i("LoaderResult","deliver Result");
                    deliverResult(""+totalScore);
                }else{
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public String loadInBackground() {
                int dice_value1 = args.getInt(DICE1_VALUE);
                int dice_value2 = args.getInt(DICE2_VALUE);
                int dice_value3 = args.getInt(DICE3_VALUE);


                totalScore = "" + (dice_value1 + dice_value2 + dice_value3);
                Log.i("Score","totalScore");
                return totalScore;

            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        if(startPlayer1){
            player1Score.setText(data);
            player1ScoreValue = Integer.parseInt(data);
            startPlayer1 = false;
        }else{
            player2Score.setText(data);
            player2ScoreValue = Integer.parseInt(data);
            startPlayer1 = true;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
