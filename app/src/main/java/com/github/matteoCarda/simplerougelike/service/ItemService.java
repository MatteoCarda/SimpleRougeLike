package com.github.matteoCarda.simplerougelike.service;

import com.github.matteoCarda.simplerougelike.model.entity.Item;
import com.github.matteoCarda.simplerougelike.model.entity.Player;
import com.github.matteoCarda.simplerougelike.model.entity.PotionItem;

/**
 * Servizio dedicato alla gestione delle interazioni con gli oggetti (Items).
 * Si occupa di definire cosa succede quando un personaggio interagisce con un oggetto sulla mappa.
 */
public class ItemService {

    // Riferimento al CharacterService per poter modificare lo stato del personaggio (es. aggiungendo oggetti all'inventario).
    private final CharacterService characterService;

    /**
     * Costruttore che richiede il CharacterService (Dependency Injection).
     * In questo modo, l'ItemService non deve preoccuparsi di come sono gestiti i personaggi,
     * ma solo di delegare le azioni che li riguardano.
     *
     * @param characterService L'istanza del servizio per i personaggi.
     */
    public ItemService(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Gestisce l'evento che si verifica quando un giocatore passa su una cella contenente un oggetto.
     * L'azione predefinita è tentare di aggiungere l'oggetto all'inventario.
     *
     * @param item L'oggetto trovato sulla mappa.
     * @param player Il giocatore che ha trovato l'oggetto.
     */
    public void onPickup(Item item, Player player) {
        // Controlliamo di che tipo di oggetto si tratta per applicare logiche specifiche se necessario.
        if (item instanceof PotionItem) {
            // In questo caso, è una pozione. La logica è semplice: la aggiungiamo all'inventario.
            // Usiamo il CharacterService, che contiene la logica per aggiungere un oggetto a un personaggio.
            characterService.takeItem(player, item);

            // Nota: In passato, questo metodo applicava direttamente l'effetto di cura.
            // Ora, l'oggetto viene solo raccolto. La logica per "usare" l'oggetto dall'inventario
            // sarà gestita altrove (es. in un 'InventoryService' o nel 'GameController').
            System.out.println(player.getClass().getSimpleName() + " ha raccolto una " + item.getName());
        }
        // Qui si potrebbero aggiungere altri 'else if' per gestire tipi diversi di oggetti (armi, armature, ecc.).
    }
}
