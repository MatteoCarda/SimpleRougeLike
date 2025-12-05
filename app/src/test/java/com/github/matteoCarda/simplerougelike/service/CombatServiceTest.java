package com.github.matteoCarda.simplerougelike.service;

import com.github.matteoCarda.simplerougelike.model.entity.Enemy;
import com.github.matteoCarda.simplerougelike.model.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CombatServiceTest {

    // --- Novità di Mockito ---

    // @Mock crea un oggetto finto (un mock) di CharacterService.
    // Questo oggetto NON eseguirà la vera logica di CharacterService.
    @Mock
    private CharacterService mockCharacterService;

    // @InjectMocks crea un'istanza REALE di CombatService e cerca di
    // iniettare automaticamente tutti gli oggetti annotati con @Mock
    // nei suoi campi corrispondenti (nel nostro caso, nel costruttore).
    @InjectMocks
    private CombatService combatService;

    // --- Oggetti di gioco standard ---
    private Player player;
    private Enemy enemy;

    @Before
    public void setUp() {
        // Questa riga è FONDAMENTALE. Inizializza tutti gli oggetti annotati con @Mock e @InjectMocks.
        MockitoAnnotations.openMocks(this);

        // Creiamo istanze reali per il giocatore e il nemico
        player = new Player(0, 0); // 100 HP, 10 Attack
        enemy = new Enemy(1, 1);   // 30 HP, 5 Attack

        when(mockCharacterService.isAlive(enemy)).thenReturn(true);
        when(mockCharacterService.isAlive(player)).thenReturn(true);
    }

    @Test
    public void performAttack_AttackerDamagesTarget() {
        // Arrange
        // Qui non dobbiamo fare nulla di speciale con Mockito, perché vogliamo solo
        // verificare che un metodo del nostro mock venga chiamato.
        double expectedDamage = player.getAttackPower(); // Ci aspettiamo 10 di danno

        // Act
        // Eseguiamo l'attacco dal giocatore al nemico
        combatService.performAttack(player, enemy);

        // Assert
        // Questa è la verifica più importante con Mockito:
        // verify(<oggetto_mock>).<metodo_che_doveva_essere_chiamato>(<con_quali_argomenti>);
        // Stiamo verificando che il metodo `takeDamage` del nostro `mockCharacterService`
        // sia stato chiamato ESATTAMENTE UNA VOLTA con il nemico come bersaglio e 10.0 come danno.
        verify(mockCharacterService, times(1)).takeDamage(enemy, expectedDamage);
    }

    @Test
    public void performAttack_ReturnsTrueIfTargetDies() {
        // Arrange
        // Qui istruiamo il nostro mock.
        // "QUANDO (when) viene chiamato `isAlive` sul nostro mockCharacterService
        //  con il nemico come argomento, ALLORA (thenReturn) deve restituire false."
        // Stiamo simulando lo scenario in cui il nemico muore dopo l'attacco.
        when(mockCharacterService.isAlive(enemy))
                .thenReturn(true)
                .thenReturn(false);


        // Act
        // Eseguiamo l'attacco
        boolean targetDied = combatService.performAttack(player, enemy);

        // Assert
        // Verifichiamo che il metodo `performAttack` abbia restituito true,
        // come ci aspettiamo quando il bersaglio muore.
        assertTrue(targetDied);
    }

    @Test
    public void performAttack_ReturnsFalseIfTargetSurvives() {
        // Arrange
        // Scenario opposto: simuliamo che il nemico sopravviva.

        // Act
        boolean targetDied = combatService.performAttack(player, enemy);

        // Assert
        // Verifichiamo che il metodo abbia restituito false.
        assertFalse(targetDied);
    }
}
