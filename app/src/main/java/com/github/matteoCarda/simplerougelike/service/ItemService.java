package com.github.matteoCarda.simplerougelike.service;

import com.github.matteoCarda.simplerougelike.model.entity.Item;
import com.github.matteoCarda.simplerougelike.model.entity.Player;
import com.github.matteoCarda.simplerougelike.model.entity.PotionItem;

/**
 * Gestisce le interazioni con gli oggetti {@link Item}.
 */
public class ItemService {

    private final CharacterService characterService;

    /**
     * Costruttore.
     * @param characterService Istanza di CharacterService per interagire con l'inventario del giocatore.
     */
    public ItemService(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Logica eseguita quando un giocatore raccoglie un oggetto dalla mappa.
     * Attualmente, si limita ad aggiungere l'oggetto all'inventario.
     *
     * @param item L'oggetto da raccogliere.
     * @param player Il giocatore che raccoglie l'oggetto.
     */
    public void onPickup(Item item, Player player) {
        // La logica di base è aggiungere l'oggetto all'inventario.
        boolean itemTaken = characterService.takeItem(player, item);

        if (itemTaken) {
            System.out.println(player.getClass().getSimpleName() + " ha raccolto: " + item.getName());
        }

        // In futuro, si potrebbero aggiungere logiche specifiche per tipo di oggetto,
        // es. per oggetti che si attivano immediatamente al contatto.
    }
}
