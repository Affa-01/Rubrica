package org.rubrica;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.google.gson.GsonBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.yaml.snakeyaml.Yaml;

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

    public void esportaCSV(String filename) throws Exception {
        Vector<Persona> persone = getPersone();
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Persona p : persone) {
                writer.println(p.toCsv());
            }
        }
    }

    public void esportaJSON(String filename) throws Exception {
        Vector<Persona> persone = getPersone();
        try (PrintWriter writer = new PrintWriter(filename)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(persone, writer);
        }
    }

    public void esportaXML(String filename) throws Exception {
        Vector<Persona> persone = getPersone();
        try (PrintWriter writer = new PrintWriter(filename)) {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("persone");
            doc.appendChild(rootElement);
            for (Persona p : persone) {
                Element personaElement = doc.createElement("persona");
                rootElement.appendChild(personaElement);

                Element nome = doc.createElement("nome");
                nome.appendChild(doc.createTextNode(p.getNome()));
                personaElement.appendChild(nome);

                Element cognome = doc.createElement("cognome");
                cognome.appendChild(doc.createTextNode(p.getCognome()));
                personaElement.appendChild(cognome);

                Element indirizzo = doc.createElement("indirizzo");
                indirizzo.appendChild(doc.createTextNode(p.getIndirizzo()));
                personaElement.appendChild(indirizzo);

                Element telefono = doc.createElement("telefono");
                telefono.appendChild(doc.createTextNode(p.getTelefono()));
                personaElement.appendChild(telefono);

                Element eta = doc.createElement("eta");
                eta.appendChild(doc.createTextNode(String.valueOf(p.getEta())));
                personaElement.appendChild(eta);
            }
            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        }
    }

    public void esportaYML(String filename) throws Exception {
        Vector<Persona> persone = getPersone();
        List<Map<String,Object>> out = new ArrayList<>();

        for (Persona p : persone) {
            Map<String,Object> m = new LinkedHashMap<>(); // keeps insertion order
            m.put("nome", p.getNome());
            m.put("cognome", p.getCognome());
            m.put("indirizzo", p.getIndirizzo());
            m.put("telefono", p.getTelefono());
            m.put("eta", p.getEta());
            out.add(m);
        }

        try (PrintWriter writer = new PrintWriter(filename)) {
            new Yaml().dump(out, writer);
        }
    }
}
