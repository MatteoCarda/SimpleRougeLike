package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Rappresenta un oggetto generico con una posizione nel mondo di gioco.
 * Classe base per tutti gli oggetti che possono essere posizionati sulla mappa.
 */
public abstract class GameObject {
    protected int x;
    protected int y;

    /**
     * Costruttore per un GameObject.
     *
     * @param x La coordinata x iniziale.
     * @param y La coordinata y iniziale.
     */
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Restituisce la coordinata x dell'oggetto.
     *
     * @return La coordinata x.
     */
    public int getX() {return x;}

    /**
     * Restituisce la coordinata y dell'oggetto.
     *
     * @return La coordinata y.
     */
    public int getY() {return y;}

    /**
     * Imposta la posizione dell'oggetto.
     *
     * @param x La nuova coordinata x.
     * @param y La nuova coordinata y.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
