package com.jakepf00.chess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

// ./Android/Sdk/platform-tools/adb connect 100.115.92.2

public class MainActivity extends AppCompatActivity {

    Button newGameButton;
    ImageButton hintButton;
    ImageButton undoMoveButton;
    ImageButton redoMoveButton;
    Switch playAISwitch;
    GameView chessGame;
    boolean playAI = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.new_game_button);
        hintButton = findViewById(R.id.hint_button);
        undoMoveButton = findViewById(R.id.undo_move_button);
        redoMoveButton = findViewById(R.id.redo_move_button);
        playAISwitch = findViewById(R.id.ai_switch);
        chessGame = findViewById(R.id.gameView);

        playAISwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playAI = isChecked;
            }
        });
    }

    public void startNewGame(View v) {
        chessGame.newGame(playAI);
    }
    public void giveHint(View v) {
        chessGame.giveHint();
    }
    public void undoMove(View v) {
        chessGame.undoMove();
    }
    public void redoMove(View v) {
        chessGame.redoMove();
    }
}
