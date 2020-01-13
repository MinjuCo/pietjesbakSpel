package com.example.pietjesbakspel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity{

    //Adapter with recyclerview
    private Adapter mAdapter, mAdapter2;
    private RecyclerView mLivePlayer1List, mLivePlayer2List;

    //Views
    private TextView unamePlayer1, unamePlayer2, titleGame, player1Live, player2Live, player1Score, player2Score;
    private Button btnSkip, btnRoll, btnAzen, btn69, btnZand, btnZeven, btnShare;
    private ImageView ivDice1, ivDice2, ivDice3;
    private LinearLayout testButtons;
    private LinearLayout dices;

    //Boolean
    private boolean startPlayer1 = true;
    private boolean firstGame = true;
    private boolean isRoundEnd = false;

    //String
    String player1ScoreText;
    String player2ScoreText;
    String player1LiveText;
    String player2LiveText;

    //int
    int firstDice;
    int secondDice;
    int thirdDice;

    int scoreValuePlayer1;
    int scoreValuePlayer2;

    int p1TotalScore;
    int p2TotalScore;

    int amountThrowP1 = 0;
    int amountThrowP2 = 0;

    int p1LiveValue = 7;
    int p2LiveValue = 7;

    //Arraylist for streepjes
    ArrayList<Integer> p1LiveList = new ArrayList<>();
    ArrayList<Integer> p2LiveList = new ArrayList<>();

    //Color
    ColorStateList oldColor;

    //Random
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mLivePlayer1List = (RecyclerView) findViewById(R.id.rv_player1_liveList);
        mLivePlayer2List = (RecyclerView) findViewById(R.id.rv_player2_liveList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLivePlayer1List.setLayoutManager(layoutManager);
        mLivePlayer2List.setLayoutManager(layoutManager2);

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
        btnShare = (Button) findViewById(R.id.btn_share);
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

        for(int i = 0; i < p1LiveValue; i++){
            p1LiveList.add(R.drawable.line);
            p2LiveList.add(R.drawable.line);
        }

        mAdapter = new Adapter(p1LiveList,p1LiveValue);
        mAdapter2 = new Adapter(p2LiveList, p2LiveValue);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mLivePlayer1List);
        new ItemTouchHelper(itemTouchHelperCallback2).attachToRecyclerView(mLivePlayer2List);
        mLivePlayer1List.setAdapter(mAdapter);
        mLivePlayer2List.setAdapter(mAdapter2);

        player1LiveText = "" + p1LiveValue;
        player2LiveText = "" + p1LiveValue;
        player1Live.setText(player1LiveText);
        player2Live.setText(player2LiveText);

        checkFirstGamePlay();

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(firstGame){
                    firstDice = getRandomDice();
                    setImageDices(firstDice);
                    btnSkip.setVisibility(View.GONE);

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

                    fullGamePlay();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startPlayer1){
                    if(scoreValuePlayer1 == 69){
                        p1TotalScore = scoreValuePlayer1 + 210;
                    }else if(firstDice == secondDice && secondDice == thirdDice && scoreValuePlayer1 != 300){
                        p1TotalScore = scoreValuePlayer1 + 260;
                    }else{
                        p1TotalScore = scoreValuePlayer1;
                    }
                }else{
                    if(scoreValuePlayer2 == 69){
                        p2TotalScore = scoreValuePlayer2 + 210;
                    }else if(firstDice == secondDice && secondDice == thirdDice && scoreValuePlayer2 != 300){
                        p2TotalScore = scoreValuePlayer2 + 260;
                    }else{
                        p2TotalScore = scoreValuePlayer2;
                    }
                }
                switchTurn();
            }
        });

        btnAzen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                firstDice = 1;
                secondDice = 1;
                thirdDice = 1;
                setImageDices(firstDice, secondDice, thirdDice);

                fullGamePlay();

            }
        });

        btn69.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                firstDice = 6;
                secondDice = 5;
                thirdDice = 4;
                setImageDices(firstDice, secondDice, thirdDice);

                fullGamePlay();

            }
        });

        btnZand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                firstDice = 4;
                secondDice = 4;
                thirdDice = 4;
                setImageDices(firstDice, secondDice, thirdDice);

                fullGamePlay();

            }
        });

        btnZeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                firstDice = 2;
                secondDice = 2;
                thirdDice = 4;
                setImageDices(firstDice, secondDice, thirdDice);

                fullGamePlay();

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

    private void fullGamePlay(){
        if(startPlayer1){
            amountThrowP1 += 1;
            scoreValuePlayer1 = calculateScore(firstDice, secondDice, thirdDice);
            player1ScoreText = scoreValuePlayer1 + "";
            player1Score.setText(player1ScoreText);
            btnSkip.setVisibility(View.VISIBLE);

            if(amountThrowP2 != 0 && amountThrowP2 == amountThrowP1){
                if(scoreValuePlayer1 == 69){
                    p1TotalScore = scoreValuePlayer1 + 210;
                }else if(firstDice == secondDice && secondDice == thirdDice && scoreValuePlayer1 != 300){
                    p1TotalScore = scoreValuePlayer1 + 260;
                }else{
                    p1TotalScore = scoreValuePlayer1;
                }

                if(isRoundEnd){
                    compareScore();
                }else{
                    switchTurn();
                }
            }else if(amountThrowP1 == 3){
                if(scoreValuePlayer1 == 69){
                    p1TotalScore = scoreValuePlayer1 + 210;
                }else if(firstDice == secondDice && secondDice == thirdDice && scoreValuePlayer1 != 300){
                    p1TotalScore = scoreValuePlayer1 + 260;
                }else{
                    p1TotalScore = scoreValuePlayer1;
                }

                if(isRoundEnd){
                    compareScore();
                }else{
                    switchTurn();
                }
            }
        }else{
            amountThrowP2 += 1;
            scoreValuePlayer2 = calculateScore(firstDice, secondDice, thirdDice);
            player2ScoreText = scoreValuePlayer2 + "";
            player2Score.setText(player2ScoreText);
            btnSkip.setVisibility(View.VISIBLE);

            if(amountThrowP1 != 0 && amountThrowP2 == amountThrowP1){
                if(scoreValuePlayer2 == 69){
                    p2TotalScore = scoreValuePlayer2 + 210;
                }else if(firstDice == secondDice && secondDice == thirdDice && scoreValuePlayer2 != 300){
                    p2TotalScore = scoreValuePlayer2 + 260;
                }else{
                    p2TotalScore = scoreValuePlayer2;
                }

                if(isRoundEnd){
                    compareScore();
                }else{
                    switchTurn();
                }
            }else if(amountThrowP2 == 3){

                if(scoreValuePlayer2 == 69){
                    p2TotalScore = scoreValuePlayer2 + 210;
                }else if(firstDice == secondDice && secondDice == thirdDice && scoreValuePlayer2 != 300){
                    p2TotalScore = scoreValuePlayer2 + 260;
                }else{
                    p2TotalScore = scoreValuePlayer2;
                }

                if(isRoundEnd){
                    compareScore();
                }else{
                    switchTurn();
                }
            }

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
            isRoundEnd = true;
        }else{
            startPlayer1 = true;
            unamePlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
            unamePlayer2.setTextColor(oldColor);
            titleGame.setText(R.string.player1_turn);
            btnSkip.setVisibility(View.GONE);
            isRoundEnd = true;
        }
    }

    private void compareScore(){
        Toast.makeText(getApplicationContext(), "scoreCompared", Toast.LENGTH_SHORT).show();

        if(p1TotalScore > p2TotalScore){
            //Check special cases
            if(p1TotalScore == 300 && p1LiveValue < 7){  //indien 3 apen en minstens 1 streep weg
                p1LiveValue = 0;
            }else if(p1TotalScore == 300 && p1LiveValue == 7){ //indien 3 apen en geen streep weg
                p2LiveValue = 0;
            }else if(p1TotalScore == 279){
                p1LiveValue -= 3;
            }else if(p1TotalScore > 260 ){ //indien zand
                p1LiveValue -= 2;

            }else if(amountThrowP1 == 1){
                p1LiveValue -= 2;
            }else if(p1TotalScore == 7){
                p1LiveValue += 1;
                p2LiveValue += 1;
            }else{
                p1LiveValue -= 1;
            }

            player1LiveText = "" + p1LiveValue;
            player1Live.setText(player1LiveText);
            player2LiveText = "" + p2LiveValue;
            player2Live.setText(player2LiveText);

        }else{
            if(p2TotalScore == 300 && p2LiveValue < 7){  //indien 3 apen en minstens 1 streep weg
                p2LiveValue = 0;
            }else if(p2TotalScore == 300 && p2LiveValue == 7){ //indien 3 apen en geen streep weg
                p1LiveValue = 0;
            }else if(p2TotalScore == 279){ //69
                p1LiveValue -= 3;
            }else if(p2TotalScore > 260 ){ //indien zand
                p2LiveValue -= 2;

            }else if(amountThrowP2 == 1){
                p2LiveValue -= 2;
            }else if(p1TotalScore == 7){
                p1LiveValue += 1;
                p2LiveValue += 1;
            }else{
                p2LiveValue -= 1;
            }

            player2LiveText = "" + p2LiveValue;
            player2Live.setText(player2LiveText);
            player1LiveText = "" + p1LiveValue;
            player1Live.setText(player1LiveText);
            player2LiveText = "" + p2LiveValue;
            player2Live.setText(player2LiveText);
        }


        if(p1LiveValue <= 0){
            setWinner(unamePlayer1.getText().toString());
        }else if(p2LiveValue <= 0){
            setWinner(unamePlayer2.getText().toString());
        }else{
            amountThrowP1 = 0;
            amountThrowP2 = 0;
            scoreValuePlayer1 = 0;
            scoreValuePlayer2 = 0;

            if(startPlayer1){
                startPlayer1 = false;
                unamePlayer1.setTextColor(oldColor);
                unamePlayer2.setTextColor(getResources().getColor(R.color.colorPrimary));
                titleGame.setText(R.string.player2_turn);
                btnSkip.setVisibility(View.GONE);
                isRoundEnd = false;
            }else{
                startPlayer1 = true;
                unamePlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
                unamePlayer2.setTextColor(oldColor);
                titleGame.setText(R.string.player1_turn);
                btnSkip.setVisibility(View.GONE);
                isRoundEnd = false;
            }
        }
    }

    private void setWinner(final String winner){

        dices.setVisibility(View.INVISIBLE);
        final String winnerText = "Winner is: " + winner;

        titleGame.setText(winnerText);

        btnShare.setVisibility(View.VISIBLE);
        btnSkip.setVisibility(View.GONE);
        testButtons.setVisibility(View.GONE);
        btnRoll.setVisibility(View.GONE);

        btnShare.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                shareWinner(winnerText);
            }
        });

    }

    private void shareWinner(String textToShare){
        String mimeType = "text/plain";
        String title = "Won the pietjesbak game";

        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }

    private void resetGame(){
        firstGame = true;
        p1LiveValue = 7;
        p2LiveValue = 7;
        startPlayer1 = true;
        p1TotalScore = 0;
        p2TotalScore = 0;
        amountThrowP1 = 0;
        amountThrowP2 = 0;
        scoreValuePlayer1 = 0;
        scoreValuePlayer2 = 0;
        isRoundEnd = false;
        titleGame.setText("Who begins?");
        ivDice1.setVisibility(View.VISIBLE);
        dices.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.GONE);

        for(int i = 0; i < p1LiveValue; i++){
            p1LiveList.add(R.drawable.line);
            p2LiveList.add(R.drawable.line);
        }

        mAdapter.notifyDataSetChanged();
        mAdapter2.notifyDataSetChanged();

        checkFirstGamePlay();

        unamePlayer1.setTextColor(oldColor);
        unamePlayer2.setTextColor(oldColor);

        player1Score.setText("");
        player2Score.setText("");
        player1LiveText = "" + p1LiveValue;
        player1Live.setText(player1LiveText);
        player2LiveText = "" + p2LiveValue;
        player2Live.setText(player2LiveText);

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
        btnShare.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemWasClicked = item.getItemId();
        if(itemWasClicked == R.id.action_reset){
            resetGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.DOWN) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(p1LiveValue < viewHolder.getAdapterPosition() + 1){
                    p1LiveList.remove(viewHolder.getAdapterPosition());
                    mAdapter.notifyDataSetChanged();
                }else{
                    mAdapter.notifyDataSetChanged();
                }
        }
    };

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback2 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(p2LiveValue < viewHolder.getAdapterPosition() + 1){
                    p2LiveList.remove(viewHolder.getAdapterPosition());
                    mAdapter2.notifyDataSetChanged();
                }else{
                    mAdapter2.notifyDataSetChanged();
                }



        }
    };

}
