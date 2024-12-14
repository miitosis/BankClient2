package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauDepotRetrait;
import com.atoudeft.vue.PanneauFacture;
import com.atoudeft.vue.PanneauTransfer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;

    public EcouteurOperationsCompte(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        String nomAction;
        if (source instanceof JButton) {
            nomAction = ((JButton)source).getActionCommand();
            switch (nomAction) {
                case "EPARGNE":
                    client.envoyer("EPARGNE");
                    break;

                case "DEPOT" :
                    gererDepot();
                    break;

                case "RETRAIT" :
                    gererRetrait();
                    break;

                case "TRANSFER":
                    gererTransfer();
                    break;

                case "FACTURE":
                    gererFacture();
                    break;

                case "HIST":
                    client.envoyer("HIST");
                    break;

                default:
                    System.out.println("Action Invalide : " + nomAction);
            }

        }

    }

    private void gererDepot() {
        boolean valide = false;

        while (!valide) {
            PanneauDepotRetrait panneauDepot = new PanneauDepotRetrait("0", false);

            int resultat = JOptionPane.showConfirmDialog(
                    null,
                    panneauDepot,
                    "Depot",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (resultat == JOptionPane.CLOSED_OPTION) {
                valide = true;
                break;
            }

            if (resultat == JOptionPane.OK_OPTION) {
                try {
                    String montant = panneauDepot.getMontant();

                    if (Double.parseDouble(montant) <= 0) {
                        throw new NumberFormatException("Montant invalide");
                    }

                    client.envoyer("DEPOT " + montant);
                    JOptionPane.showMessageDialog(
                            null,
                            "Le depot de " + montant + "$ a été effectué !",
                            "Depot Effectué",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    valide = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Entrez un nombre plus grand que 0 (N'oubliez pas le point pour les decimales)",
                            "Erreur de Depot",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                valide = true;
            }
        }
    }

    private void gererRetrait() {
        boolean valide = false;

        while (!valide) {
            PanneauDepotRetrait panneauDepot = new PanneauDepotRetrait("0", true);

            int resultat = JOptionPane.showConfirmDialog(
                    null,
                    panneauDepot,
                    "Retrait",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (resultat == JOptionPane.CLOSED_OPTION) {
                valide = true;
                break;
            }

            if (resultat == JOptionPane.OK_OPTION) {
                try {
                    String montant = panneauDepot.getMontant();

                    if (Double.parseDouble(montant) <= 0) {
                        throw new NumberFormatException("Montant invalide");
                    }

                    client.envoyer("RETRAIT " + montant);
                    JOptionPane.showMessageDialog(
                            null,
                            "Retrait de " + montant + "$ a été effectué !",
                            "Retrait Effectué",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    valide = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Entrez un nombre plus grand que 0 (N'oubliez pas le point pour les decimales)",
                            "Erreur de Retrait",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                valide = true;
            }
        }
    }

    private void gererTransfer() {
        PanneauTransfer panneauTransfer = new PanneauTransfer();

        int resultat = JOptionPane.showConfirmDialog(
                null,
                panneauTransfer,
                "Transfert",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultat == JOptionPane.OK_OPTION) {
            try {
                String montant = panneauTransfer.getMontant();
                String compteDest = panneauTransfer.getCompteDestinataire();

                if (Double.parseDouble(montant) <= 0 || compteDest.isEmpty()) {
                    throw new NumberFormatException("Montant invalide");
                }

                client.envoyer("TRANSFER " + montant + " " + compteDest);
                JOptionPane.showMessageDialog(
                        null,
                        "Transfert de " + montant + "$ vers le compte " + compteDest + " a été effectué !",
                        "Transfert Effectué",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Erreur : Entrez un nombre plus grand que 0 (N'oubliez pas le point pour les decimales) / Entrez un compte destinataire valide.",
                        "Erreur de Transfert",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void gererFacture() {
        PanneauFacture panneauFacture = new PanneauFacture();

        int resultat = JOptionPane.showConfirmDialog(
                null,
                panneauFacture,
                "Paiement Facture",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultat == JOptionPane.OK_OPTION) {
            try {
                String montant = panneauFacture.getMontant();
                String fournisseur = panneauFacture.getNumeroFacture();
                String description = panneauFacture.getDescription();

                if (Double.parseDouble(montant) <= 0 || fournisseur.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Montant invalide");
                }

                client.envoyer("FACTURE " + montant + " " + fournisseur + " " + description);

                JOptionPane.showMessageDialog(
                        null,
                        "Paiement de " + montant + "$  Fournisseur : " + fournisseur + " Pour : " + description + " a été effectué !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Erreur : Entrez un nombre plus grand que 0 (N'oubliez pas le point pour les decimales) / Entrez un fournisseur valide",
                        "Erreur de Paiement de Facture",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }



}
