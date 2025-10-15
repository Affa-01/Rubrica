package org.rubrica;

public class Persona {
    String nome;
    String cognome;
    String indirizzo;
    String telefono;
    int eta;

    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
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

    void applicaModifiche(Persona p) {
        p.nome = this.nome;
        p.cognome = this.cognome;
        p.indirizzo = this.indirizzo;
        p.telefono = this.telefono;
        p.eta = this.eta;
    }

    @Override
    public String toString() {
        return nome + ";" + cognome + ";" + indirizzo + ";" + telefono + ";" + eta;
    }
}
