package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauHistorique extends JPanel {

    private DefaultListModel<String> historique;
    private JList<String> historique2;


    public PanneauHistorique(String[] liste) {

        setLayout(new GridLayout(1, 1));
        historique = new DefaultListModel<>();

        historique2 = new JList<>(historique);
        historique2.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        historique2.setBorder(BorderFactory.createTitledBorder("Comptes bancaires"));
        historique2.setPreferredSize(new Dimension(500,500));
        add(historique2, BorderLayout.SOUTH);
        setHistorique(liste);
    }

    public void setHistorique( String[] list) {
        historique.clear();
        if (list.length == 0) {
            historique.addElement("Vide");
        } else {
            for (String temp : list) {
                historique.addElement(temp);
            }
        }

    }

}