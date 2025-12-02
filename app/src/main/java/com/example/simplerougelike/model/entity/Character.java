package com.example.simplerougelike.model.entity;

public abstract class Character extends GameObject {
    protected double health;
    protected double maxHealth;
    protected double attackPower;

    public Character(int x, int y, double health, double attackPower) {
        super(x, y);
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
    }

    public double getHealth() {return this.health;}
    public void setHealth(double health) {this.health = health;}
    public double getMaxHealth() {return this.maxHealth;}
    public double getAttackPower() {return this.attackPower;}
    public boolean isAlive() {return this.getHealth() > 0;}

}
