package com.example.simplerougelike.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simplerougelike.R;
import com.example.simplerougelike.controller.GameController;
import com.example.simplerougelike.view.GameView;

/**
 * L'Activity principale dell'applicazione, punto di ingresso del gioco.
 * Questa classe ha due ruoli fondamentali:
 * 1. Ospitare la GameView, che si occupa di tutto il rendering grafico.
 * 2. Catturare l'input dell'utente (in questo caso, gli "swipe") e tradurlo in comandi per il GameController.
 */
public class MainActivity extends AppCompatActivity {

    private GameController gameController;
    private GameView gameView;
    private GestureDetector gestureDetector;
    private static final int MAP_WIDTH = 50;
    private static final int MAP_HEIGHT = 50;
    private static final int ENEMY_COUNT = 20;
    private static final int ITEM_COUNT = 10;


    /**
     * Metodo chiamato alla creazione dell'Activity. È qui che avviene tutta l'impostazione iniziale.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout dell'activity. Usa 'activity_main.xml' che conterrà la nostra GameView.
        setContentView(R.layout.activity_main);

        // Inizializziamo i componenti principali del gioco.
        gameController = GameController.getInstance();
        gameView = findViewById(R.id.gameView);

        // Avviamo una nuova partita con dimensioni e numero di nemici/oggetti predefiniti.
        // In futuro, questi valori potrebbero venire da una schermata di selezione del livello.
        gameController.startNewGame(MAP_WIDTH, MAP_HEIGHT, ENEMY_COUNT, ITEM_COUNT);

        // Impostiamo il sistema di rilevamento dei gesti (swipe) per l'input del giocatore.
        setupGestureDetector();
    }

    /**
     * Metodo chiamato quando l'utente tocca lo schermo.
     * Passiamo l'evento al nostro GestureDetector, che capirà se si tratta di uno swipe.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Delega la gestione del tocco al GestureDetector.
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    /**
     * Configura il GestureDetector per ascoltare gli swipe e tradurli in comandi di gioco.
     */
    private void setupGestureDetector() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100; // Minima distanza in pixel per considerare un movimento come "swipe".
            private static final int SWIPE_VELOCITY_THRESHOLD = 100; // Minima velocità del dito.

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Calcoliamo la differenza di posizione tra l'inizio e la fine del gesto.
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();

                // Determiniamo se lo swipe è più orizzontale o più verticale.
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    // È uno swipe orizzontale.
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            gameController.handlePlayerTurn(GameController.Direction.RIGHT);
                        } else {
                            gameController.handlePlayerTurn(GameController.Direction.LEFT);
                        }
                        gameView.invalidate(); // Dopo l'azione, chiediamo alla GameView di ridisegnarsi.
                        return true;
                    }
                } else {
                    // È uno swipe verticale.
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            gameController.handlePlayerTurn(GameController.Direction.DOWN);
                        } else {
                            gameController.handlePlayerTurn(GameController.Direction.UP);
                        }
                        gameView.invalidate(); // Ridisegniamo la scena.
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
