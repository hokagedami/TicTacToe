package com.hokage.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BoardTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_board_type );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged ( hasFocus );
        View decorView = getWindow ( ).getDecorView ( );
        if (hasFocus) {
            decorView.setSystemUiVisibility (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
        }
    }

    public void playThreeByThree(View v) {
        Intent threeByThreeIntent = new Intent ( getBaseContext ( ), ThreeByThreeActivity.class );
        startActivity ( threeByThreeIntent );
    }

    public void playFiveByFive(View v) {
        Intent fiveByFiveIntent = new Intent ( getBaseContext ( ), FiveByFiveActivity.class );
        startActivity ( fiveByFiveIntent );
    }
}
