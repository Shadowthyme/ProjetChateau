/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeudechateaux;

import java.util.Scanner;

/**
 *
 * @author hugor
 */
public class Cavalier extends Piece {

    public Cavalier(char couleur, int coordx, int coordy) {
        super(couleur, coordx, coordy);
    }

    @Override
    boolean mouvementValide(Piece[][] Plateau, int x2, int y2) {
        int dx = Math.abs(x2 - coordx);
        int dy = Math.abs(y2 - coordy);

        // Déplacement du Cavalier : en "L" ou comme un Roi
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2) || mouvementCommeRoi(x2, y2);
    }

    void chargeCavalier(Piece[][] Plateau) {
        Scanner sc = new Scanner(System.in);
        boolean capture = false;
        while (capture == false) {
            System.out.println("Que souhaitez vous faire?");
            System.out.println("1 - Vous deplacer");
            System.out.println("2 - Capturer une piece (Attention! plus de déplacement possible après cette action)");
            int rep = sc.nextInt();
            if (rep == 1) {
                System.out.println("Ecrire les coordonnées d'arrivée ex: 5 4");
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                boolean dep = deplacerPiece(Plateau, x2, y2);
                if (dep == false) {
                    System.out.println("Erreur, veuillez réessayer");
                } else {
                    System.out.println("Déplacement effectué");
                }

            }
            if (rep == 2) {
                System.out.println("entrez les coordonnées de la pièce après le saut ex: 5 3  --> 5 5");
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                capture = capture(Plateau, x2,y2);
                if(capture == false){
                    System.out.println("Erreur, veuillez reessayer");
                }
                else{
                    capture_chaine(Plateau);
                }
            }
        }
    }
}
