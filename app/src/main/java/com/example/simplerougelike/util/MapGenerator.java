package com.example.simplerougelike.util;

import com.example.simplerougelike.model.GameMap;
import com.example.simplerougelike.model.entity.Enemy;
import com.example.simplerougelike.model.entity.Item;
import com.example.simplerougelike.model.entity.Player;
import com.example.simplerougelike.model.entity.PotionItem;
import com.example.simplerougelike.model.tile.FloorTile;
import com.example.simplerougelike.model.tile.Tile;
import com.example.simplerougelike.model.tile.WallTile;

import java.util.ArrayList;
import java.util.List;

import squidpony.squidmath.Coord;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidmath.RNG;

/**
 * Classe di utilità per la generazione procedurale della mappa di gioco.
 * Utilizza la libreria SquidLib per creare la struttura del dungeon.
 */
public class MapGenerator {
    private final RNG rng;

    /**
     * Costruttore che inizializza il generatore di numeri casuali (RNG).
     * L'RNG è fondamentale per garantire che ogni mappa sia diversa.
     */
    public MapGenerator() {
        this.rng = new RNG();
    }

    /**
     * Genera una mappa di gioco completa con stanze, corridoi, nemici e oggetti.
     *
     * @param width Larghezza della mappa da generare.
     * @param height Altezza della mappa da generare.
     * @param enemyCount Numero di nemici da posizionare casualmente.
     * @param itemCount Numero di oggetti da posizionare casualmente.
     * @return Un oggetto GameMap pronto per essere utilizzato nel gioco.
     * @throws IllegalStateException se la generazione del dungeon non produce spazi calpestabili.
     */
    public GameMap generateMap(int width, int height, int enemyCount, int itemCount) {
        // 1. USARE SQUIDLIB PER GENERARE LA STRUTTURA DEL DUNGEON
        // DungeonGenerator crea una griglia di caratteri, dove '#' rappresenta un muro e '.' un pavimento.
        DungeonGenerator dungeonGenerator = new DungeonGenerator(width, height, rng);
        char[][] generatedGrid = dungeonGenerator.generate();

        // 2. CONVERTIRE LA GRIGLIA DI CARATTERI IN OGGETTI TILE
        // Traduciamo la mappa di char in una griglia di nostri oggetti Tile (WallTile e FloorTile).
        // Teniamo anche traccia di tutte le coordinate del pavimento per il posizionamento degli oggetti.
        Tile[][] grid = new Tile[width][height];
        ArrayList<Coord> floorCoords = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (generatedGrid[x][y] == '#') {
                    grid[x][y] = new WallTile(x, y);
                } else {
                    grid[x][y] = new FloorTile(x, y);
                    floorCoords.add(Coord.get(x, y)); // Aggiungiamo le coordinate alla lista dei pavimenti
                }
            }
        }

        // 3. MESCOLARE LE POSIZIONI E POSIZIONARE IL GIOCATORE
        // Mescoliamo la lista di posizioni libere per garantire casualità.
        rng.shuffle(floorCoords);
        if (floorCoords.isEmpty()) {
            // Se non ci sono spazi calpestabili, è impossibile continuare. Lanciamo un'eccezione.
            throw new IllegalStateException("Nessuna casella calpestabile trovata sulla mappa generata.");
        }

        // La prima posizione della lista mescolata diventa il punto di spawn del giocatore.
        Coord playerCoord = floorCoords.remove(0); // Rimuoviamo la posizione per non piazzarci altro
        Player player = new Player(playerCoord.x, playerCoord.y);

        // 4. POSIZIONARE I NEMICI
        // Prendiamo le posizioni successive dalla lista per i nemici.
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < enemyCount && !floorCoords.isEmpty(); i++) {
            Coord enemyCoord = floorCoords.remove(0);
            enemies.add(new Enemy(enemyCoord.x, enemyCoord.y));
        }

        // 5. POSIZIONARE GLI OGGETTI
        // E infine, usiamo le posizioni rimanenti per gli oggetti.
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < itemCount && !floorCoords.isEmpty(); i++) {
            Coord itemCoord = floorCoords.remove(0);
            items.add(new PotionItem(itemCoord.x, itemCoord.y));
        }

        // 6. CREARE E RESTITUIRE L'OGGETTO GAMEMAP COMPLETO
        // Assembliamo tutto in un unico oggetto GameMap che rappresenta lo stato iniziale del livello.
        return new GameMap(grid, player, enemies, items);
    }
}
