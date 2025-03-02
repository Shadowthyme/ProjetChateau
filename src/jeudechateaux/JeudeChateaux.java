
package jeudechateaux;

import java.util.Scanner;

public class JeudeChateaux {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jeu jeu = new Jeu();
        jeu.Generer_plateau();

        while (true) { // Boucle infinie (on ajoutera une condition de fin plus tard)
            jeu.afficherPlateau();
            System.out.println(jeu.getJoueurActif().getPseudo() + ", c'est votre tour !");
            System.out.println("Entrez les coordonnees de la piece a deplacer (x1 y1) et la destination (x2 y2) : ");

            int x1 = scanner.nextInt();
            int y1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int y2 = scanner.nextInt();

            if (jeu.deplacerPiece(x1, y1, x2, y2)) {
                System.out.println("Deplacement reussi !");
            } else {
                System.out.println("Deplacement invalide");
            }

            // Ajout futur : condition de victoire pour sortir de la boucle
        }
    }
}
