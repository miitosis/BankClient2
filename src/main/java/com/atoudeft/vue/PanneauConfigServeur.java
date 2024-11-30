package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        //à compléter
        JLabel lNumCompteClient = new JLabel("Adresse IP : "),
                lNip = new JLabel("Port : ");
        txtAdrServeur = new JTextField(30);
        txtNumPort = new JTextField(30);

        txtAdrServeur.setBorder(BorderFactory.createTitledBorder("Adresse IP : "));
        txtNumPort.setBorder(BorderFactory.createTitledBorder("Port : "));

        JPanel p1 = new JPanel();
        JPanel pTout = new JPanel(new GridLayout(3,1));

        p1.add(txtAdrServeur);
        p1.add(txtNumPort);

        this.setLayout(new BorderLayout());
        pTout.add(p1);


        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));
    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
