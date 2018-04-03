package com.hokage.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ThreeByThreeVsComputer extends AppCompatActivity implements View.OnClickListener {

    public Button[][] buttons = new Button[3][3];
    public boolean playerHumanTurn = true;
    public String humanPiece;
    public String computerPiece;
    private int roundCounts;
    private TextView humanPlayerScoreTextView;
    private TextView computerScoreTextView;
    private int humanScore;
    private int computerScore;

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
    public void onClick(View v) {
        if (((Button) v).getText ( ).toString ( ).equals ( "" )) {
            if (playerHumanTurn) {
                ((Button) v).setText ( humanPiece );
            } else {
                computerPlay ( );
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
        } else if (roundCounts == 9) {
            draw ( );
        } else {
            playerHumanTurn = !playerHumanTurn;
        }
    }


    public void computerPlay() {
        Move bestMove = findBestMove ( buttons );
        buttons[bestMove.row][bestMove.col].setText ( computerPiece );

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
                    humanPiece = "X";
                    computerPiece = "O";
                    resetBoard ( );
                    break;
                }
            case R.id.radio_O:
                if (checked) {
                    humanPiece = "0";
                    computerPiece = "X";
                    resetBoard ( );
                    break;
                }

        }
    }

    public int evaluateBoard(Button[][] board) {

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = board[i][j].getText ( ).toString ( );
            }
        }
        // checking rows for win
        for (int k = 0; k < 3; k++) {
            if (field[k][0].equals ( field[k][1] )
                    && field[k][0].equals ( field[k][2] )
                    && !field[k][0].equals ( "" )) {
                if (field[k][0].equals ( computerPiece )) {
                    return 10;
                } else if (field[k][0].equals ( humanPiece )) {
                    return -10;
                }
            }
        }
        // checking columns for win
        for (int k = 0; k < 3; k++) {
            if (field[0][k].equals ( field[1][k] )
                    && field[0][k].equals ( field[2][k] )
                    && !field[0][k].equals ( "" )) {
                if (field[0][k].equals ( computerPiece )) {
                    return 10;
                } else if (field[0][k].equals ( humanPiece )) {
                    return -10;
                }

            }
        }

        //checking diagonals for win
        if (field[0][0].equals ( field[1][1] )
                && field[0][0].equals ( field[2][2] )
                && !field[0][0].equals ( "" )) {
            if (field[0][0].equals ( computerPiece )) {
                return 10;
            } else if (field[0][0].equals ( humanPiece )) {
                return -10;
            }
        }

        if (field[0][2].equals ( field[1][1] )
                && field[0][2].equals ( field[2][0] )
                && !field[0][2].equals ( "" )) {
            if (field[0][2].equals ( computerPiece )) {
                return 10;
            } else if (field[0][2].equals ( humanPiece )) {
                return -10;
            }
        }

        return 0;
    }

    private boolean isMoveLeft(Button[][] board) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText ( ).toString ( ).equals ( "" )) {
                    return true;
                }
            }
        }
        return false;
    }

    private int minimax(Button[][] board, int depth, boolean computerTurn) {
        String[][] field = new String[3][3];
        int score = evaluateBoard ( board );
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }

        if (!isMoveLeft ( board )) {
            return 0;
        }
        if (computerTurn) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText ( ).toString ( ).equals ( "" )) {
                        buttons[i][j].setText ( computerPiece );
                        best = Math.max ( best, minimax ( board, depth + 1, playerHumanTurn ) );
                        buttons[i][j].setText ( "" );

                    }
                }

            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    field[i][j] = buttons[i][j].getText ( ).toString ( );
                    if (field[i][j].equals ( "" )) {
                        if (humanPiece.equals ( "X" )) {
                            buttons[i][j].setText ( "O" );
                        }

                        if (humanPiece.equals ( "O" )) {
                            buttons[i][j].setText ( "X" );
                        }

                        best = Math.max ( best, minimax ( board, depth + 1, playerHumanTurn ) );
                        buttons[i][j].setText ( "" );

                    }
                }

            }
            return best;
        }
    }

    private Move findBestMove(Button[][] board) {
        int bestValue = -1000;
        Move bestMove = new Move ( );
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText ( ).toString ( ).equals ( "" )) {
                    board[i][j].setText ( "O" );
                    int moveValue = minimax ( board, 0, false );
                    board[i][j].setText ( "" );

                    if (moveValue > bestValue) {
                        bestMove.row = i;
                        bestMove.col = j;
                    }
                }
            }
        }
        return bestMove;
    }

    public class Move {
        int row;
        int col;
    }


}
