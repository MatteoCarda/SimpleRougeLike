package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Rappresenta un nemico nel gioco.
 * Estende la classe Character con valori di salute e attacco specifici.
 */
public class Enemy extends Character {

    /**
     * Costruttore per il nemico.
     *
     * @param x La coordinata x iniziale del nemico.
     * @param y La coordinata y iniziale del nemico.
     */
    public Enemy(int x, int y) {
        super(x, y,30,5);
    }

}
