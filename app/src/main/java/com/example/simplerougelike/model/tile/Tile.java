package com.example.simplerougelike.model.tile;

public abstract class Tile {
    protected int x;
    protected int y;
    protected boolean isVisible;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}

    public int getY() {return y;}

    public boolean isVisible() {return isVisible;}

    public abstract boolean isWalkable();
}
