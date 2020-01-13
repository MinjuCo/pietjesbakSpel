package com.example.pietjesbakspel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity{

    //Views
    private TextView unamePlayer1, unamePlayer2, titleGame, player1Live, player2Live, player1Score, player2Score;
    private Button btnSkip, btnRoll, btnAzen, btn69, btnZand, btnZeven;
    private ImageView ivDice1, ivDice2, ivDice3;
    private LinearLayout testButtons;
    private LinearLayout dices;

    //Boolean
    private boolean startPlayer1 = true;
    private boolean firstGame = true;

    //String
    String player1ScoreText;
    String player2ScoreText;

    String p1LiveText;
    String p2LiveText;

    //int
    int firstDice;
    int secondDice;
    int thirdDice;

    int scoreValuePlayer1;
    int scoreValuePlayer2;

    int amountThrowP1 = 0;
    int amountThrowP2 = 0;

    //Arraylist for streepjes
    ArrayList<Character> p1LiveValue = new ArrayList<>();
    ArrayList<Character> p2LiveValue = new ArrayList<>();

    //Color
    ColorStateList oldColor;

    //Random
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        unamePlayer1 = (TextView) findViewById(R.id.tv_username1);
        unamePlayer2 = (TextView) findViewById(R.id.tv_username2);
        titleGame = (TextView) findViewById(R.id.gameTitle);
        player1Live = (TextView) findViewById(R.id.tv_player1_live);
        player2Live = (TextView) findViewById(R.id.tv_player2_live);
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
        dices = (LinearLayout) findViewById(R.id.dices);

        oldColor = unamePlayer1.getTextColors();

        Intent intentThatStartedThis = getIntent();

        //Check if we have the username of player1 and player2
        if (intentThatStartedThis.hasExtra("pietjesbakSpel.player1") && intentThatStartedThis.hasExtra("pietjesbakSpel.player2")) {

            String username1 = intentThatStartedThis.getStringExtra("pietjesbakSpel.player1");
            String username2 = intentThatStartedThis.getStringExtra("pietjesbakSpel.player2");


            unamePlayer1.setText(username1);
            unamePlayer2.setText(username2);
        }

        //Amout of lives
        for(int i = 0; i<7; i++){
            p1LiveValue.add('|');
            p2LiveValue.add('|');

            p1LiveText += p1LiveValue.get(i);
            p2LiveText += p2LiveValue.get(i);
        }

        player1Live.setText(p1LiveText);
        player2Live.setText(p2LiveText);

        checkFirstGamePlay();

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(firstGame){
                    firstDice = getRandomDice();
                    setImageDices(firstDice);

                    if(startPlayer1){
                        scoreValuePlayer1 = firstDice;
                        player1ScoreText = scoreValuePlayer1 + "";
                        player1Score.setText(player1ScoreText);

                        startPlayer1 = false;
                        unamePlayer1.setTextColor(oldColor);
                        unamePlayer2.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {
                        scoreValuePlayer2 = firstDice;
                        player2ScoreText = scoreValuePlayer2 + "";
                        player2Score.setText(player2ScoreText);
                        firstGame = false;

                        //if the dice value of player1 is higher than player2, player1 begins else player 2
                        if(scoreValuePlayer1 == scoreValuePlayer2){
                            startPlayer1 = true;
                            firstGame = true;
                            titleGame.setText(R.string.tie);
                        }else if(scoreValuePlayer1 > scoreValuePlayer2) {
                            startPlayer1 = true;
                            titleGame.setText(R.string.player1_turn);
                        } else {
                            startPlayer1 = false;
                            titleGame.setText(R.string.player2_turn);
                        }

                        checkFirstGamePlay();
                    }
                }else{
                    firstDice = getRandomDice();
                    secondDice = getRandomDice();
                    thirdDice = getRandomDice();
                    setImageDices(firstDice, secondDice, thirdDice);

                    if(startPlayer1){
                        amountThrowP1 += 1;
                        scoreValuePlayer1 = calculateScore(firstDice, secondDice, thirdDice);
                        player1ScoreText = scoreValuePlayer1 + "";
                        player1Score.setText(player1ScoreText);
                        btnSkip.setVisibility(View.VISIBLE);

                        if(amountThrowP2 != 0 && amountThrowP2 == amountThrowP1){
                            switchTurn();
                        }else if(amountThrowP1 == 3){
                            switchTurn();
                        }
                    }else{
                        amountThrowP2 += 1;
                        scoreValuePlayer2 = calculateScore(firstDice, secondDice, thirdDice);
                        player2ScoreText = scoreValuePlayer2 + "";
                        player2Score.setText(player2ScoreText);
                        btnSkip.setVisibility(View.VISIBLE);

                        if(amountThrowP1 != 0 && amountThrowP2 == amountThrowP1){
                            switchTurn();
                        }else if(amountThrowP2 == 3){
                            switchTurn();
                        }

                    }
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTurn();
            }
        });

    }

    private void checkFirstGamePlay(){
        if(firstGame){
            hideOnFirstPlay();
        }else{
            showGameEssentials();
        }

        if(startPlayer1){
            unamePlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
            unamePlayer2.setTextColor(oldColor);
        }else{
            unamePlayer1.setTextColor(oldColor);
            unamePlayer2.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

    }

    private int calculateScore(int dice1, int dice2, int dice3){
        int totalScore = 0;

        totalScore = getScoreDice(dice1) + getScoreDice(dice2) + getScoreDice(dice3);

        return totalScore;
    }

    private void switchTurn(){
        if(startPlayer1){
            startPlayer1 = false;
            unamePlayer1.setTextColor(oldColor);
            unamePlayer2.setTextColor(getResources().getColor(R.color.colorPrimary));
            titleGame.setText(R.string.player2_turn);
            btnSkip.setVisibility(View.GONE);
        }else{
            startPlayer1 = true;
            unamePlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
            unamePlayer2.setTextColor(oldColor);
            titleGame.setText(R.string.player1_turn);
            btnSkip.setVisibility(View.GONE);
        }
    }

    private int getScoreDice(int value){
        int score = 0;
        switch (value){
            case 1:
                score = 100;
                break;
            case 2:
                score = 2;
                break;
            case 3:
                score = 3;
                break;
            case 4:
                score = 4;
                break;
            case 5:
                score = 5;
                break;
            case 6:
                score = 60;
                break;
        }
        return score;
    }

    private void hideOnFirstPlay(){
        testButtons.setVisibility(View.GONE);
        btnSkip.setVisibility(View.GONE);
        ivDice2.setVisibility(View.GONE);
        ivDice3.setVisibility(View.GONE);
    }

    private void showGameEssentials(){
        testButtons.setVisibility(View.VISIBLE);
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

    private int getRandomDice(){
        return random.nextInt(6) + 1;
    }
}
