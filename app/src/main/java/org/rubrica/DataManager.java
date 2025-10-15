package org.rubrica;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

public class DataManager {
    Connection conn;

    public DataManager() throws RuntimeException, SQLException {
        inizializza();
    }

    void salva(Persona p) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES ('" 
                + p.getNome() + "', '" + p.getCognome() + "', '" + p.getIndirizzo() + "', '" 
                + p.getTelefono() + "', '" +  p.getEta() + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifica(int id, Persona pm) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE persone SET nome='" + pm.getNome() + "', cognome='" + pm.getCognome() 
                + "', indirizzo='" + pm.getIndirizzo() + "', telefono='" + pm.getTelefono() 
                + "', eta=" + pm.getEta() + " WHERE id=" + id;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void elimina(int id) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM persone WHERE id=" + id;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Persona> getPersone() throws SQLException {
        Statement stmt = conn.createStatement();
        var rs = stmt.executeQuery("SELECT * FROM persone");
        Vector<Persona> persone = new Vector<>();
        while (rs.next()) {
            Persona p = new Persona(
                rs.getString("nome"),
                rs.getString("cognome"),
                rs.getString("indirizzo"),
                rs.getString("telefono"),
                rs.getInt("eta"),
                rs.getInt("id")
            );
            persone.add(p);
        }
        return persone;
    }

    public Persona getPersona(int id) {
        try {
            Statement stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT * FROM persone WHERE id=" + id);
            if (rs.next()) {
                return new Persona(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("indirizzo"),
                    rs.getString("telefono"),
                    rs.getInt("eta"),
                    rs.getInt("id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void inizializza() throws RuntimeException, SQLException {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("credenziali_database.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento delle proprietà del database", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        this.conn = DriverManager.getConnection(url, user, password);
    }
}
