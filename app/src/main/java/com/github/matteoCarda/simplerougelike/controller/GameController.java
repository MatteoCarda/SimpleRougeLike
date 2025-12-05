package com.github.matteoCarda.simplerougelike.controller;

import com.github.matteoCarda.simplerougelike.model.GameMap;
import com.github.matteoCarda.simplerougelike.model.entity.Enemy;
import com.github.matteoCarda.simplerougelike.model.entity.Item;
import com.github.matteoCarda.simplerougelike.model.entity.Player;
import com.github.matteoCarda.simplerougelike.model.entity.Character;
import com.github.matteoCarda.simplerougelike.service.CharacterService;
import com.github.matteoCarda.simplerougelike.service.CombatService;
import com.github.matteoCarda.simplerougelike.service.EnemyAIService;
import com.github.matteoCarda.simplerougelike.service.ItemService;
import com.github.matteoCarda.simplerougelike.util.MapGenerator;

import java.util.ArrayList;
import java.util.List;

import squidpony.squidai.DijkstraMap;
import squidpony.squidgrid.FOV;


/**
 * Controller principale del gioco, gestito come Singleton.
 * Orchestra la logica di gioco, lo stato della partita e le interazioni dell'utente.
 * Fa da ponte tra i dati (Model), la logica di business (Service) e l'interfaccia utente (View).
 */
public class GameController {
    // --- Singleton Pattern ---
    private static GameController instance;

    // --- Stato del Gioco ---
    private GameMap gameMap; // L'oggetto che contiene la mappa e tutte le sue entità.
    private Player player;   // Riferimento diretto al giocatore per comodità.
    private GameState gameState; // Stato attuale della partita (in gioco, game over, ecc.).

    // --- Servizi ---
    // Invece di mettere la logica qui, la deleghiamo a classi specializzate (Service).
    private final MapGenerator mapGenerator;
    private final CombatService combatService;
    private final CharacterService characterService;
    private final ItemService itemService;
    private final EnemyAIService enemyAIService;
    private static final int PLAYER_VISION_RADIUS = 8;

    private FOV fov;

    private double[][] playerFov;

    private double[][] resistanceMap;

    /**
     * Definisce i possibili stati della partita.
     * Utile per controllare quali azioni sono permesse in un dato momento.
     */
    public enum GameState {
        PLAYING,    // Il giocatore può muoversi e interagire.
        GAME_OVER,  // La partita è finita (sconfitta).
        VICTORY     // La partita è finita (vittoria).
    }

    /**
     * Definisce le direzioni di movimento possibili.
     * Usato per interpretare l'input del giocatore.
     */
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Metodo standard per ottenere l'unica istanza del GameController (Singleton).
     * Il 'synchronized' garantisce che non vengano create istanze multiple in un ambiente multithread.
     * @return L'istanza unica del GameController.
     */
    public static synchronized GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Costruttore privato, come richiesto dal pattern Singleton.
     * Qui inizializziamo tutti i servizi necessari, passandoli l'uno all'altro se necessario (Dependency Injection).
     */
    private GameController() {
        // Inizializzazione dei service.
        this.mapGenerator = new MapGenerator();
        this.characterService = new CharacterService();
        this.combatService = new CombatService(this.characterService); // Il CombatService ha bisogno del CharacterService.
        this.itemService = new ItemService(this.characterService); // Anche l'ItemService.
        this.enemyAIService = new EnemyAIService(this.combatService, this.characterService); // Anche l'EnemyAIService.

        // All'avvio, il gioco è in stato di "attesa", pronto per una nuova partita.
        this.gameState = GameState.GAME_OVER;
    }

    // --- Metodi Pubblici di Accesso (Getters) ---

    public GameMap getGameMap() {
        return gameMap;
    }

    public GameState getGameState() {
        return gameState;
    }

    public double[][] getPlayerFov() {
        return playerFov;
    }

