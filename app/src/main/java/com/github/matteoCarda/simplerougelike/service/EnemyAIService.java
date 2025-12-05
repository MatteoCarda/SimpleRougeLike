package com.github.matteoCarda.simplerougelike.service;

import com.github.matteoCarda.simplerougelike.model.GameMap;
import com.github.matteoCarda.simplerougelike.model.entity.Enemy;
import com.github.matteoCarda.simplerougelike.model.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import squidpony.squidai.DijkstraMap;
import squidpony.squidmath.Coord;


public class EnemyAIService {
    private final CombatService combatService;
    private final CharacterService characterService;
    private final Random random = new Random();

    public EnemyAIService(CombatService combatService, CharacterService characterService) {
        this.combatService = combatService;
        this.characterService = characterService;
    }

    public void performTurn(Enemy enemy, GameMap gameMap, Player player, DijkstraMap dijkstraMap){
        int distance = Math.abs(enemy.getX() - player.getX()) + Math.abs(enemy.getY() - player.getY());
        if (distance <= 1){
            combatService.performAttack(enemy, player);
            return;
        }
        if (distance<=8){
            ArrayList<Coord> path = dijkstraMap.findPath(1, null,null, Coord.get(enemy.getX(), enemy.getY()));
            if (!path.isEmpty()){
                Coord nextStep = path.get(0);
                if (isMoveValid(nextStep.x, nextStep.y, gameMap, player)){
                    enemy.setPosition(nextStep.x, nextStep.y);
                    return;

                }
            }
        }

        int dirX = random.nextInt(3) - 1;
        int dirY = random.nextInt(3) - 1;
        int targetX = enemy.getX() + dirX;
        int targetY = enemy.getY() + dirY;

        if (isMoveValid(targetX, targetY, gameMap, player)){
            enemy.setPosition(targetX, targetY);
        }
    }

    public DijkstraMap getDijkstraMap(GameMap gameMap) {
        char[][] costMap = new char[gameMap.getWidth()][gameMap.getHeight()];
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                costMap[x][y] = gameMap.getTile(x, y).isWalkable() ? '.' : '#';
            }
        }
        return new DijkstraMap(costMap, 'M');
    }

    private boolean isMoveValid(int x, int y, GameMap gameMap, Player player) {
        if (x < 0 || x >= gameMap.getWidth() || y < 0 || y >= gameMap.getHeight()) return false;
        if (!gameMap.getTile(x, y).isWalkable()) return false;
        if (player.getX() == x && player.getY() == y) return false;

        // Controlla se c'è un altro nemico in quella posizione
        for (Enemy otherEnemy : gameMap.getEnemies()) {
            if (characterService.isAlive(otherEnemy) && otherEnemy.getX() == x && otherEnemy.getY() == y) {
                return false;
            }
        }
        return true;
    }
}
