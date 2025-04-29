/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptraitement;

/**12345
 *
 * @author hugor
 */
public class Case_Vide extends Piece {
    private boolean interdite;

    public Case_Vide(int coordx, int coordy, boolean interdite) {
        super(' ', coordx, coordy);
        this.interdite = interdite;
    }

    public boolean estInterdite() {
        return interdite;
    }
    @Override
    boolean mouvementValide(Piece[][] Plateau, int x2, int y2) {
        // Une case vide n'est pas censée bouger, donc elle ne peut jamais être valide
        return false;
    }
}
