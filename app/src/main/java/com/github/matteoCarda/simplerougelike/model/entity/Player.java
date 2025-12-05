package com.github.matteoCarda.simplerougelike.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il giocatore controllato dall'utente.
 * Estende Character con valori di vita e attacco specifici e aggiunge un inventario.
 */
public class Player extends Character {

    // L'inventario personale del giocatore, dove vengono conservati gli oggetti raccolti.
    private final List<Item> inventory;

    /**
     * Costruttore per il giocatore.
     * Inizializza il giocatore con le statistiche base e un inventario vuoto.
     *
     * @param x La coordinata x iniziale del giocatore sulla mappa.
     * @param y La coordinata y iniziale del giocatore sulla mappa.
     */
    public Player(int x, int y) {
        // I valori di salute (100) e attacco (10) sono hardcoded per il giocatore.
        super(x, y, 100, 10);
        // Ogni nuovo giocatore parte con una borsa vuota.
        this.inventory = new ArrayList<>();
    }

    /**
     * Restituisce l'inventario del giocatore.
     * Permette ad altri servizi (es. InventoryService) di vedere e manipolare gli oggetti posseduti.
     *
     * @return La lista di oggetti (Item) nell'inventario.
     */
    public List<Item> getInventory() {
        return inventory;
    }
}
