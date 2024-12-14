package com.atoudeft.client;

import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;
import com.atoudeft.vue.PanneauHistorique;
import com.atoudeft.vue.PanneauPrincipal;
import com.programmes.MainFrame;

import javax.swing.*;

public class GestionnaireEvenementClient2 implements GestionnaireEvenement {
    private Client client;
    private PanneauPrincipal panneauPrincipal;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient2(Client client, PanneauPrincipal panneauPrincipal) {

        this.client = client;
        this.panneauPrincipal = panneauPrincipal;
        this.client.setGestionnaireEvenement(this);
    }
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        //Connexion cnx;
        String typeEvenement, arg, str;
        int i;
        String[] t;
        MainFrame fenetre;

        if (source instanceof Connexion) {
            //cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "END": //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    break;
                /******************* CREATION et CONNEXION *******************/
                case "HIST": //Le serveur a renvoyé
                    arg = evenement.getArgument();
                    if (arg.length() > 3) {
                        str = arg.substring(arg.indexOf("OK") + 2).trim();
                        System.out.print(str);
                        t = str.split("\n");
                        gererHistorique(t);
                    } else {
                        gererHistorique(new String[0]);
                    }
                    break;
                case "OK":
                    panneauPrincipal.setVisible(true);
                    fenetre = (MainFrame)panneauPrincipal.getTopLevelAncestor();
                    fenetre.setTitle(MainFrame.TITRE);//+" - Connecté"
                    break;
                case "NOUVEAU":
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Nouveau refusé");
                    }
                    else {
                        panneauPrincipal.cacherPanneauConnexion();
                        panneauPrincipal.montrerPanneauCompteClient();
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        panneauPrincipal.ajouterCompte(str);
                    }
                    break;
                case "CONNECT":
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Connexion refusée");
                    }
                    else {
                        panneauPrincipal.cacherPanneauConnexion();
                        panneauPrincipal.montrerPanneauCompteClient();
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        t = str.split(":");
                        for (String s:t) {
                            panneauPrincipal.ajouterCompte(s.substring(0,s.indexOf("]")+1));
                        }
                    }
                    break;
                /******************* SÉLECTION DE COMPTES *******************/
                case "EPARGNE" :
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Erreur lors de la création du compte épargne !");
                    }
                    else {
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        panneauPrincipal.ajouterCompte(str);
                    }
                    break;
                case "SELECT" :
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Erreur lors de la sélection du compte");
                    }else {
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        t = str.split(" ");
                        panneauPrincipal.getPanneauOperationsCompte().setLblSolde("Solde: " + t[1]);
                    }
                    break;

                /******************* OPÉRATIONS BANCAIRES *******************/
                case "DEPOT" :
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Erreur de depot");
                    }
                    else {
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        setListeSolde(str);
                    }
                    break;
                case "RETRAIT" :
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Erreur de retrait");
                    }
                    else {
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        setListeSolde(str);
                    }
                    break;
                case "FACTURE" :
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Erreur de paiement");
                    } else {
                        JOptionPane.showMessageDialog(panneauPrincipal, "Le paiement est effectué");
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        setListeSolde(str);
                    }
                    break;
                case "TRANSFER" :
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal,"Erreur de transfer");
                    } else {
                        JOptionPane.showMessageDialog(panneauPrincipal, "Le transfert est effectué");
                        str = arg.substring(arg.indexOf("OK")+2).trim();
                        setListeSolde(str);
                        System.out.println(str);
                    }
                    break;
                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default:
                    System.out.println("RECU : "+evenement.getType()+" "+evenement.getArgument());
            }
        }
    }

    private void gererHistorique(String[] liste) {

        boolean valeur = false;

        while (!valeur) {
            PanneauHistorique panneauHist = new PanneauHistorique(liste);

            int reponse = JOptionPane.showConfirmDialog(
                    null,
                    panneauHist,
                    "Historique",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (reponse == JOptionPane.CLOSED_OPTION) {
                valeur = true;
            }

            if (reponse == JOptionPane.OK_OPTION) {
                valeur = true;
            }
        }
    }

    private void setListeSolde(String solde) {
        JList<String> liste = panneauPrincipal.getListeNumeroCompte();
        int index = Math.abs(liste.getSelectedIndex());
        String[] tableauString = new String[liste.getModel().getSize()];

        for (int i = 0; i < liste.getModel().getSize(); i++) {
            tableauString[i] = liste.getModel().getElementAt(i);
        }
        tableauString[index] = tableauString[index].substring(0, tableauString[index].indexOf("]") + 1) + " " + solde;
        panneauPrincipal.getNumerosComptes().clear();
        for (String temp : tableauString) {
            panneauPrincipal.getNumerosComptes().addElement(temp);
        }

    }

}