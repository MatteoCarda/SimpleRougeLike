package com.github.matteoCarda.simplerougelike.service;

import com.github.matteoCarda.simplerougelike.model.entity.Character;

/**
 * Gestisce la logica di combattimento.
 * Isola le regole di attacco e applicazione del danno.
 */
public class CombatService {

    private final CharacterService characterService;

    /**
     * Costruttore.
     * @param characterService Istanza di CharacterService per la manipolazione dello stato dei personaggi.
     */
    public CombatService(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Esegue un attacco da un'entità (attacker) a un'altra (target).
     * Applica il danno e restituisce se il target è stato sconfitto.
     *
     * @param attacker L'entità che attacca.
     * @param target L'entità che subisce l'attacco.
     * @return true se il target è morto dopo l'attacco, false altrimenti.
     */
    public boolean performAttack(Character attacker, Character target) {
        // Controlli di validità: non si può attaccare o essere attaccati se nulli o morti.
        if (attacker == null || target == null || !characterService.isAlive(attacker) || !characterService.isAlive(target)) {
            return false;
        }

        // Formula del danno (attualmente semplice, può essere espansa).
        double damage = attacker.getAttackPower();
        
        // Delega l'applicazione del danno al service apposito.
        characterService.takeDamage(target, damage);

        System.out.println(attacker.getClass().getSimpleName() + " attacca " + target.getClass().getSimpleName() +
                " per " + damage + " danni. Vita rimanente: " + target.getHealth());

        // Ritorna lo stato di "morte" del target post-attacco.
        return !characterService.isAlive(target);
    }
}
