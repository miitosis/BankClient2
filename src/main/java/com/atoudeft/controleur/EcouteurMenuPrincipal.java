package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauConfigServeur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft
 * @version 1.0
 * @since 2024-11-01
 */
public class EcouteurMenuPrincipal implements ActionListener {
    private Client client;
    private JFrame fenetre;

    public EcouteurMenuPrincipal(Client client, JFrame fenetre) {
        this.client = client;
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String action;
        int res;

        if (source instanceof JMenuItem) {
            action = ((JMenuItem) source).getActionCommand();
            switch (action) {
                case "CONNECTER":
                    if (!client.isConnecte()) {
                        if (!client.connecter()) {
                            JOptionPane.showMessageDialog(fenetre, "Le serveur ne répond pas");
                            break;
                        }
                    }
                    break;
                case "DECONNECTER":
                    if (!client.isConnecte())
                        break;
                    res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                            "Confirmation Déconnecter",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (res == JOptionPane.OK_OPTION) {
                        client.deconnecter();
                    }
                    break;
                case "CONFIGURER":
                    configurerServeur();
                    break;
                case "QUITTER":
                    if (client.isConnecte()) {
                        res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                                "Confirmation Quitter",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (res == JOptionPane.OK_OPTION) {
                            client.deconnecter();
                            System.exit(0);
                        }
                    } else {
                        System.exit(0);
                    }
                    break;
            }
        }
    }

    private void configurerServeur() {
        PanneauConfigServeur panneauConfig = new PanneauConfigServeur(client.getAdrServeur(), client.getPortServeur());

        boolean configurationValide = false;
        while (!configurationValide) {
            int option = JOptionPane.showConfirmDialog(fenetre, panneauConfig, "Configuration serveur",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Récupérer l'adresse et le port saisis par l'utilisateur
                    String nouvelleAdresse = panneauConfig.getAdresseServeur();
                    int nouveauPort = Integer.parseInt(panneauConfig.getPortServeur());

                    // Mise à jour du client avec les nouvelles informations
                    client.setAdrServeur(nouvelleAdresse);
                    client.setPortServeur(nouveauPort);
                    configurationValide = true;
                } catch (NumberFormatException e) {
                    // Afficher un message d'erreur si le port n'est pas un entier valide
                    JOptionPane.showMessageDialog(fenetre, "Le port doit être un entier valide.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Si l'utilisateur annule la configuration
                configurationValide = true;
            }
        }
    }
}
