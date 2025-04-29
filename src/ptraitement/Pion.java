/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptraitement;

/**
 *
 * @author hugor
 */
    class Pion extends Piece {

    public Pion(char couleur, int coordx, int coordy) {
        super(couleur, coordx, coordy);
    }
    @Override
    boolean mouvementValide(Piece[][] Plateau, int x2, int y2) {
        return mouvementCommeRoi(x2, y2);
    }
}
