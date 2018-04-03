package com.hokage.tictactoe;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ThreeByThreeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCounts;
    private TextView playerXScoreTextView;
    private TextView playerOScoreTextView;
    private int playerXScore;
    private int playerOScore;
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
        setContentView ( R.layout.activity_three_by_three );

        playerXScoreTextView = findViewById ( R.id.player_x_score );
        playerOScoreTextView = findViewById ( R.id.player_o_score );

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "box_" + i + j;
                int resID = getResources ( ).getIdentifier ( buttonID, "id", getPackageName ( ) );
                buttons[i][j] = findViewById ( resID );
                buttons[i][j].setOnClickListener ( this );
            }
        }
    }

    public void resetGame(View v) {
        resetBoard ( );
        playerOScore = 0;
        playerXScore = 0;
        playerXScoreTextView.setText ( String.valueOf ( playerXScore ) );
        playerOScoreTextView.setText ( String.valueOf ( playerOScore ) );
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText ( ).toString ( ).equals ( "" )) {
            if (player1Turn) {
                ((Button) v).setText ( player1Piece );
            } else {
                ((Button) v).setText ( player2Piece );
            }
        } else {
            return;
        }


        roundCounts++;

        if (checkWin ( )) {
            if (player1Turn) {
                playerXWin ( );
            } else {
                playerOWin ( );
            }
        } else if (roundCounts == 9) {
            draw ( );
        } else {
            player1Turn = !player1Turn;
        }

    }

    private void playerXWin() {
        playerXScore++;
        Toast.makeText ( this, "PLAYER 1 WINS", Toast.LENGTH_SHORT ).show ( );
        updateScoreText ( );
        resetBoard ( );
    }


    private void playerOWin() {
        playerOScore++;
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
        playerXScoreTextView.setText ( String.valueOf ( playerXScore ) );
        playerOScoreTextView.setText ( String.valueOf ( playerOScore ) );
    }


    private boolean checkWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText ( ).toString ( );
            }
        }
        // checking rows for win
        for (int k = 0; k < 3; k++) {
            if (field[k][0].equals ( field[k][1] )
                    && field[k][0].equals ( field[k][2] )
                    && !field[k][0].equals ( "" )) {
                return true;
            }
        }
        // checking columns for win
        for (int k = 0; k < 3; k++) {
            if (field[0][k].equals ( field[1][k] )
                    && field[0][k].equals ( field[2][k] )
                    && !field[0][k].equals ( "" )) {
                return true;
            }
        }
        //checking diagonals for win
        if (field[0][0].equals ( field[1][1] )
                && field[0][0].equals ( field[2][2] )
                && !field[0][0].equals ( "" )) {
            return true;
        }

        return field[0][2].equals ( field[1][1] )
                && field[0][2].equals ( field[2][0] )
                && !field[0][2].equals ( "" );

    }

    public void symbolChoice(View view) {
        boolean checked = ((RadioButton) view).isChecked ( );
        switch (view.getId ( )) {
            case R.id.radio_X:
                if (checked) {
                    player1Piece = "X";
                    player2Piece = "O";
                    resetBoard ( );
                    break;
                }
            case R.id.radio_O:
                if (checked) {
                    player1Piece = "O";
                    player2Piece = "X";
                    resetBoard ( );
                    break;
                }
            case R.id.radio_XO:
                if (checked) {
                    player1Turn = !player1Turn;
                    resetBoard ( );
                }
        }
    }
}
