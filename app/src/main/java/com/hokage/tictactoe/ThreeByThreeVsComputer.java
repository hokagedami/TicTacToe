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
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class ThreeByThreeVsComputer extends AppCompatActivity implements View.OnClickListener {

    public Button[][] buttons = new Button[3][3];
    public boolean playerHumanTurn = true;
    public String humanPiece;
    public String computerPiece;
    public int roundCounts;
    private TextView humanPlayerScoreTextView;
    private TextView computerScoreTextView;
    private int humanScore;
    private int computerScore;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_three_by_three_vs_computer );

        humanPlayerScoreTextView = findViewById ( R.id.player_human_score );
        computerScoreTextView = findViewById ( R.id.player_android_score );

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
    public void onClick(View v) {
        Move move;
        int winner;
        do {
            ((Button) v).setText ( humanPiece );
            roundCounts++;
            if (roundCounts == 9) {
                break;
            }
            winner = checkWin ( );
            if (checkWin ( ) == 0) {
                move = findBestMove ( );
                buttons[move.getRow ( )][move.getCol ( )].setText ( computerPiece );
                roundCounts++;
            }
        } while (winner < 0 || roundCounts == 9);
        winner = checkWin ( );
        if (winner == 1) {
            computerWin ( );
        } else if (winner == 2) {
            humanPlayerWin ( );
        } else if (roundCounts == 9 && winner == 0) {
            draw ( );
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
        Toast.makeText ( this, "YOU WIN!", Toast.LENGTH_LONG ).show ( );
        updateScoreText ( );
        resetBoard ( );
    }

    private void computerWin() {
        computerScore++;
        Toast.makeText ( this, "COMPUTER WINS!", Toast.LENGTH_LONG ).show ( );
        updateScoreText ( );
        resetBoard ( );
    }

    private void draw() {
        Toast.makeText ( this, "DRAW", Toast.LENGTH_LONG ).show ( );
        resetBoard ( );
    }

    private void updateScoreText() {
        humanPlayerScoreTextView.setText ( String.valueOf ( humanScore ) );
        computerScoreTextView.setText ( String.valueOf ( computerScore ) );
    }

    public int checkWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText ( ).toString ( );
            }
        }
        // checking rows for win for computer
        for (int k = 0; k < 3; k++) {
            if (field[k][0].equals ( field[k][1] )
                    && field[k][0].equals ( field[k][2] )
                    && field[k][0].equals ( computerPiece )) {
                return 1;
            }
        }
        // checking columns for win for computer
        for (int k = 0; k < 3; k++) {
            if (field[0][k].equals ( field[1][k] )
                    && field[0][k].equals ( field[2][k] )
                    && field[0][k].equals ( computerPiece )) {
                return 1;
            }
        }
        //checking diagonals for win for computer
        if (field[0][0].equals ( field[1][1] )
                && field[0][0].equals ( field[2][2] )
                && field[0][0].equals ( computerPiece )
                || field[0][2].equals ( field[1][1] )
                && field[0][2].equals ( field[2][0] )
                && field[0][2].equals ( computerPiece )) {
            return 1;
        }
//**********************************************************************************

        // checking rows for win for human
        for (int k = 0; k < 3; k++) {
            if (field[k][0].equals ( field[k][1] )
                    && field[k][0].equals ( field[k][2] )
                    && field[k][0].equals ( humanPiece )) {
                return 2;
            }
        }
        // checking columns for win for human
        for (int k = 0; k < 3; k++) {
            if (field[0][k].equals ( field[1][k] )
                    && field[0][k].equals ( field[2][k] )
                    && field[0][k].equals ( humanPiece )) {
                return 2;
            }
        }
        //checking diagonals for win for human
        if (field[0][0].equals ( field[1][1] )
                && field[0][0].equals ( field[2][2] )
                && field[0][0].equals ( humanPiece )
                || field[0][2].equals ( field[1][1] )
                && field[0][2].equals ( field[2][0] )
                && field[0][2].equals ( humanPiece )) {
            return 2;
        }
        return 0;
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

        int checkWinScore = checkWin ( );
        if (checkWinScore == 1) {
            return 10 - roundCounts;
        }
        if (checkWinScore == 2) {
            return roundCounts - 10;
        }
        return 0;
    }

    public int minimax(Button[][] board, int depth, boolean isMax) {
        int score;
        score = minimaxScore ( );
        if (score != 0) {
            return score;
        }

        if (isMoveLeft ( )) {
            return 0;
        }

        if (isMax) {
            int best = MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText ( ).toString ( ).equals ( "" )) {
                        board[i][j].setText ( computerPiece );
                        best = max ( best, minimax ( board, depth + 1, false ) );
                        board[i][j].setText ( "" );
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText ( ).toString ( ).equals ( "" )) {
                        board[i][j].setText ( humanPiece );
                        best = min ( best, minimax ( board, depth + 1, true ) );
                        board[i][j].setText ( "" );
                    }
                }
            }
            return best;
        }
    }

    public Move findBestMove() {
        int bestValue = MIN_VALUE;
        Move bestMove = new Move ( );

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText ( ).toString ( ).equals ( "" )) {
                    buttons[i][j].setText ( computerPiece );
                    bestValue = max ( bestValue, minimax ( buttons, roundCounts, false ) );
                    bestMove.setRow ( i );
                    bestMove.setCol ( j );
                    buttons[i][j].setText ( "" );
                }
            }
        }
        return bestMove;
    }


}











