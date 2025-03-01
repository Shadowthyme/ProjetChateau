package jeudechateaux;

/**
 *
 * @author hugor
 */
public class Joueur {
    private String pseudo;
    private int nbr_piece;
    private char couleur;
    
    public Joueur(String pseudo, int nbr_piece, char couleur){
        this.pseudo = pseudo;
        this.nbr_piece = nbr_piece;
        this.couleur = couleur;
    }

    public char getCouleur() {
        return couleur;
    }
}
