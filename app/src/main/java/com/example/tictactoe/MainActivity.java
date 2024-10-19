package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; // 0 -> Circle and 1-> Cross
    int[] gameState = {2,22,23,24,25,26,27,28,9};
    boolean isGameActive = true;
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn(View view) {
        if (isGameActive) {
            ImageView img = (ImageView) view;

            int tag = Integer.parseInt(img.getTag().toString());

            // ... (rest of the code remains the same)
            gameState[tag - 1] = activePlayer;
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.circle);
                activePlayer = 1;
            } else {
                img.setImageResource(R.drawable.cross);
                activePlayer = 0;
            }
            img.setScaleX(1.5f);
            img.setScaleY(1.5f);
            img.animate().scaleX(1).scaleY(1).setDuration(1);

            for (int[] winnerPos : winningPositions) {
                if (gameState[winnerPos[0]] == gameState[winnerPos[1]] &&
                        gameState[winnerPos[1]] == gameState[winnerPos[2]] &&
                        gameState[winnerPos[0]] != 2) {
                    // Check for non-empty cell

                    if (gameState[winnerPos[0]] == 0) {
                        Toast.makeText(MainActivity.this, "🟡 has won", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "❌ has won", Toast.LENGTH_LONG).show();
                    }
                    isGameActive = false;
                    // Reset the board (consider adding a delay or a reset button)
                  resetGame();
                    activePlayer = 0; // Reset active player
                    break; // Exit the loop after finding a winner
                }
            }

        }

    }

    private void resetGame() {
        // Reset game state
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2; // Reset to empty
            ImageView img = findViewById(getResources().getIdentifier("imageView" + (i + 1), "id", getPackageName()));
            img.setImageResource(0); // Clear the image
        }
        activePlayer = 0; // Reset active player
        isGameActive = true; // Allow new game to start
        Toast.makeText(this, "Game reset!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}