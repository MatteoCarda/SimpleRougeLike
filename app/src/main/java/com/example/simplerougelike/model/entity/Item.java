package com.example.simplerougelike.model.entity;

/**
 * Rappresenta un oggetto generico nel gioco.
 * È una classe astratta da cui derivano tutti gli oggetti specifici.
 */
public abstract class Item extends GameObject {
    protected String name;

    /**
     * Costruttore per un oggetto.
     *
     * @param x La coordinata x iniziale dell'oggetto.
     * @param y La coordinata y iniziale dell'oggetto.
     * @param name Il nome dell'oggetto.
     */
    public Item( int x, int y, String name) {
        super(x, y);
        this.name = name;
    }

    /**
     * Restituisce il nome dell'oggetto.
     *
     * @return Il nome dell'oggetto.
     */
    public String getName() {
        return name;
    }
}
