package com.github.matteoCarda.simplerougelike.model.tile;

/**
 * Rappresenta una casella di tipo "Pavimento" sulla mappa.
 * È sempre calpestabile.
 */
public class FloorTile extends Tile {

    /**
     * Costruttore per una casella Pavimento.
     *
     * @param x La coordinata x della casella.
     * @param y La coordinata y della casella.
     */
    public FloorTile(int x, int y) {
        super(x, y);
    }

    /**
     * Determina se un personaggio può camminare su questa casella.
     *
     * @return true, perché il pavimento è sempre calpestabile.
     */
    @Override
    public boolean isWalkable() {
        return true;
    }
}
