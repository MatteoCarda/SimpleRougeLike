package com.example.simplerougelike.model;

import com.example.simplerougelike.model.entity.Enemy;
import com.example.simplerougelike.model.entity.Player;
import com.example.simplerougelike.model.entity.Item;
import com.example.simplerougelike.model.tile.Tile;

import java.util.List;

/**
 * Rappresenta l'intero stato di un livello di gioco in un dato momento.
 * Questo oggetto contiene la griglia statica (muri e pavimenti) e le entità dinamiche
 * come il giocatore, i nemici e gli oggetti.
 */
public class GameMap {

    // La griglia bidimensionale che definisce la struttura fisica del livello.
    private final Tile[][] grid;

    // L'istanza del giocatore, tenuta separata per un accesso rapido e diretto.
    private final Player player;

    // Liste per tutte le altre entità che popolano la mappa.
    private final List<Enemy> enemies;
    private final List<Item> items;

    // Le dimensioni della mappa, memorizzate nel costruttore per non doverle ricalcolare.
    private final int width;
    private final int height;

    /**
     * Costruttore principale della GameMap.
     * Viene tipicamente chiamato da una classe come MapGenerator, che fornisce tutti i dati
     * necessari per costruire un livello completo e popolato.
     *
     * @param grid    La griglia di Tile che forma la mappa.
     * @param player  L'oggetto Player controllato dall'utente.
     * @param enemies La lista iniziale dei nemici.
     * @param items   La lista iniziale degli oggetti.
     */
    public GameMap(Tile[][] grid, Player player, List<Enemy> enemies, List<Item> items) {
        this.grid = grid;
        this.player = player;
        this.enemies = enemies;
        this.items = items;

        // Le dimensioni vengono estratte dalla griglia una sola volta per efficienza.
        this.width = grid.length;
        this.height = (grid.length > 0) ? grid[0].length : 0;
    }

    // --- METODI PUBBLICI DI ACCESSO (GETTERS) ---
    // Questi metodi forniscono un accesso controllato e sicuro ai dati della mappa,
    // permettendo ad altre parti del codice (come il GameController o il sistema di rendering)
    // di ottenere le informazioni di cui hanno bisogno senza poter modificare lo stato interno della mappa.

    /**
     * Restituisce una specifica cella (Tile) dalla griglia, date le sue coordinate.
     * Include un controllo dei limiti per prevenire errori.
     *
     * @param x La coordinata X (colonna) della cella richiesta.
     * @param y La coordinata Y (riga) della cella richiesta.
     * @return L'oggetto Tile a quella posizione, o null se le coordinate sono fuori dai limiti della mappa.
     */
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        // Restituire null è una scelta sicura: chi chiama il metodo dovrà gestire questo caso.
        return null;
    }

    /**
     * Restituisce l'istanza del giocatore.
     * @return L'oggetto Player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Restituisce la lista di tutti i nemici attualmente presenti sulla mappa.
     * @return Una lista di oggetti Enemy.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Restituisce la lista di tutti gli oggetti raccoglibili.
     * @return Una lista di oggetti Item.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Restituisce la larghezza totale della mappa in numero di celle.
     * @return La larghezza della mappa.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Restituisce l'altezza totale della mappa in numero di celle.
     * @return L'altezza della mappa.
     */
    public int getHeight() {
        return height;
    }
}
