/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeudechateaux;

/**
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
}
