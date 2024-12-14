package com.atoudeft.controleur;

import com.atoudeft.client.Client;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurListeComptes extends MouseAdapter {

    private Client client;
    public EcouteurListeComptes(Client client) {
        this.client = client;
    }

    public static int index;
    public static String[] tableauString;

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            JList<String> liste = (JList<String>) evt.getSource();

            String compteSelectionne = liste.getSelectedValue();

            index = liste.getSelectedIndex();
            tableauString = new String[liste.getModel().getSize()];

            for (int i = 0; i < liste.getModel().getSize(); i++) {
                tableauString[i] = liste.getModel().getElementAt(i);
            }



            compteSelectionne = compteSelectionne.substring(compteSelectionne.indexOf("[") + 1);
            compteSelectionne = compteSelectionne.substring(0,compteSelectionne.indexOf("]"));
            compteSelectionne = compteSelectionne.toLowerCase();

            if (compteSelectionne != null) {
                client.envoyer("SELECT " + compteSelectionne);
            }
        }
    }
}
