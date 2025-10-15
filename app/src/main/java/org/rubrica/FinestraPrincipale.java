package org.rubrica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FinestraPrincipale extends JFrame implements ActionListener {
    JButton buttonNuovo;
    JButton buttonModifica;
    JButton buttonElimina;
    JTable table;
    DataManager dm;

    public FinestraPrincipale(DataManager dm) {
        this.dm = dm;
        setTitle("Rubrica");
        setLayout(new BorderLayout());
        setSize(400, 300);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new JTable();
        generaTabella();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel barraBottoni = new JPanel(new FlowLayout());
        add(barraBottoni, BorderLayout.SOUTH);


        buttonNuovo = new JButton("Nuovo");
        barraBottoni.add(buttonNuovo);
        buttonNuovo.addActionListener(this);
        buttonNuovo.setActionCommand("NUOVO");

        buttonModifica = new JButton("Modifica");
        barraBottoni.add(buttonModifica);
        buttonModifica.addActionListener(this);
        buttonModifica.setActionCommand("MODIFICA");

        buttonElimina = new JButton("Elimina");
        barraBottoni.add(buttonElimina);
        buttonElimina.addActionListener(this);
        buttonElimina.setActionCommand("ELIMINA");

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int id;
        switch (e.getActionCommand()) {
            case "NUOVO":
                apriFinestraNuovo();
                break;
            case "MODIFICA":
                id = table.getSelectedRow();
                if (id == -1) {
                    JOptionPane.showMessageDialog(null, "Nessuna riga selezionata", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                apriFinestraModifica(id);
                break;
            case "ELIMINA":
                id = table.getSelectedRow();
                if (id == -1) {
                    JOptionPane.showMessageDialog(null, "Nessuna riga selezionata", "Errore", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                elimina(id);
                generaTabella();
                break;
        
            default:
                break;
        }

    }

    private void apriFinestraNuovo() {
        new FinestraNuovo(this, dm);
    }
    private void apriFinestraModifica(int id) {
        new FinestraModifica(this, dm, id);
    }

    private void elimina(int id) {
        dm.elimina(id);
    }

    public void generaTabella() {
        String[] nomiColonne = { "Nome", "Cognome", "Telefono"};
        DefaultTableModel model = new DefaultTableModel(nomiColonne, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Persona p : dm.getPersone()) {
            Object[] row = { p.getNome(), p.getCognome(), p.getTelefono() };
            model.addRow(row);
        } 

        table.setModel(model);
    }
}
