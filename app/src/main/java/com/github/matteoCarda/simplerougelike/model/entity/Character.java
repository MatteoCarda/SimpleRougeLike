package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Rappresenta un personaggio generico nel gioco.
 * Mantiene le informazioni di base come salute e potenza d'attacco.
 * È una classe astratta da cui derivano Player e Enemy.
 */
public abstract class Character extends GameObject {
    protected double health;
    protected double maxHealth;
    protected double attackPower;

    /**
     * Costruttore per un personaggio.
     *
     * @param x La coordinata x iniziale del personaggio.
     * @param y La coordinata y iniziale del personaggio.
     * @param health La salute iniziale del personaggio.
     * @param attackPower La potenza d'attacco iniziale del personaggio.
     */
    public Character(int x, int y, double health, double attackPower) {
        super(x, y);
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
    }

    /**
     * Restituisce la salute attuale del personaggio.
     *
     * @return La salute attuale.
     */
    public double getHealth() {return this.health;}

    /**
     * Imposta la salute attuale del personaggio.
     *
     * @param health Il nuovo valore della salute.
     */
    public void setHealth(double health) {this.health = health;}

    /**
     * Restituisce la salute massima del personaggio.
     *
     * @return La salute massima.
     */
    public double getMaxHealth() {return this.maxHealth;}

    /**
     * Imposta la salute massima del personaggio.
     *
     * @param maxHealth Il nuovo valore della salute massima.
     */
    public void setMaxHealth(double maxHealth) {this.maxHealth = maxHealth;}

    /**
     * Restituisce la potenza d'attacco del personaggio.
     *
     * @return La potenza d'attacco.
     */
    public double getAttackPower() {return this.attackPower;}

    /**
     * Imposta la potenza d'attacco del personaggio.
     *
     * @param attackPower Il nuovo valore della potenza d'attacco.
     */
    public void setAttackPower(double attackPower) {this.attackPower = attackPower;}


}
