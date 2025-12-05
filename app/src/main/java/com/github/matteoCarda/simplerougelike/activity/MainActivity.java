package com.github.matteoCarda.simplerougelike.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.matteoCarda.simplerougelike.R;
import com.github.matteoCarda.simplerougelike.controller.GameController;
import com.github.matteoCarda.simplerougelike.view.GameView;

/**
 * Activity principale dell'app.
 * Ospita la GameView e cattura l'input dell'utente (swipe) per passarlo al GameController.
 */
public class MainActivity extends AppCompatActivity {

    private GameController gameController;
    private GameView gameView;
    private GestureDetector gestureDetector;
    private ProgressBar healthBar;
    private ProgressBar xpBar;
    private static final int MAP_WIDTH = 50;
    private static final int MAP_HEIGHT = 50;
    private static final int ENEMY_COUNT = 20;
    private static final int ITEM_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializzazione dei componenti principali
        gameController = GameController.getInstance();
        gameView = findViewById(R.id.gameView);

        // Trova le barre di stato nel layout e le passa al controller
        healthBar = findViewById(R.id.healthBar);
        xpBar = findViewById(R.id.xpBar);
        gameController.setHealthBar(healthBar);
        gameController.setXpBar(xpBar);

        if (savedInstanceState == null || gameController.getGameState() != GameController.GameState.PLAYING) {
            // Inizia una NUOVA partita SOLO se:
            // 1. L'app è stata appena avviata.
            // 2. O se la partita precedente era finita.
            gameController.startNewGame(MAP_WIDTH, MAP_HEIGHT, ENEMY_COUNT, ITEM_COUNT);
        }

        // Imposta il listener per gli swipe.
        setupGestureDetector();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Passa l'evento al gestore di swipe.
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    /**
     * Configura il GestureDetector per tradurre gli swipe in comandi di gioco.
     */
    private void setupGestureDetector() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100; // Minima distanza (pixel)
            private static final int SWIPE_VELOCITY_THRESHOLD = 100; // Minima velocità

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    // Swipe orizzontale
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            gameController.handlePlayerTurn(GameController.Direction.RIGHT);
                        } else {
                            gameController.handlePlayerTurn(GameController.Direction.LEFT);
                        }
                        gameView.invalidate(); // Forza il ridisegno della vista di gioco
                        return true;
                    }
                } else {
                    // Swipe verticale
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            gameController.handlePlayerTurn(GameController.Direction.DOWN);
                        } else {
                            gameController.handlePlayerTurn(GameController.Direction.UP);
                        }
                        gameView.invalidate(); // Forza il ridisegno
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
