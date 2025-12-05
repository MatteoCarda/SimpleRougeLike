package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Rappresenta un'entità nemica.
 * Estende {@link Character} con statistiche di base fisse.
 */
public class Enemy extends Character {

    /**
     * Costruttore.
     * @param x Coordinata x iniziale.
     * @param y Coordinata y iniziale.
     */
    public Enemy(int x, int y) {
        // Statistiche base per un nemico generico: 30 HP, 5 Attack Power.
        super(x, y, 30, 5);
    }
}
