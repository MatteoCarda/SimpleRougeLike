package com.example.simplerougelike.model.entity;

public class PotionItem extends Item{

    private final double healingAmount;

    public PotionItem (int x, int y){
        super(x,y, "Pozione");
        healingAmount = 25;
    }
    public double getHealingAmount() {
        return healingAmount;
    }
}
