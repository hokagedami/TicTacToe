package com.hokage.tictactoe;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class ThreeByThreeVsComputer extends AppCompatActivity implements View.OnClickListener {

    public Button[][] buttons;
    public boolean playerHumanTurn = true;
    public String humanPiece;
    public String computerPiece;
    public int roundCounts;
    private TextView humanPlayerScoreTextView;
    private TextView computerScoreTextView;
    private int humanScore;
    private int computerScore;

    {
        buttons = new Button[3][3];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_three_by_three_vs_computer );

        humanPlayerScoreTextView = findViewById ( R.id.player_x_score );
        computerScoreTextView = findViewById ( R.id.player_o_score );

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "box_" + i + j;
                int resID = getResources ( ).getIdentifier ( buttonID, "id", getPackageName ( ) );
                buttons[i][j] = findViewById ( resID );
                buttons[i][j].setOnClickListener ( this );
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged ( hasFocus );
        View decorView = getWindow ( ).getDecorView ( );
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= KITKAT) {
                decorView.setSystemUiVisibility (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (((Button) v).getText ( ).toString ( ).equals ( "" )) {
            if (playerHumanTurn) {
                ((Button) v).setText ( humanPiece );
            } else {
                Move compMove = findBestMove ( );
                int i = compMove.getRow ( );
                int j = compMove.getCol ( );
                buttons[i][j].setText ( computerPiece );

            }
        } else {
            return;
        }
        roundCounts++;

        if (checkWin ( )) {
            if (playerHumanTurn) {
                humanPlayerWin ( );
            } else {
                computerWin ( );
            }
        } else if (roundCounts == 9 && isMoveLeft ( )) {
            draw ( );
        } else {
            playerHumanTurn = false;
        }

    }


    public void symbolChoice(View view) {
        boolean checked = ((RadioButton) view).isChecked ( );
        switch (view.getId ( )) {
            case R.id.radio_X:
                if (checked) {
                    humanPiece = "X";
                    computerPiece = "O";
                    resetBoard ( );
                    break;
                }
            case R.id.radio_O:
                if (checked) {
                    humanPiece = "O";
                    computerPiece = "X";
                    resetBoard ( );
                    break;
                }

        }
    }



    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText ( "" );
            }
        }
        roundCounts = 0;
        playerHumanTurn = !playerHumanTurn;
    }

    public void resetGame(View v) {
        resetBoard ( );
        humanScore = 0;
        computerScore = 0;
        humanPlayerScoreTextView.setText ( String.valueOf ( humanScore ) );
        computerScoreTextView.setText ( String.valueOf ( computerScore ) );
    }


    private void humanPlayerWin() {
        humanScore++;
        Toast.makeText ( this, "YOU WIN!", Toast.LENGTH_SHORT ).show ( );
        updateScoreText ( );
        resetBoard ( );
    }

    private void computerWin() {
        computerScore++;
        Toast.makeText ( this, "COMPUTER WINS!\nDO BETTER NEXT GAME.", Toast.LENGTH_SHORT ).show ( );
        updateScoreText ( );
        resetBoard ( );

    }

    private void draw() {
        Toast.makeText ( this, "DRAW", Toast.LENGTH_SHORT ).show ( );
        resetBoard ( );


    }

    private void updateScoreText() {
        humanPlayerScoreTextView.setText ( String.valueOf ( humanScore ) );
        computerScoreTextView.setText ( String.valueOf ( computerScore ) );
    }


    public boolean checkWin() {
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
        return field[0][0].equals ( field[1][1] ) && field[0][0].equals ( field[2][2] )
                && !field[0][0].equals ( "" ) || field[0][2].equals ( field[1][1] )
                && field[0][2].equals ( field[2][0] ) && !field[0][2].equals ( "" );

    }

    public boolean isMoveLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText ( ).toString ( ).equals ( "" )) {
                    return true;
                }
            }
        }
        return false;
    }

    public int minimaxScore() {

        /* depth =   9 - roundCounts; */
        if (checkWin ( )) {
            if (playerHumanTurn) {
                //  score = depth - 10;
                return -10;
            } else {
                /* score = 10 - depth; */
                return 10;
            }
        } else {
            return 0;
        }
    }

    public int minimax(Button[][] buttons, int depth, boolean isMax) {

        int score = minimaxScore ( );

        if (score == 10 || score == -10) {
            return score;
        }

        if (isMoveLeft ( )) {
            return 0;
        }

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText ( ).toString ( ).equals ( "" )) {
                        buttons[i][j].setText ( computerPiece );
                        best = max ( minimax ( buttons, depth + 1, false ), best );
                        buttons[i][j].setText ( "" );
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText ( ).toString ( ).equals ( "" )) {
                        buttons[i][j].setText ( humanPiece );
                        best = min ( minimax ( buttons, depth + 1, true ), best );
                        buttons[i][j].setText ( "" );
                    }
                }
            }
            return best;
        }

    }

    public Move findBestMove() {
        int bestVal = -1000;
        Move bestMove = new Move ( ) {
            @Override
            public void setRow(int row) {
                super.setRow ( -1 );
            }

            @Override
            public void setCol(int col) {
                super.setCol ( -1 );
            }
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (buttons[i][j].getText ( ).toString ( ).equals ( "" )) {
                    buttons[i][j].setText ( computerPiece );
                    int moveVal = minimax ( buttons, 0, false );
                    buttons[i][j].setText ( "" );

                    if (moveVal > bestVal) {
                        bestMove.setRow ( i );
                        bestMove.setCol ( j );
                    }
                }
            }
        }

        return bestMove;
    }


}









