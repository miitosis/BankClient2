package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauDepotRetrait extends JPanel {

    private JTextField montant;

    public PanneauDepotRetrait(String montant, boolean TorF){

        this.montant = new JTextField(15);
        this.montant.setText(String.valueOf(montant));

        setLayout(new GridLayout(1, 1));
        add(new JLabel((TorF) ? "Retrait: " : "Depot: "));
        add(this.montant);
    }

    public String getMontant(){

        return  montant.getText();

    }

}
