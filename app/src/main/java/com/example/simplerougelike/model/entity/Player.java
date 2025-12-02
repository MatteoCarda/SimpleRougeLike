package com.example.simplerougelike.model.entity;

/**
 * Rappresenta il giocatore nel gioco.
 * Estende la classe Character con valori di salute e attacco specifici.
 */
public class Player extends Character {

    /**
     * Costruttore per il giocatore.
     *
     * @param x La coordinata x iniziale del giocatore.
     * @param y La coordinata y iniziale del giocatore.
     */
    public Player(int x, int y){
        super(x, y, 100, 10);
    }
}
