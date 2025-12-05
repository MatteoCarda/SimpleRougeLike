package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Classe base astratta per gli oggetti raccoglibili nel gioco.
 * Fornisce un nome e una posizione.
 */
public abstract class Item extends GameObject {
    protected String name;

    /**
     * Costruttore.
     * @param x Coordinata x iniziale.
     * @param y Coordinata y iniziale.
     * @param name Nome dell'oggetto.
     */
    public Item( int x, int y, String name) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