    /**
     * Avvia una nuova partita, cancellando quella vecchia.
     * Genera una nuova mappa e posiziona il giocatore.
     */
    public void startNewGame(int width, int height, int enemyCount, int itemCount) {
        this.gameMap = mapGenerator.generateMap(width, height, enemyCount, itemCount);
        this.player = gameMap.getPlayer(); // Aggiorniamo il riferimento al giocatore.
        this.gameState = GameState.PLAYING; // La partita ha inizio!

        this.fov = new FOV();
        this.playerFov = new double[width][height];
        this.resistanceMap = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                resistanceMap[x][y] = gameMap.getTile(x, y).isWalkable() ? 0.0 : 1.0;
            }
        }

        calculateFov();

    }

    /**
     * Punto di ingresso principale per ogni turno del giocatore.
     * Riceve l'input di movimento e orchestra le azioni conseguenti.
     * @param direction La direzione in cui il giocatore intende muoversi.
     */
    public void handlePlayerTurn(Direction direction) {
        // Se non siamo in gioco (es. in un menu o in game over), non facciamo nulla.
        if (gameState != GameState.PLAYING) {
            return;
        }
        processGameTurn(direction);
    }

    private void processGameTurn(Direction playerDirection) {
        handlePlayerAction(playerDirection);

        if(characterService.isAlive(player)){
            calculateFov();
        }

        processEnemyTurns();
        checkEndGameCondition();
    }

    private void handlePlayerAction(Direction direction) {
        movePlayer(direction);
    }

    private void processEnemyTurns() {
        DijkstraMap dijkstraMapForPlayer = enemyAIService.getDijkstraMap(gameMap);
        dijkstraMapForPlayer.setGoal(player.getX(), player.getY());

        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : gameMap.getEnemies()) {
            if (characterService.isAlive(enemy)) {
                enemyAIService.performTurn(enemy, gameMap, player, dijkstraMapForPlayer);
            } else {
                enemiesToRemove.add(enemy); // Aggiungi alla lista dei morti
            }
        }
        gameMap.getEnemies().removeAll(enemiesToRemove); // Rimuovi in modo sicuro

    }

    /**
     * Logica principale per il movimento del giocatore e le interazioni basate sulla posizione.
     * Controlla cosa c'è nella cella di destinazione e agisce di conseguenza.
     * @param direction La direzione del movimento.
     */
    private void movePlayer(Direction direction) {
        int currentX = player.getX();
        int currentY = player.getY();

        int targetX = currentX;
        int targetY = currentY;

        // Calcoliamo le coordinate della cella bersaglio.
        switch (direction) {
            case UP:    targetY--; break;
            case DOWN:  targetY++; break;
            case LEFT:  targetX--; break;
            case RIGHT: targetX++; break;
        }

        // --- CONTROLLO DELLA CELLA DI DESTINAZIONE ---

        // 1. Il movimento è dentro i confini della mappa?
        if (targetX < 0 || targetX >= gameMap.getWidth() || targetY < 0 || targetY >= gameMap.getHeight()) {
            return; // Movimento non valido, il turno finisce qui.
        }

        // 2. C'è un muro?
        if (!gameMap.getTile(targetX, targetY).isWalkable()) {
            // Il giocatore "urta" il muro. Non succede nulla, ma il turno è consumato.
            return;
        }

        // 3. C'è un nemico?
        Enemy targetEnemy = getEnemyAt(targetX, targetY);
        if (targetEnemy != null) {
            // Sì! Invece di muoversi, il giocatore attacca.
            boolean isEnemyDead = combatService.performAttack(player, targetEnemy);
            if (isEnemyDead) {
                // Il nemico è morto! Lo rimuoviamo dalla mappa.
                gameMap.getEnemies().remove(targetEnemy);
            }
            // Il turno finisce. Il giocatore non si muove sulla cella del nemico.
            return;
        }

        // 4. C'è un oggetto?
        Item targetItem = getItemAt(targetX, targetY);
        if (targetItem != null) {
            // Sì! Raccogliamo l'oggetto.
            itemService.onPickup(targetItem, player);
            // L'oggetto viene rimosso dalla mappa.
            gameMap.getItems().remove(targetItem);
            // NOTA: Dopo aver raccolto l'oggetto, il giocatore si sposta comunque sulla cella.
        }

        // 5. Se la cella è libera da muri e nemici, il giocatore si muove.
        player.setPosition(targetX, targetY);
    }

    /**
     * Metodo di supporto per trovare un nemico a una data coordinata.
     * @param x Coordinata X da controllare.
     * @param y Coordinata Y da controllare.
     * @return L'oggetto Enemy se trovato e vivo, altrimenti null.
     */
    private Enemy getEnemyAt(int x, int y) {
        for (Enemy enemy : gameMap.getEnemies()) {
            // Controlliamo che il nemico sia vivo prima di interagire con lui.
            if (characterService.isAlive(enemy) && enemy.getX() == x && enemy.getY() == y) {
                return enemy;
            }
        }
        return null; // Nessun nemico vivo in questa cella.
    }

    /**
     * Metodo di supporto per trovare un oggetto a una data coordinata.
     * @param x Coordinata X da controllare.
     * @param y Coordinata Y da controllare.
     * @return L'oggetto Item se trovato, altrimenti null.
     */
    private Item getItemAt(int x, int y) {
        for (Item item : gameMap.getItems()) {
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        return null; // Nessun oggetto in questa cella.
    }

    private void checkEndGameCondition(){
        if(!characterService.isAlive(player)){
            gameState= GameState.GAME_OVER;
            System.out.println("Hai perso!");
        }
    }

    public boolean isAlive(Character character) {
        return characterService.isAlive(character);
    }

    private void calculateFov() {
        FOV.reuseFOV(resistanceMap, playerFov, player.getX(), player.getY(), PLAYER_VISION_RADIUS);
    }

}
