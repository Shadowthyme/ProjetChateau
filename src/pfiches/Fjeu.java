/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pfiches;

import ptraitement.Jeu;
import ptraitement.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import ptraitement.Joueur;

/**
 *
 * @author hugor
 */
public class Fjeu extends javax.swing.JDialog {

    private JButton[][] tab = new JButton[13][7];
    private Jeu monJeu;
    private int[] selectedPiece = null; // Initialisé à null au départ
    protected String pseudo1;
    protected String pseudo2;
    protected boolean chargement;

    public Fjeu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        chargement = ((Faccueil) this.getParent()).getCBchargement();
        pseudo1 = ((Faccueil) this.getParent()).getPseudo1();
        pseudo2 = ((Faccueil) this.getParent()).getPseudo2();
        System.out.println(pseudo1 + " "+ pseudo2);
        initComponents();
        initialiser(pseudo1, pseudo2, chargement);
        afficherPlateau();
    }

    public void initialiser(String pseudo1, String pseudo2, boolean chargement) {
        char couleur1 = new Random().nextBoolean() ? 'N' : 'B';
        char couleur2 = couleur1 == 'N' ? 'B' : 'N';
        Joueur joueur1 = new Joueur(pseudo1, 7, couleur1);
        Joueur joueur2 = new Joueur(pseudo2, 7, couleur2);
        monJeu = new Jeu(joueur1, joueur2);
        monJeu.Generer_plateau();
        if (chargement == true) {
            monJeu.chargerPartie();
        }

        GridLayout layout = new GridLayout(13, 7);
        pPlateau.setLayout(layout);

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 7; j++) {
                JButton bouton = new JButton();
                bouton.setPreferredSize(new Dimension(50, 50));
                bouton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                bouton.setActionCommand(i + "," + j);
                bouton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(e);
                    }
                });
                pPlateau.add(bouton);
                tab[i][j] = bouton;
            }
        }
        CouleurPlateau();
        desactiverCoins();
        this.pack();
    }

    public void afficherPlateau() {
        Piece[][] plateau = monJeu.getPlateau(); // Récupère l'état actuel du plateau
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                // Remplir les boutons avec des images
                if (plateau[i][j] instanceof Case_Vide) {
                    tab[i][j].setIcon(null);
                }
                if (plateau[i][j] instanceof Pion) {
                    Pion pion = (Pion) plateau[i][j];
                    if (pion.getCouleur() == 'B') {
                        tab[i][j].setIcon(redimensionnerImage("src/pimages/pionB.png", 45, 45)); // Image pour un pion blanc
                    } else {
                        tab[i][j].setIcon(redimensionnerImage("src/pimages/pionN.png", 45, 45)); // Image pour un pion noir
                    }
                } else if (plateau[i][j] instanceof Cavalier) {
                    Cavalier cavalier = (Cavalier) plateau[i][j];
                    if (cavalier.getCouleur() == 'B') {
                        tab[i][j].setIcon(redimensionnerImage("src/pimages/CavalierB.png", 45, 45)); // Image pour un cavalier blanc
                    } else {
                        tab[i][j].setIcon(redimensionnerImage("src/pimages/CavalierN.png", 45, 45)); // Image pour un cavalier noir
                    }
                }
                tab[i][j].setText(""); // Efface tout texte sur le bouton
            }
        }
    }

    public void CouleurPlateau() {
        Color Blanc = new Color(245, 245, 220);
        Color Noir = new Color(180, 102, 67);
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if ((j + i) % 2 == 0) {
                    tab[i][j].setBackground(Blanc);
                } else {
                    tab[i][j].setBackground(Noir);
                }
            }
        }
    }

    public void desactiverCoins() {
        tab[0][0].setEnabled(false);
        tab[1][0].setEnabled(false);
        tab[2][0].setEnabled(false);
        tab[10][0].setEnabled(false);
        tab[11][0].setEnabled(false);
        tab[12][0].setEnabled(false);
        tab[0][1].setEnabled(false);
        tab[1][1].setEnabled(false);
        tab[11][1].setEnabled(false);
        tab[12][1].setEnabled(false);
        tab[0][2].setEnabled(false);
        tab[12][2].setEnabled(false);
        tab[0][6].setEnabled(false);
        tab[1][6].setEnabled(false);
        tab[2][6].setEnabled(false);
        tab[10][6].setEnabled(false);
        tab[11][6].setEnabled(false);
        tab[12][6].setEnabled(false);
        tab[0][5].setEnabled(false);
        tab[1][5].setEnabled(false);
        tab[11][5].setEnabled(false);
        tab[12][5].setEnabled(false);
        tab[0][4].setEnabled(false);
        tab[12][4].setEnabled(false);
    }

    private ImageIcon redimensionnerImage(String chemin, int largeur, int hauteur) {
        ImageIcon icon = new ImageIcon(chemin);
        Image img = icon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void handleButtonClick(ActionEvent e) {
        String[] coords = e.getActionCommand().split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        boolean CapturePossible = false;
        Piece[][] plateau = monJeu.getPlateau();

        if (selectedPiece == null || plateau[selectedPiece[0]][selectedPiece[1]].getCouleur() == plateau[x][y].getCouleur()) {
            // Première sélection : vérifier qu'une pièce du joueur actif est sélectionnée
            if (plateau[x][y] != null && plateau[x][y].getCouleur() == monJeu.getJoueurActif().getCouleur()) {
                if (selectedPiece != null) {
                    tab[selectedPiece[0]][selectedPiece[1]].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
                selectedPiece = new int[]{x, y};  // Définir les coordonnées de la pièce sélectionnée
                tab[x][y].setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Marquer la pièce sélectionnée
            }
        } else {
            // Deuxième clic : tenter de déplacer la pièce sélectionnée
            Piece selected = monJeu.getPlateau()[selectedPiece[0]][selectedPiece[1]];
            boolean moved = selected.deplacerPiece(monJeu.getPlateau(), x, y); // Effectuer le déplacement
            if (!moved) {
                moved = selected.capture(monJeu.getPlateau(), x, y);
                CapturePossible = selected.verifCapture(monJeu.getPlateau());
                if (CapturePossible == true) {
                    tab[selectedPiece[0]][selectedPiece[1]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    selectedPiece = new int[]{x, y};
                    tab[x][y].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    afficherPlateau();
                }
            }
            if (moved && CapturePossible == false) {
                monJeu.changerTour(); // Passer au joueur suivant
                afficherPlateau(); // Mettre à jour l'affichage du plateau
                Joueur Gagnant = monJeu.Victoire();
                if (Gagnant != null) {
                    JOptionPane.showMessageDialog(null, "Le joueur " + Gagnant.getPseudo() + " a Gagné!");
                }
            }

            // Réinitialiser la sélection
            if (CapturePossible == false) {
                tab[selectedPiece[0]][selectedPiece[1]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                selectedPiece = null; // Réinitialiser à null après l'action
            }
        }
    }
//Méthode générée par ChatGPT, quelques modification effectuées (capture & chaine de capture implémentées manuellement)

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pPlateau = new javax.swing.JPanel();
        bRetour2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pPlateau.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pPlateauLayout = new javax.swing.GroupLayout(pPlateau);
        pPlateau.setLayout(pPlateauLayout);
        pPlateauLayout.setHorizontalGroup(
            pPlateauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pPlateauLayout.setVerticalGroup(
            pPlateauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        bRetour2.setText("Retour");
        bRetour2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRetour2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(pPlateau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(bRetour2)
                .addGap(67, 67, 67))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(pPlateau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(bRetour2)))
                .addContainerGap(143, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bRetour2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRetour2ActionPerformed
        monJeu.sauvegarderPartie();
        this.setVisible(false);
        this.getParent().setVisible(true);
    }//GEN-LAST:event_bRetour2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fjeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fjeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fjeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fjeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Fjeu dialog = new Fjeu(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bRetour2;
    private javax.swing.JPanel pPlateau;
    // End of variables declaration//GEN-END:variables
}
