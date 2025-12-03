package com.example.simplerougelike.service;

import com.example.simplerougelike.model.entity.Character;
import com.example.simplerougelike.model.entity.Item;
import com.example.simplerougelike.model.entity.Player;

/**
 * Service per la gestione della logica di base dei personaggi (salute, stato, ecc.).
 * Isola le manipolazioni di stato dal modello e dai controller.
 */
public class CharacterService {

    /**
     * Applica una certa quantità di danno a un personaggio.
     * Include controlli per assicurarsi che il personaggio sia valido e vivo prima di agire.
     * La vita non può scendere sotto lo zero.
     *
     * @param character Il personaggio che subisce il danno.
     * @param damageAmount La quantità di danno da infliggere.
     */
    public void takeDamage(Character character, double damageAmount) {
        // Un controllo di sicurezza: non facciamo nulla se il personaggio non esiste o è già morto.
        if (character == null || !isAlive(character)) {
            return;
        }

        double newHealth = character.getHealth() - damageAmount;
        // Assicuriamoci che la vita non diventi negativa.
        if (newHealth < 0) {
            newHealth = 0;
        }
        character.setHealth(newHealth);
    }

    /**
     * Cura un personaggio, ripristinando una data quantità di salute.
     * La salute non può superare il valore massimo del personaggio.
     *
     * @param character Il personaggio da curare.
     * @param healingAmount L'ammontare di salute da ripristinare.
     */
    public void heal(Character character, double healingAmount) {
        double newHealth = character.getHealth() + healingAmount;
        // Impediamo che la cura superi la salute massima.
        if (newHealth > character.getMaxHealth()) {
            newHealth = character.getMaxHealth();
        }
        character.setHealth(newHealth);
    }

    /**
     * Controlla se un personaggio è attualmente vivo.
     *
     * @param character Il personaggio da controllare.
     * @return true se la salute del personaggio è maggiore di zero, false altrimenti.
     */
    public boolean isAlive(Character character) {
        // Un personaggio nullo non è considerato vivo.
        if (character == null) {
            return false;
        }
        return character.getHealth() > 0;
    }

    /**
     * Aggiunge un oggetto all'inventario del giocatore.
     * Questo metodo verifica che il personaggio sia effettivamente un giocatore prima di procedere.
     *
     * @param character Il personaggio che sta tentando di raccogliere l'oggetto.
     * @param item L'oggetto da aggiungere all'inventario.
     * @return true se l'oggetto è stato aggiunto con successo, false altrimenti (es. se il personaggio non è un Player).
     */
    public boolean takeItem(Character character, Item item) {
        // Solo i Player possono avere un inventario e raccogliere oggetti.
        if (!(character instanceof Player)) {
            return false;
        }
        // Se è un player, facciamo il cast e aggiungiamo l'oggetto al suo inventario.
        Player player = (Player) character;
        player.getInventory().add(item);
        return true;
    }
}
