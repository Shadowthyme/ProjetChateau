package jeudechateaux;

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
        this.Joueur1 = new Joueur("Joueur1", 5, 'B');
        this.Joueur2 = new Joueur("Joueur2", 5, 'N');
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
    public boolean deplacerPiece(int x1, int y1, int x2, int y2) {
    // Vérifier si la case d'origine contient une pièce
    if (Plateau[x1][y1] == null || Plateau[x1][y1] instanceof Case_Vide) {
        System.out.println("Aucune piece à cet endroit !");
        return false;
    }

    Piece piece = Plateau[x1][y1];

    // Vérifier si la pièce appartient au joueur actif
    if (piece.getCouleur() != J_actif.getCouleur()) {
        System.out.println("Ce n'est pas votre piece !");
        return false;
    }

    // Vérifier si le déplacement est valide
    if (!mouvementValide(piece, x2, y2)) {
        return false;
    }

    // Vérifier si la case de destination est interdite
    if (Plateau[x2][y2] instanceof Case_Vide && ((Case_Vide) Plateau[x2][y2]).estInterdite()) {
        System.out.println("Deplacement interdit vers une case non autorisee !");
        return false;
    }

    // Vérifier si une pièce adverse est présente
    if (Plateau[x2][y2] != null && !(Plateau[x2][y2] instanceof Case_Vide)) {
        Piece cible = Plateau[x2][y2];
        if (cible.getCouleur() == piece.getCouleur()) {
            System.out.println("Vous ne pouvez pas capturer votre propre piece !");
            return false;
        }
        System.out.println("Piece capturee : " + cible.getClass().getSimpleName());
    }

    // Déplacer la pièce (capture ou déplacement simple)
    Plateau[x2][y2] = piece;
    piece.setCoordonnees(x2, y2);

    // Vérifier si la case d'origine était interdite avant de la vider
    boolean etaitInterdite = (Plateau[x1][y1] instanceof Case_Vide) && ((Case_Vide) Plateau[x1][y1]).estInterdite();
    Plateau[x1][y1] = new Case_Vide(x1, y1, etaitInterdite);

    // Changer de tour après un déplacement réussi
    changerTour();

    return true;
}

    private boolean mouvementValide(Piece piece, int x2, int y2) {
    int x1 = piece.getCoordx(); // Position actuelle en ligne
    int y1 = piece.getCoordy(); // Position actuelle en colonne
    int dx = Math.abs(x2 - x1); // Distance en ligne
    int dy = Math.abs(y2 - y1); // Distance en colonne

    // Vérifier si la case contient une pièce adverse ou est vide
    if (Plateau[x2][y2] != null && !(Plateau[x2][y2] instanceof Case_Vide)) {
        Piece cible = Plateau[x2][y2];
        if (cible.getCouleur() == piece.getCouleur()) {
            System.out.println("Vous ne pouvez pas capturer votre propre piece !");
            return false;
        } else {
            System.out.println("Capture autorisee de " + cible.getClass().getSimpleName() + " en " + x2 + "," + y2);
        }
    }

    // Déplacement des pions (1 case dans toutes les directions)
    if (piece instanceof Pion) {
        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1) || (dx == 1 && dy == 1)) {
            return true;
        } else {
            System.out.println("Deplacement invalide pour un pion !");
            return false;
        }
    }

    // Déplacement des cavaliers (1 case partout OU en L)
    if (piece instanceof Cavalier) {
        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1) || (dx == 1 && dy == 1) || // 1 case dans toutes les directions
            (dx == 2 && dy == 1) || (dx == 1 && dy == 2)) { // Mouvement en L
            return true;
        } else {
            System.out.println("Deplacement invalide pour un cavalier !");
            return false;
        }
    }

    // Si ce n'est ni un pion ni un cavalier, refuser le mouvement
    System.out.println("Type de piece inconnu !");
    return false;
}
    
    public void changerTour() {
        if (J_actif == Joueur1) {
            J_actif = Joueur2;
        } else {
            J_actif = Joueur1;
        }
}
    public Joueur getJoueurActif() {
        return J_actif;
}
}
