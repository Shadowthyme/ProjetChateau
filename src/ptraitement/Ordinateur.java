/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptraitement;

/**
 *
 * @author hugor
 */
public class Ordinateur extends Joueur{
    
    private Piece[] liste_Piece;
    public Ordinateur(String pseudo, int nbr_piece, char couleur,Piece[] liste_Piece) {
        super(pseudo, nbr_piece, couleur);
        this.liste_Piece = liste_Piece;
    }
    
}
