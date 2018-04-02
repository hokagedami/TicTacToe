package com.hokage.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_start_screen );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void options(View v){
        Intent optionIntent = new Intent(getBaseContext(),   OptionsActivity.class);
        startActivity(optionIntent);
    }
    public void onePlayer(View v) {
        Intent onePlayerIntent = new Intent ( getBaseContext (), OnePlayerActivity.class );
        startActivity ( onePlayerIntent );
    }
    public void twoPlayer(View v) {
        Intent twoPlayerIntent = new Intent ( getBaseContext (), TwoPlayersActivity.class );
        startActivity ( twoPlayerIntent );
    }



}
