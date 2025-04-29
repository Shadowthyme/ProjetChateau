package ptraitement;

import java.util.Scanner;
import java.util.Random;

public class JeudeChateaux {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Voulez vous jouez entre ami (1) ou contre un ordinateur (2)");
        int rep = sc.nextInt();
        System.out.println("Donnez le pseudo du joueur 1");
        sc.nextLine();
        String pseudo1 = sc.nextLine();
        String pseudo2 = " ";
        char couleur2 = ' ';
        if (rep == 1) {
            System.out.println("Donnez le pseudo du joueur 2");
            pseudo2 = sc.nextLine();
        } else {
            pseudo2 = "Ordinateur";
        }
        char couleur1 = new Random().nextBoolean() ? 'N' : 'B';
        if (couleur1 == 'N') {
            couleur2 = 'B';
        } else {
            couleur2 = 'N';
        }
        Joueur Joueur1 = new Joueur(pseudo1, 0, couleur1);
        Joueur Joueur2 = new Joueur(pseudo2, 7, couleur2);
        Joueur Joueur_actif;
        if (Joueur1.getCouleur() == 'B') {
            Joueur_actif = Joueur1;
        } else {
            Joueur_actif = Joueur2;
        }

        Jeu jeu = new Jeu(Joueur1, Joueur2, Joueur_actif);
        jeu.Generer_plateau();

        jeu.BoucleJeu();
    }
}
