/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeudechateaux;

/**
 *
 * @author hugor
 */
public class Cavalier extends Piece{
    
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
}
