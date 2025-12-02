package com.example.simplerougelike.model.entity;

public abstract class GameObject {
    protected int x;
    protected int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
