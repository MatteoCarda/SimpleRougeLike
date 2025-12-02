package com.example.simplerougelike.model.tile;

/**
 * Rappresenta una singola "casella" o "tessera" nella mappa di gioco.
 * È una classe astratta da cui derivano tutti i tipi specifici di caselle (es. Muro, Pavimento).
 */
public abstract class Tile {
    protected int x;
    protected int y;
    /**
     * Flag che indica se la casella è stata scoperta dal giocatore.
     */
    protected boolean isVisible;

    /**
     * Costruttore per una Tile.
     *
     * @param x La coordinata x della casella.
     * @param y La coordinata y della casella.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Restituisce la coordinata x della casella.
     *
     * @return La coordinata x.
     */
    public int getX() {return x;}

    /**
     * Restituisce la coordinata y della casella.
     *
     * @return La coordinata y.
     */
    public int getY() {return y;}

    /**
     * Indica se la casella è attualmente visibile al giocatore.
     *
     * @return true se la casella è visibile, false altrimenti.
     */
    public boolean isVisible() {return isVisible;}

    /**
     * Imposta la visibilità della casella.
     *
     * @param visible il nuovo stato di visibilità.
     */
    public void setVisible(boolean visible) { this.isVisible = visible; }

    /**
     * Metodo astratto per determinare se un personaggio può camminare su questa casella.
     *
     * @return true se la casella è calpestabile, false altrimenti.
     */
    public abstract boolean isWalkable();
}
