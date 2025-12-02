package com.example.simplerougelike.service;

import com.example.simplerougelike.model.entity.Item;
import com.example.simplerougelike.model.entity.Player;
import com.example.simplerougelike.model.entity.PotionItem;

/**
 * Service per la gestione della logica degli oggetti.
 */
public class ItemService {

    private final CharacterService characterService = new CharacterService();

    /**
     * Gestisce l'azione che si verifica quando un giocatore raccoglie un oggetto.
     *
     * @param item L'oggetto raccolto.
     * @param player Il giocatore che ha raccolto l'oggetto.
     */
    public void onPickup(Item item, Player player) {
        if (item instanceof PotionItem) {
            characterService.heal(player, ((PotionItem) item).getHealingAmount());
        }
    }
}
