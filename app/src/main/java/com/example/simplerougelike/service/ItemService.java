package com.example.simplerougelike.service;

import com.example.simplerougelike.model.entity.Item;
import com.example.simplerougelike.model.entity.Player;
import com.example.simplerougelike.model.entity.PotionItem;

public class ItemService {

    private final CharacterService characterService = new CharacterService();

    public void onPickup(Item item, Player player) {
        if (item instanceof PotionItem) {
            characterService.heal(player, ((PotionItem) item).getHealingAmount());
        }
    }
}
