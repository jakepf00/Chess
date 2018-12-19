package com.jakepf00.chess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

// ./Android/Sdk/platform-tools/adb connect 100.115.92.2

public class MainActivity extends AppCompatActivity {

    Button newGameButton;
    Switch playAISwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.new_game_button);
        playAISwitch = findViewById(R.id.ai_switch);
    }

    void startNewGame(View v) {
        Toast toast = Toast.makeText(this, "hey", Toast.LENGTH_LONG);
        toast.show();
    }
}
