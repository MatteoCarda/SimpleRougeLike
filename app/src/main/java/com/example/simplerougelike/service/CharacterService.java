package com.example.simplerougelike.service;

import com.example.simplerougelike.model.entity.Character;

public class CharacterService {

    public void takeDamage(Character character, double damage) {
        character.setHealth(character.getHealth() - damage);
        if (character.getHealth() <= 0) {
            character.setHealth(0);
        }
    }

    public void attack(Character attacker, Character target) {
        takeDamage(target, attacker.getAttackPower());
    }

    public void heal(Character character, double amount) {
        character.setHealth(character.getHealth() + amount);
        if (character.getHealth() > character.getMaxHealth()) {
            character.setHealth(character.getMaxHealth());
        }
    }
}
