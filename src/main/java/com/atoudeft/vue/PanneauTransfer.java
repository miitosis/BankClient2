package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauTransfer extends JPanel {
    private JTextField montantText, compteDestinataireText;

    public PanneauTransfer() {
        montantText = new JTextField(15);
        compteDestinataireText = new JTextField(15);

        setLayout(new GridLayout(3, 2));
        add(new JLabel("Montant :"));
        add(montantText);
        add(new JLabel("Compte destinataire :"));
        add(compteDestinataireText);
    }

    public String getMontant() {
        return montantText.getText();
    }

    public String getCompteDestinataire() {
        return compteDestinataireText.getText();
    }
}