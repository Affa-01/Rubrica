package org.rubrica;

public class Persona {
    String nome;
    String cognome;
    String indirizzo;
    String telefono;
    int età;

    public Persona(String nome, String cognome, String indirizzo, String telefono, int età) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.età = età;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCognome() {
        return cognome;
    }
}
