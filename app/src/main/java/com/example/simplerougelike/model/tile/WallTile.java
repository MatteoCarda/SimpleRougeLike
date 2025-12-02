package com.example.simplerougelike.model.tile;

public class WallTile extends Tile {
    public WallTile(int x, int y){
        super(x,y);
    }
    @Override
    public boolean isWalkable(){
        return false;
    }

}
