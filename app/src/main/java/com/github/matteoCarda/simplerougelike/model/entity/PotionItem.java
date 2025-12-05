package com.github.matteoCarda.simplerougelike.model.entity;

/**
 * Rappresenta una pozione, un tipo di oggetto che ripristina la salute.
 */
public class PotionItem extends Item{

    private final double healingAmount;

    /**
     * Costruttore per una pozione.
     *
     * @param x La coordinata x iniziale della pozione.
     * @param y La coordinata y iniziale della pozione.
     */
    public PotionItem (int x, int y){
        super(x,y, "Pozione");
        healingAmount = 25;
    }

    /**
     * Restituisce la quantità di salute che questa pozione ripristina.
     *
     * @return L'ammontare di guarigione.
     */
    public double getHealingAmount() {
        return healingAmount;
    }
}
