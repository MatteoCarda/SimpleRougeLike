package com.github.matteoCarda.simplerougelike.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il giocatore.
 * Estende {@link Character} con statistiche fisse e un inventario.
 */
public class Player extends Character {

    // Inventario per gli oggetti raccolti.
    private final List<Item> inventory;

    /**
     * Costruttore.
     * @param x Coordinata x iniziale.
     * @param y Coordinata y iniziale.
     */
    public Player(int x, int y) {
        // Statistiche base per il giocatore.
        super(x, y, 100, 10);
        this.inventory = new ArrayList<>();
    }

    /**
     * Ritorna l'inventario del giocatore.
     */
    public List<Item> getInventory() {
        return inventory;
    }
}
