package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Classe base astratta per tutte le entità "viventi" del gioco (es. Player, Enemy).
 * Contiene le statistiche fondamentali come salute e potenza d'attacco.
 */
public abstract class Character extends GameObject {
    protected double health;
    protected double maxHealth;
    protected double attackPower;

    /**
     * Costruttore.
     * @param x Coordinata x iniziale.
     * @param y Coordinata y iniziale.
     * @param health Salute iniziale (e massima).
     * @param attackPower Potenza d'attacco base.
     */
    public Character(int x, int y, double health, double attackPower) {
        super(x, y);
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
    }

    // --- Getters e Setters ---

    public double getHealth() { return this.health; }
    public void setHealth(double health) { this.health = health; }

    public double getMaxHealth() { return this.maxHealth; }
    public void setMaxHealth(double maxHealth) { this.maxHealth = maxHealth; }

    public double getAttackPower() { return this.attackPower; }
    public void setAttackPower(double attackPower) { this.attackPower = attackPower; }
}
