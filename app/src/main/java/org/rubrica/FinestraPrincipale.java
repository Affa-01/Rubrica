package org.rubrica;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
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

        
        String[] nomiColonne = { "Nome", "Cognome", "Telefono"};
        DefaultTableModel model = new DefaultTableModel(nomiColonne, 0);
        for (Persona p : dm.getPersone()) {
            Object[] row = { p.getNome(), p.getCognome(), p.getTelefono() };
            model.addRow(row);
        }
        table = new JTable(model);
        add(table, BorderLayout.CENTER);

        JPanel barraBottoni = new JPanel(new FlowLayout());
        add(barraBottoni, BorderLayout.SOUTH);


        buttonNuovo = new JButton("Nuovo");
        barraBottoni.add(buttonNuovo);
        buttonNuovo.addActionListener(this);
        buttonNuovo.setActionCommand("NUOVO");

        buttonModifica = new JButton("Modifica");
        barraBottoni.add(buttonModifica);
        buttonModifica.addActionListener(this);
        buttonNuovo.setActionCommand("MODIFICA");

        buttonElimina = new JButton("Elimina");
        barraBottoni.add(buttonElimina);
        buttonElimina.addActionListener(this);
        buttonNuovo.setActionCommand("ELIMINA");

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "NUOVO":
                apriFinestraNuovo();
                break;
            case "MODIFICA":
                apriFinestraModifica();
                break;
            case "ELIMINA":
                
                break;
        
            default:
                break;
        }

    }

    private void apriFinestraNuovo() {
        FinestraNuovo fn = new FinestraNuovo(dm);
    }
    private void apriFinestraModifica() {
        FinestraModifica fm = new FinestraModifica(dm);
    }
}
