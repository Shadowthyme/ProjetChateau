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

    public Jeu(Piece[][] Plateau, Joueur Joueur1, Joueur Joueur2, Joueur J_actif, String nomFichier) {
        this.Plateau = Plateau;
        this.Joueur1 = Joueur1;
        this.Joueur2 = Joueur2;
        this.J_actif = J_actif;
        this.nomFichier = nomFichier + ".txt";
    }

    public void Generer_plateau() {
        Plateau = new Piece[13][7]; // 13 lignes, 7 colonnes (A à G)

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 7; j++) {
                Plateau[i][j] = null;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 - i; j++) {
                Plateau[i][j] = new Case_Vide('X', i, j);
                Plateau[i][Plateau[i].length - j] = new Case_Vide('X', i, j);
                Plateau[Plateau.length - i][j] = new Case_Vide('X', i, j);
                Plateau[Plateau.length - i][Plateau[i].length - j] = new Case_Vide('X', i, j);
            }
        }
        // Placer les cavaliers (C)
        Plateau[1][2] = new Cavalier('N', 1, 2);
        Plateau[1][4] = new Cavalier('N', 1, 4); // Cavaliers noirs
        Plateau[11][2] = new Cavalier('B', 11, 2);
        Plateau[11][4] = new Cavalier('B', 11, 4); // Cavaliers blancs

        // Placer les pions (P)
        Plateau[2][1] = new Pion('N', 2, 1);
        Plateau[2][2] = new Pion('N', 2, 2);
        Plateau[2][3] = new Pion('N', 2, 3);
        Plateau[2][4] = new Pion('N', 2, 4);
        Plateau[2][5] = new Pion('N', 2, 5); // Pions noirs
        Plateau[10][1] = new Pion('B', 10, 1);
        Plateau[10][2] = new Pion('B', 10, 2);
        Plateau[10][3] = new Pion('B', 10, 3);
        Plateau[10][4] = new Pion('B', 10, 4);
        Plateau[10][5] = new Pion('B', 10, 5); // Pions blancs
    }

    public void afficherPlateau() {
        System.out.println("   A B C D E F G");
        for (int i = 0; i < Plateau.length; i++) {
            System.out.printf("%2d ", 13 - i); // Affichage des numéros de lignes
            for (int j = 0; j < Plateau[i].length; j++) {
                if (Plateau[i][j] == null) {
                    System.out.print(".");
                } else if (Plateau[i][j] instanceof Pion == true) {
                    System.out.print("P");
                } else if (Plateau[i][j] instanceof Cavalier == true) {
                    System.out.print("C");
                } else if (Plateau[i][j] instanceof Case_Vide == true) {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }
}
