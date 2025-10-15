package org.rubrica;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DataManager {
    Vector<Persona> persone;

    public DataManager() {
        inizializza();
    }

    void salva(Persona p) {
        persone.add(p);
        salvaSuFile();
    }

    void modifica(int id, Persona pm) {
        Persona p = persone.get(id);
        pm.applicaModifiche(p);
        salvaSuFile();
    }

    void elimina(int id) {
        persone.remove(id);   
        salvaSuFile();
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public Persona getPersona(int id) {
        return persone.get(id);
    }


    public void inizializza() {
        Vector<Persona> loaded = new Vector<Persona>();
        try (Scanner sc = new Scanner(new File("informazioni.txt"))) {
            while (sc.hasNextLine()) {
                String[] params = sc.nextLine().split(";");
                loaded.add(new Persona(
                    params[0],
                    params[1],
                    params[2],
                    params[3],
                    Integer.parseInt(params[4])
                ));
            }
        }
        catch (Exception e) {
            
        }
        persone = loaded;
    }

    private void salvaSuFile() {
        try (PrintStream out = new PrintStream("informazioni.txt")) {
            for (Persona p : persone) {
                out.println(p.toString()); 
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nel salvataggio dei dati", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
