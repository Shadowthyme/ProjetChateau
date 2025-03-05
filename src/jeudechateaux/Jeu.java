package jeudechateaux;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Jeu {

    private Piece[][] Plateau;
    private Joueur Joueur1, Joueur2;
    private Joueur J_actif;
    private String nomFichier;

    public Jeu(Joueur Joueur1, Joueur Joueur2, Joueur Joueur_actif) {
        this.Plateau = new Piece[13][7];
        this.Joueur1 = Joueur1;
        this.Joueur2 = Joueur2;
        this.J_actif = Joueur_actif;
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
                if (Plateau[i][j] instanceof Case_Vide) {
                    Case_Vide CV = (Case_Vide) Plateau[i][j];
                    if (CV.estInterdite()) {
                        System.out.print("X  ");
                    } else {
                        System.out.print(".  "); // Afficher un point pour une case vide
                    }
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

    public void BoucleJeu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Afficher le plateau et les informations du joueur actif
            afficherPlateau();
            System.out.println("C'est au tour de " + J_actif.getPseudo() + " qui joue les pieces de couleur " + (J_actif.getCouleur() == 'B' ? "Blanches" : "Noires"));

            // Vérification de la condition de victoire après chaque tour
            char victoire1 = Victoire_Chateau();
            if (victoire1 != ' ') {
                System.out.println("Le joueur " + (victoire1 == 'B' ? "Blanc" : "Noir") + " a gagne ! Partie terminee.");
                break; // Sortir de la boucle si la condition de victoire est remplie
            }

            // Vérification si le joueur actif n'a plus de pièces, dans ce cas l'autre joueur gagne
            char victoire2 = Victoire_platVide();
            if (victoire2 != ' ') {
                System.out.println("Le joueur " + (victoire2 == 'B' ? "Blanc" : "Noir") + " a gagne ! Partie terminee.");
                break; // Sortir de la boucle si la condition de victoire est remplie
            }

            // Demander au joueur actif de faire son mouvement
            System.out.println("Choisissez la piece a deplacer (coordonnees x1 y1) :");
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            if (Plateau[x1][y1].getCouleur() != J_actif.getCouleur()) {
                System.out.println("Erreur, il ne s'agit pas de votre piece");
            } else {
                System.out.println("Choisir action : 1 - Deplacer, 2 - Capturer, 3 - Charger le cavalier, 4 - Sauvegarder");
                int action = sc.nextInt();

                // Actions en fonction de l'entrée de l'utilisateur
                if (action == 1) {
                    System.out.println("Entrer les coordonnees d'arrivee (x2 y2) :");
                    int x2 = sc.nextInt();
                    int y2 = sc.nextInt();
                    if (Plateau[x1][y1].deplacerPiece(Plateau, x2, y2) == false) {
                        while (Plateau[x1][y1].deplacerPiece(Plateau, x2, y2) == false) {
                            System.out.println("Entrer les coordonnees d'arrivee (x2 y2) :");
                            x2 = sc.nextInt();
                            y2 = sc.nextInt();
                        }
                    }
                } else if (action == 2) {
                    System.out.println("Entrer les coordonnees d'arrivee pour la capture (x2 y2) :");
                    int x2 = sc.nextInt();
                    int y2 = sc.nextInt();
                    boolean capture = Plateau[x1][y1].capture(Plateau, x2, y2);
                    if (capture == true) {
                        Plateau[x2][y2].capture_chaine(Plateau);
                    }
                } else if (action == 3) {
                    // Charger le cavalier
                    Cavalier cavalier = (Cavalier) Plateau[x1][y1];
                    cavalier.chargeCavalier(Plateau);
                }
                if (action == 4) {
                    sauvegarderPartie();
                    System.out.println("Partie sauvegardee !");
                    continue; // Retourne au début du tour sans changer de joueur
                }

                // Changer de tour après chaque action
                changerTour();
            }
        }
    }
    
    public void sauvegarderPartie() {
    try (FileWriter writer = new FileWriter(nomFichier)) {
        // Sauvegarde du plateau
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 7; j++) {
                Piece piece = Plateau[i][j];
                if (piece instanceof Case_Vide) {
                    writer.write(((Case_Vide) piece).estInterdite() ? "X " : ". ");
                } else {
                    writer.write(piece.getSymbole() + " ");
                }
            }
            writer.write("\n");
        }
        
        // Sauvegarde des informations des joueurs
        writer.write(Joueur1.getPseudo() + " " + Joueur1.getCouleur() + "\n");
        writer.write(Joueur2.getPseudo() + " " + Joueur2.getCouleur() + "\n");
        
        // Sauvegarde du joueur actif
        writer.write(J_actif.getPseudo() + "\n");

        System.out.println("Sauvegarde reussie !");
    } catch (IOException e) {
        System.out.println("Erreur lors de la sauvegarde.");
    }
}
    public void chargerPartie() {
    try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
        // Lecture du plateau
        for (int i = 0; i < 13; i++) {
            String[] ligne = reader.readLine().split(" ");
            for (int j = 0; j < 7; j++) {
                char symbole = ligne[j].charAt(0);
                if (symbole == 'X') {
                    Plateau[i][j] = new Case_Vide(i, j, true);
                } else if (symbole == '.') {
                    Plateau[i][j] = new Case_Vide(i, j, false);
                } else if (symbole == 'P' || symbole == 'C') {
                    char couleur = (i < 6) ? 'N' : 'B'; // Supposition (à affiner)
                    if (symbole == 'P') Plateau[i][j] = new Pion(couleur, i, j);
                    else Plateau[i][j] = new Cavalier(couleur, i, j);
                }
            }
        }

        // Lecture des joueurs
        String[] joueur1Data = reader.readLine().split(" ");
        Joueur1 = new Joueur(joueur1Data[0], 0, joueur1Data[1].charAt(0));

        String[] joueur2Data = reader.readLine().split(" ");
        Joueur2 = new Joueur(joueur2Data[0], 7, joueur2Data[1].charAt(0));

        // Lecture du joueur actif
        String pseudoActif = reader.readLine();
        J_actif = pseudoActif.equals(Joueur1.getPseudo()) ? Joueur1 : Joueur2;

        System.out.println("Partie chargee !");
    } catch (IOException e) {
        System.out.println("Erreur lors du chargement de la partie.");
    }
}
}
