package com.example.pietjesbakspel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String ERR_PLAYER1_VISIBILITY = "err_player1_visibility";
    private static final String ERR_PLAYER2_VISIBILITY = "err_player2_visibility";

    //Fields that will store button, inputs and errormsg
    private EditText etPlayer1;
    private EditText etPlayer2;
    private Button playButton;
    private TextView errMsgPlayer1;
    private TextView errMsgPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference to button, edittext and textviews
        etPlayer1 = (EditText) findViewById(R.id.et_player1);
        etPlayer2 = (EditText) findViewById(R.id.et_player2);

        playButton = (Button) findViewById(R.id.btn_play);

        errMsgPlayer1 = (TextView) findViewById(R.id.msg_error_pl1);
        errMsgPlayer2 = (TextView) findViewById(R.id.msg_error_pl2);

        playButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                String usernamePlayer1 = etPlayer1.getText().toString();
                String usernamePlayer2 = etPlayer2.getText().toString();

                Intent startGameActivityIntent = new Intent(MainActivity.this, GameActivity.class);

                if(usernamePlayer1.trim().equals("") || usernamePlayer2.trim().equals("")){
                    showErrorMsg(usernamePlayer1,usernamePlayer2);
                }else{
                    hideErrorMsg();
                    startGameActivityIntent.putExtra("pietjesbakSpel.player1", usernamePlayer1);
                    startGameActivityIntent.putExtra("pietjesbakSpel.player2", usernamePlayer2);

                    startActivity(startGameActivityIntent);
                }
            }
        });

        if (savedInstanceState != null) {
            int errPlayer1Visible = savedInstanceState.getInt(ERR_PLAYER1_VISIBILITY);
            int errPlayer2Visible = savedInstanceState.getInt(ERR_PLAYER2_VISIBILITY);

            errMsgPlayer1.setVisibility(errPlayer1Visible);
            errMsgPlayer2.setVisibility(errPlayer2Visible);
        }
    }

    private void showErrorMsg(String usernPlayer1, String usernPlayer2){
        hideErrorMsg();

        if(usernPlayer1.trim().equals("")) errMsgPlayer1.setVisibility(View.VISIBLE);

        if(usernPlayer2.trim().equals("")) errMsgPlayer2.setVisibility(View.VISIBLE);
    }

    private void hideErrorMsg(){
        errMsgPlayer1.setVisibility(View.INVISIBLE);
        errMsgPlayer2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the visiblity of error messages
        int errPlayer1Visible = errMsgPlayer1.getVisibility();
        outState.putInt(ERR_PLAYER1_VISIBILITY,errPlayer1Visible);

        int errPlayer2Visible = errMsgPlayer2.getVisibility();
        outState.putInt(ERR_PLAYER2_VISIBILITY,errPlayer2Visible);
    }
}
