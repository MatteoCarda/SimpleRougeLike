package com.example.simplerougelike.model.tile;

public class FloorTile extends Tile {

    public FloorTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
