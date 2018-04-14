package com.hokage.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FiveByFiveActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[5][5];
    private boolean player1Turn = true;
    private int roundCounts;
    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;
    private int player1Score;
    private int player2Score;
    private String player1Piece = "";
    private String player2Piece = "";


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_five_by_five );

        player1ScoreTextView = findViewById ( R.id.player1_score_5x5 );
        player2ScoreTextView = findViewById ( R.id.player2_score_5x5 );

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "box_5_" + i + j;
                int resID = getResources ( ).getIdentifier ( buttonID, "id", getPackageName ( ) );
                buttons[i][j] = findViewById ( resID );
                buttons[i][j].setOnClickListener ( this );
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText ( ).toString ( ).equals ( "" )) {
            if (player1Turn) {
                ((Button) v).setText ( player1Piece );
            } else {
                ((Button) v).setText ( player2Piece );
            }
        } else return;

        roundCounts++;

        if (checkWin ( )) {
            if (player1Turn) {
                player1Win ( );
            } else {
                player2Win ( );
            }
        } else if (roundCounts == 25) {
            draw ( );
        } else {
            player1Turn = !player1Turn;
        }

    }

    private void player1Win() {
        player1Score++;
        Toast.makeText ( this, "PLAYER 1 WINS", Toast.LENGTH_SHORT ).show ( );
        updateScoreText ( );
        resetBoard ( );
    }

    private void player2Win() {
        player2Score++;
        Toast.makeText ( this, "PLAYER 2 WINS", Toast.LENGTH_SHORT ).show ( );
        updateScoreText ( );
        resetBoard ( );

    }

    private void draw() {
        Toast.makeText ( this, "DRAW", Toast.LENGTH_SHORT ).show ( );
        resetBoard ( );


    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText ( "" );
            }
        }
        roundCounts = 0;
        player1Turn = true;
    }

    private void updateScoreText() {
        player1ScoreTextView.setText ( String.valueOf ( player1Score ) );
        player2ScoreTextView.setText ( String.valueOf ( player2Score ) );
    }

    public void symbolChoiceFiveByFive(View view) {
        boolean checked = ((RadioButton) view).isChecked ( );
        switch (view.getId ( )) {
            case R.id.radio_X_5x5:
                if (checked) {
                    player1Piece = "X";
                    player2Piece = "O";
                    resetBoard ( );
                    break;
                }
            case R.id.radio_O_5x5:
                if (checked) {
                    player1Piece = "O";
                    player2Piece = "X";
                    resetBoard ( );
                    break;
                }
            case R.id.radio_XO_5x5:
                if (checked) {
                    player1Turn = !player1Turn;
                    resetBoard ( );
                }
        }
    }

    private boolean checkWin() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons[i][j].getText ( ).toString ( );
            }
        }
        // checking rows for win
        for (int k = 0; k < 5; k++) {
            if (field[k][0].equals ( field[k][1] )
                    && field[k][0].equals ( field[k][2] )
                    && field[k][0].equals ( field[k][3] )
                    && !field[k][0].equals ( "" )
                    || field[k][1].equals ( field[k][2] )
                    && field[k][1].equals ( field[k][3] )
                    && field[k][1].equals ( field[k][4] )
                    && !field[k][1].equals ( "" )) {
                return true;
            }
        }
        // checking columns for win
        for (int k = 0; k < 5; k++) {
            if (field[0][k].equals ( field[1][k] )
                    && field[0][k].equals ( field[2][k] )
                    && field[0][k].equals ( field[3][k] )
                    && !field[0][k].equals ( "" )
                    || field[1][k].equals ( field[2][k] )
                    && field[1][k].equals ( field[3][k] )
                    && field[1][k].equals ( field[4][k] )
                    && !field[1][k].equals ( "" )) {
                return true;
            }
        }
        //checking diagonals for win
        return field[0][0].equals ( field[1][1] )
                && field[0][0].equals ( field[2][2] )
                && field[0][0].equals ( field[3][3] )
                && !field[0][0].equals ( "" )
                || field[1][1].equals ( field[2][2] )
                && field[1][1].equals ( field[3][3] )
                && field[1][1].equals ( field[4][4] )
                && !field[1][1].equals ( "" )
                || field[0][1].equals ( field[1][2] )
                && field[0][1].equals ( field[2][3] )
                && field[0][1].equals ( field[3][4] )
                && !field[0][1].equals ( "" )
                || field[1][0].equals ( field[2][1] )
                && field[1][0].equals ( field[3][2] )
                && field[1][0].equals ( field[4][3] )
                && !field[1][0].equals ( "" )
                || field[0][2].equals ( field[1][1] )
                && field[0][2].equals ( field[2][0] )
                && !field[0][2].equals ( "" );

    }

}
