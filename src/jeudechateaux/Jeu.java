package jeudechateaux;

import java.util.Scanner;

/**
 *
 * @author hugor
 */
public class Jeu {

    private Piece[][] Plateau;
    private Joueur Joueur1, Joueur2;
    private Joueur J_actif;
    private String nomFichier;

    public Jeu() {
        this.Plateau = new Piece[13][7];
        this.Joueur1 = new Joueur("Joueur1", 7, 'B');
        this.Joueur2 = new Joueur("Joueur2", 7, 'N');
        this.J_actif = Joueur1;
        this.nomFichier = "partie.txt";
    }

    public void Generer_plateau() {
        Plateau = new Piece[13][7]; // 13 lignes, 7 colonnes (A à G)

        // Placer les cases interdites ("X") aux coins en forme de château
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 - i; j++) {
                Plateau[i][j] = new Case_Vide(i, j, true);         // Coin haut gauche
                Plateau[i][6 - j] = new Case_Vide(i, 6 - j, true); // Coin haut droit
                Plateau[12 - i][j] = new Case_Vide(12 - i, j, true); // Coin bas gauche (corrigé)
                Plateau[12 - i][6 - j] = new Case_Vide(12 - i, 6 - j, true); // Coin bas droit
            }
        }

        // Placer les cavaliers (C)
        Plateau[3][2] = new Cavalier('N', 3, 2);
        Plateau[3][4] = new Cavalier('N', 3, 4); // Cavaliers noirs
        Plateau[9][2] = new Cavalier('B', 9, 2);
        Plateau[9][4] = new Cavalier('B', 9, 4); // Cavaliers blancs

        // Placer les pions (P)
        Plateau[4][1] = new Pion('N', 4, 1);
        Plateau[4][2] = new Pion('N', 4, 2);
        Plateau[4][3] = new Pion('N', 4, 3);
        Plateau[4][4] = new Pion('N', 4, 4);
        Plateau[4][5] = new Pion('N', 4, 5); // Pions noirs
        Plateau[8][1] = new Pion('B', 8, 1);
        Plateau[8][2] = new Pion('B', 8, 2);
        Plateau[8][3] = new Pion('B', 8, 3);
        Plateau[8][4] = new Pion('B', 8, 4);
        Plateau[8][5] = new Pion('B', 8, 5); // Pions blancs

        // Remplir le reste avec des cases vides normales (.)
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 7; j++) {
                if (Plateau[i][j] == null) {
                    Plateau[i][j] = new Case_Vide(i, j, false); // Case vide normale
                }
            }
        }
    }

    public void afficherPlateau() {
        System.out.println("   A  B  C  D  E  F  G"); // En-tête des colonnes
        for (int i = 0; i < Plateau.length; i++) {
            System.out.printf("%2d ", 13 - i); // Numérotation des lignes

            for (int j = 0; j < Plateau[i].length; j++) {
                if (Plateau[i][j] == null || Plateau[i][j] instanceof Case_Vide) {
                    System.out.print(".  "); // Afficher un point pour une case vide
                } else {
                    System.out.print(Plateau[i][j].getSymbole() + " "); // Afficher le symbole de la pièce
                }
            }
            System.out.println(); // Nouvelle ligne pour chaque rangée
        }
    }

    public void changerTour() {
        if (J_actif == Joueur1) {
            J_actif = Joueur2;
        } else {
            J_actif = Joueur1;
        }
    }

    public void scan_plateau() {
        int nbr_piece = 0;
        for (int i = 0; i < Plateau.length; i++) {
            for (int j = 0; j < Plateau[i].length; j++) {
                if (Plateau[i][j].getCouleur() == J_actif.getCouleur()) {
                    nbr_piece++;
                }
            }

        }
        J_actif.modif_nbr_piece(nbr_piece);
    }

    public char Victoire_Chateau() {
        char victoire = ' ';
        if (!(Plateau[0][3] instanceof Case_Vide) && Plateau[0][3].getCouleur() == 'B') {
            victoire = 'B';
        }
        if (!(Plateau[12][3] instanceof Case_Vide) && Plateau[12][3].getCouleur() == 'N') {
            victoire = 'N';
        }
        return victoire;
    }

    public char Victoire_platVide() {
        char victoire = ' ';
        scan_plateau();
        if (J_actif.getnbr_piece() == 0) {
            changerTour();
            victoire = J_actif.getCouleur();
        }
    return victoire;
    }

    public Joueur getJoueurActif() {
        return J_actif;
    }

    public Piece[][] getPlateau() {
        return Plateau;
    }

    public void BoucleJeu(){
        //faite pour tester, pas de sécuritées
        Victoire_platVide();
        Scanner sc = new Scanner(System.in);
        afficherPlateau();
        System.out.println("choisir piece");
        int x1 = sc.nextInt();
        int y1 = sc.nextInt();
        System.out.println("bouger(1) ou manger(2)? ou charge du cavalier(3)");
        int rep = sc.nextInt();
        if(rep==1){
            System.out.println("coord d'arrivee");
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            Plateau[x1][y1].deplacerPiece(Plateau,x2,y2);
            
            afficherPlateau();
        }
        if(rep == 2){
            System.out.println("coord d'arrivee");
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            boolean cap =Plateau[x1][y1].capture(Plateau,x2,y2);
            if (cap == true){
                Plateau[x2][y2].capture_chaine(Plateau);
            }
        }
        if(rep==3){
            Cavalier cavalier = (Cavalier)Plateau[x1][y1];
            cavalier.chargeCavalier(Plateau);
        }
        Victoire_Chateau();
    }
}
