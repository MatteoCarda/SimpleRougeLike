package com.example.simplerougelike.service;

import com.example.simplerougelike.model.entity.Character;

/**
 * Service per la gestione della logica dei personaggi.
 */
public class CharacterService {

    /**
     * Applica il danno a un personaggio. La salute non può scendere sotto lo zero.
     *
     * @param character Il personaggio che subisce il danno.
     * @param damage L'ammontare del danno da applicare.
     */
    public void takeDamage(Character character, double damage) {
        character.setHealth(character.getHealth() - damage);
        if (character.getHealth() <= 0) {
            character.setHealth(0);
        }
    }

    /**
     * Permette a un personaggio di attaccarne un altro.
     *
     * @param attacker Il personaggio che sferra l'attacco.
     * @param target Il personaggio che subisce l'attacco.
     */
    public void attack(Character attacker, Character target) {
        takeDamage(target, attacker.getAttackPower());
    }

    /**
     * Cura un personaggio. La salute non può superare la salute massima.
     *
     * @param character Il personaggio da curare.
     * @param amount L'ammontare di salute da ripristinare.
     */
    public void heal(Character character, double amount) {
        character.setHealth(character.getHealth() + amount);
        if (character.getHealth() > character.getMaxHealth()) {
            character.setHealth(character.getMaxHealth());
        }
    }
}
