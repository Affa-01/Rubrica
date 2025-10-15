package org.rubrica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public abstract class EditorPersona extends JFrame implements ActionListener {
    String[] labels = {"nome: ", "cognome: ", "indirizzo: ", "telefono: ", "età: "};
    int numRighe = labels.length; 
    JTextField[] campiTesto = new JTextField[5];

    public EditorPersona() {
        setTitle("editor-persona");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        
        JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numRighe; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            campiTesto[i] = new JTextField(10);
            l.setLabelFor(campiTesto[i]);
            p.add(campiTesto[i]); 
        } 

        SpringUtilities.makeCompactGrid(p, numRighe, 2,6, 6, 6, 6);

        add(p, BorderLayout.CENTER);

        JPanel barraBottoni = new JPanel(new FlowLayout());
        add(barraBottoni, BorderLayout.SOUTH);

        JButton buttonSalva = new JButton("Salva");
        barraBottoni.add(buttonSalva);
        buttonSalva.addActionListener(this);
        buttonSalva.setActionCommand("SALVA");

        JButton buttonAnnulla = new JButton("Annulla");
        barraBottoni.add(buttonAnnulla);
        buttonAnnulla.addActionListener(this);
        buttonAnnulla.setActionCommand("ANNULLA");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "SALVA":
                String stringEtà = campiTesto[4].getText();
                try {
                    Integer.parseInt(stringEtà);
                    salva();
                    this.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Età non valida", "Errore", JOptionPane.ERROR_MESSAGE); 
                }
                break;
            case "ANNULLA":
                this.dispose();
                break;
            default:
                break;
        }
    }

    protected abstract void salva();
}
