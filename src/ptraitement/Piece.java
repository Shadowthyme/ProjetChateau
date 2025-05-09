package ptraitement;

import java.awt.Toolkit;
import java.util.Scanner;

/**
 *
 * @author hugor
 */
public abstract class Piece {

    private char couleur;
    protected int coordx, coordy;
    public static final String DOSS_IMAGES = "src/pimages/";

    public Piece(char couleur, int coordx, int coordy) {
        this.couleur = couleur;
        this.coordx = coordx;
        this.coordy = coordy;
    }

    public char getCouleur() { //méthode pour savoir de quelle couleur est une pièce (Noir ou Blanc)
        return this.couleur;
    }

    void setCoordonnees(int x2, int y2) {
        this.coordx = x2;
        this.coordy = y2;
    }

    int getCoordx() {
        return this.coordx;
    }

    int getCoordy() {
        return this.coordy;
    }

    public String getSymbole() {
        if (this instanceof Pion) {
            return (this.getCouleur() == 'N') ? "PN" : "PB";
        } else if (this instanceof Cavalier) {
            return (this.getCouleur() == 'N') ? "CN" : "CB";
        } else {
            return ".";
        }
    }

    public boolean deplacerPiece(Piece[][] Plateau, int x2, int y2) {
        // Vérifier si le déplacement est valide
        if (Plateau[x2][y2] instanceof Case_Vide) {
            Case_Vide CV = (Case_Vide) Plateau[x2][y2];
            if (CV.estInterdite()) {
                return false;
            }
            if (!mouvementValide(Plateau, x2, y2)) {
                return false;
            } else {
                Plateau[x2][y2] = Plateau[coordx][coordy];
                Plateau[coordx][coordy] = new Case_Vide(coordx, coordy, false);
                coordx = x2;
                coordy = y2;
            }
        }
        return true;
    }

    public boolean verifCapture(Piece[][] Plateau) {
        // Vérifie qu'il existe au moins UNE capture possible à effectuer
        boolean capturePossible = false;

        // Directions possibles pour les mouvements (haut, bas, gauche, droite, et diagonales)
        int[][] directions = {
            {-1, -1},{-1, 0},{-1, 1},{0, -1},{0, 1},{1, -1},{1, 0},{1, 1}
        };

        for (int[] direction : directions) {
            int x1 = coordx + direction[0]; // Coordonnées de la pièce adverse
            int y1 = coordy + direction[1];
            int x2 = coordx + 2 * direction[0]; // Coordonnées de la case derrière
            int y2 = coordy + 2 * direction[1];

            // Vérifier si les coordonnées sont dans les limites du tableau
            if (x1 >= 0 && x1 < Plateau.length && y1 >= 0 && y1 < Plateau[0].length
                    && x2 >= 0 && x2 < Plateau.length && y2 >= 0 && y2 < Plateau[0].length) {

                // Vérifier la présence d'une pièce ennemie à proximité
                if ((Plateau[x1][y1] instanceof Pion || Plateau[x1][y1] instanceof Cavalier)
                        && Plateau[x1][y1].getCouleur() != couleur) {

                    // Vérifier que la case derrière l'ennemi est vide
                    if (Plateau[x2][y2] instanceof Case_Vide) {
                        capturePossible = true;
                        break; // Une capture possible suffit
                    }
                }
            }
        }

        return capturePossible;
    }

    public boolean capture(Piece[][] Plateau, int x2, int y2) {
        int dx = Math.abs(x2 - coordx);
        int dy = Math.abs(y2 - coordy);
        boolean capture = false;

        // Capture (saut par-dessus une pièce adverse)
        if ((dx == 2 && dy == 0) || (dx == 0 && dy == 2) || (dx == 2 && dy == 2)) {
            int x_middle = (coordx + x2) / 2;
            int y_middle = (coordy + y2) / 2;
            if (Plateau[x_middle][y_middle] != null && !(Plateau[x_middle][y_middle] instanceof Case_Vide)) {
                Piece cible = Plateau[x_middle][y_middle];
                if (cible.getCouleur() != this.getCouleur() && Plateau[x2][y2] instanceof Case_Vide) {
                    System.out.println("Capture effectuee !");
                    Plateau[x2][y2] = Plateau[coordx][coordy];
                    Plateau[coordx][coordy] = new Case_Vide(coordx, coordy, false);
                    coordx = x2;
                    coordy = y2;
                    capture = true;
                    Plateau[x_middle][y_middle] = new Case_Vide(x_middle, y_middle, false);
                }
            }
        }

        return capture;
    }

    public boolean capture_chaine(Piece[][] Plateau) {
        Scanner sc = new Scanner(System.in);
        boolean fin = false;
        while (fin == false) {
            System.out.println("Voulez vous capturer a nouveau?");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            int rep = sc.nextInt();
            if (rep == 1) {
                afficherPlateau(Plateau);
                System.out.println("Ecrire les coordonnées d'arrivée ex: 5 4");
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                capture(Plateau, x2, y2);
            }
            if (rep == 2) {
                fin = true;
                afficherPlateau(Plateau);
            } else {
                System.out.println("Erreur, saisir a nouveau votre choix");
            }
        }
        return fin;
    }

    abstract boolean mouvementValide(Piece[][] Plateau, int x2, int y2);

    // Méthode commune pour le mouvement du Roi
    protected boolean mouvementCommeRoi(int x2, int y2) {
        int dx = Math.abs(x2 - coordx);
        int dy = Math.abs(y2 - coordy);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1) || (dx == 1 && dy == 1);
    }

    public void afficherPlateau(Piece[][] Plateau) {
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
}
