
package jeudechateaux;

import java.util.Scanner;

public class JeudeChateaux {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jeu jeu = new Jeu();
        jeu.Generer_plateau();

        jeu.BoucleJeu();
        }
    }
