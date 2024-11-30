package com.atoudeft.vue;

import com.atoudeft.client.Config;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        // Utiliser les valeurs fournies si elles sont valides, sinon utiliser celles par défaut
        String adresse = (adr == null || adr.isEmpty()) ? Config.ADRESSE_SERVEUR : adr;
        int numeroPort = (port <= 0) ? Config.PORT_SERVEUR : port;

        // Initialiser les champs de texte avec les valeurs d'adresse et de port
        txtAdrServeur = new JTextField(adresse, 15);
        txtNumPort = new JTextField(String.valueOf(numeroPort), 5);

        // Configurer la mise en page du panneau
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Ajouter les étiquettes et champs de texte au panneau
        add(new JLabel("Adresse du serveur :"), gbc);
        gbc.gridx++;
        add(txtAdrServeur, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Port du serveur :"), gbc);
        gbc.gridx++;
        add(txtNumPort, gbc);
    }

    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }

    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
