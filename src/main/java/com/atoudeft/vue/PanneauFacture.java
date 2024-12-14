package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauFacture extends JPanel {
    private JTextField montantText, numeroFactureText, descriptionText;

    public PanneauFacture() {
        montantText = new JTextField(15);
        numeroFactureText = new JTextField(15);
        descriptionText = new JTextField(30);

        setLayout(new GridLayout(4, 2));
        add(new JLabel("Montant :"));
        add(montantText);
        add(new JLabel("Numéro Facture :"));
        add(numeroFactureText);
        add(new JLabel("Description :"));
        add(descriptionText);
    }

    public String getMontant() {
        return montantText.getText();
    }

    public String getNumeroFacture() {
        return numeroFactureText.getText();
    }

    public String getDescription() {
        return descriptionText.getText();
    }
}