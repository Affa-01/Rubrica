package org.rubrica;

public class PersonaModifiche {
    String nome;
    String cognome;
    String indirizzo;
    String telefono;
    Integer età;

    public PersonaModifiche(String nome, String cognome, String indirizzo, String telefono, Integer età) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.età = età;
    }

    void applicaModifiche(Persona p) {
        if (nome != null) p.nome = nome;
        if (cognome != null) p.cognome = cognome;
        if (indirizzo != null) p.indirizzo = indirizzo;
        if (telefono != null) p.telefono = telefono;
        if (età != null) p.età = età;
    }
}
