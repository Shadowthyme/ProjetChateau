package jeudechateaux;

import java.util.Scanner;
import java.util.Random;
import java.io.File;

public class JeudeChateaux {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Voulez-vous charger une partie existante ? (oui/non)");
        String choix = sc.nextLine();
        Jeu jeu;

        if (choix.equalsIgnoreCase("oui")) {
            File fichierSauvegarde = new File("partie.txt");
            if (fichierSauvegarde.exists()) {
                jeu = new Jeu(null, null, null);
                jeu.chargerPartie();
            } else {
                System.out.println("Aucune sauvegarde trouvee, demarrage d'une nouvelle partie.");
                jeu = creerNouvellePartie(sc);
            }
        } else {
            jeu = creerNouvellePartie(sc);
        }

        jeu.BoucleJeu();
    }

    private static Jeu creerNouvellePartie(Scanner sc) {
        System.out.println("Voulez-vous jouer entre amis (1) ou contre un ordinateur (2) ?");
        int rep = sc.nextInt();
        System.out.println("Donnez le pseudo du joueur 1");
        sc.nextLine(); // Consommer le retour à la ligne
        String pseudo1 = sc.nextLine();
        String pseudo2;
        char couleur2;

        if (rep == 1) {
            System.out.println("Donnez le pseudo du joueur 2");
            pseudo2 = sc.nextLine();
        } else {
            pseudo2 = "Ordinateur";
        }

        char couleur1 = new Random().nextBoolean() ? 'N' : 'B';
        couleur2 = (couleur1 == 'N') ? 'B' : 'N';

        Joueur Joueur1 = new Joueur(pseudo1, 0, couleur1);
        Joueur Joueur2 = new Joueur(pseudo2, 7, couleur2);
        Joueur Joueur_actif = (Joueur1.getCouleur() == 'B') ? Joueur1 : Joueur2;

        Jeu jeu = new Jeu(Joueur1, Joueur2, Joueur_actif);
        jeu.Generer_plateau();
        return jeu;
    }
}
