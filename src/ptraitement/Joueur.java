package ptraitement;

/**
 *
 * @author hugor
 */
public class Joueur {

    private String pseudo;
    private int nbr_piece;
    private char couleur;

    public Joueur(String pseudo, int nbr_piece, char couleur) {
        this.pseudo = pseudo;
        this.nbr_piece = nbr_piece;
        this.couleur = couleur;
    }

    public void modif_nbr_piece(int new_val) {
        nbr_piece = new_val;
    }

    public char getCouleur() {
        return couleur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getnbr_piece() {
        return nbr_piece;
    }
}
