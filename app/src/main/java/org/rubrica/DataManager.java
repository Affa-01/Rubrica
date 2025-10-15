package org.rubrica;

import java.util.Vector;

public class DataManager {
    Vector<Persona> persone;

    public DataManager() {
        persone = new Vector<Persona>();

        persone.add(new Persona("Mario", "Rossi", "Via Roma 1", "1234567890", 30));
        persone.add(new Persona("Luigi", "Verdi", "Via Milano 2", "0987654321", 25));
        persone.add(new Persona("Anna", "Bianchi", "Via Napoli 3", "1122334455", 28));
    }

    void salva(Persona p) {
        persone.add(p);
    }

    void modifica(int id, PersonaModifiche pm) {
        Persona p = persone.get(id);
        pm.applicaModifiche(p);
    }

    void elimina(int id) {
        persone.remove(id);   
    }

    public Vector<Persona> getPersone() {
        return persone;
    }
}
