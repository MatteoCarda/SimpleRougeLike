package com.example.simplerougelike.model.entity;

public abstract class Item extends GameObject {
    protected String name;

    public Item( int x, int y, String name) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onPickup(Player player);

}
