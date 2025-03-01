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
            Plateau[i][j] = new Case_Vide(i, j);
            Plateau[i][6 - j] = new Case_Vide(i, 6 - j);
            Plateau[12 - i][j] = new Case_Vide(2 - i, j);
            Plateau[12 - i][6 - j] = new Case_Vide(12 - i, 6 - j);
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
                    Plateau[i][j] = new Case_Vide(i, j);
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
            System.out.println("Aucune pièce à cet endroit !");
            return false;
        }

        Piece piece = Plateau[x1][y1];

        // Vérifier si le déplacement est valide
        if (!mouvementValide(piece, x2, y2)) {
            System.out.println("Déplacement non valide !");
            return false;
        }

        // Déplacer la pièce
        Plateau[x2][y2] = piece;  
        Plateau[x1][y1] = new Case_Vide(x1, y1); // Mettre une case vide à la place de l’ancienne position
        piece.setCoordonnees(x2, y2); 

        return true;
}

    private boolean mouvementValide(Piece piece, int x2, int y2) {
    int x1 = piece.getCoordx(); // Position actuelle en ligne
    int y1 = piece.getCoordy(); // Position actuelle en colonne
    int dx = Math.abs(x2 - x1); // Distance horizontale du mouvement
    int dy = Math.abs(y2 - y1); // Distance verticale du mouvement

    // Vérifier si la case de destination est vide (ou contenir une pièce adverse si les règles le permettent)
    if (!(Plateau[x2][y2] instanceof Case_Vide)) {
        System.out.println("La case de destination n'est pas vide !");
        return false;
    }

    // Déplacement des pions
    if (piece instanceof Pion) {
        // Un pion peut se déplacer d'une case dans n'importe quelle direction
        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1) || (dx == 1 && dy == 1)) {
            return true;
        } else {
            System.out.println("Déplacement invalide pour un pion !");
            return false;
        }
    }

    // Déplacement des cavaliers
    if (piece instanceof Cavalier) {
        // Un cavalier se déplace en "L" : 2 cases dans une direction et 1 case dans l'autre
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            return true;
        } else {
            System.out.println("Déplacement invalide pour un cavalier !");
            return false;
        }
    }

    // Si ce n'est ni un pion ni un cavalier, on refuse le déplacement
    System.out.println("Type de pièce inconnu !");
    return false;
}
}
