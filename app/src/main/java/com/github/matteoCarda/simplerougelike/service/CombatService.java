package com.github.matteoCarda.simplerougelike.service;

import com.github.matteoCarda.simplerougelike.model.entity.Character;

/**
 * Servizio dedicato alla gestione di tutte le logiche legate al combattimento.
 * Isola le regole di attacco e danno dal resto del modello e dei controller.
 */
public class CombatService {

    /**
     * Riferimento al CharacterService per manipolare lo stato dei personaggi (es. vita).
     * Questa è un'iniezione di dipendenza: il CombatService non crea il CharacterService,
     * ma lo riceve dall'esterno, rendendo il codice più modulare e testabile.
     */
    private final CharacterService characterService;

    /**
     * Costruttore del servizio di combattimento.
     * @param characterService Un'istanza di CharacterService, necessaria per operare.
     */
    public CombatService(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Esegue una singola azione di attacco da un personaggio (attacker) a un altro (target).
     * Questo metodo si occupa di verificare la validità dell'attacco e di applicare il danno.
     *
     * @param attacker Il personaggio che esegue l'attacco.
     * @param target Il personaggio che dovrebbe subire l'attacco.
     */
    public boolean performAttack(Character attacker, Character target) {
        // Prima di tutto, un po' di controlli di sicurezza.
        // Non ha senso continuare se uno dei due contendenti non esiste o è già fuori combattimento.
        if (attacker == null || target == null || !characterService.isAlive(attacker) || !characterService.isAlive(target)) {
            return false; // Interrompiamo l'azione qui.
        }

        // Calcoliamo il danno. Per ora è semplice: il danno è uguale alla potenza d'attacco.
        // In futuro, qui si potrebbero aggiungere calcoli più complessi (es. bonus, armature).
        double damage = attacker.getAttackPower();
        
        // Applichiamo il danno al bersaglio usando il servizio apposito.
        // Deleghiamo al CharacterService il compito di ridurre la vita del target.
        characterService.takeDamage(target, damage);

        // Questo è utile per seguire cosa succede durante il gioco.
        // Stampa un messaggio nella console che riassume l'esito dell'attacco.
        System.out.println(attacker.getClass().getSimpleName() + " attacca " + target.getClass().getSimpleName() +
                " infliggendo " + damage + " danni. Vita rimanente: " + target.getHealth());

        return !characterService.isAlive(target); // Restituiamo true se il target è morto.
    }
}
