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

    public void takeDamage(double damage) {
        this.health -= damage;
        if (health <= 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void attack(Character target) {
        target.takeDamage(this.attackPower);
    }

    public double getHealth() {return this.health;}
    public double getMaxHealth() {return this.maxHealth;}
    public double getAttackPower() {return this.attackPower;}
    public void heal(double amount){
        this.health += amount;
        if (this.health > this.maxHealth){
            this.health = this.maxHealth;
        }
    };

}
