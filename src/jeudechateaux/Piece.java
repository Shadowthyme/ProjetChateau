package jeudechateaux;

/**
 *
 * @author hugor
 */
public class Piece {
    private char couleur;
    private int coordx, coordy;
    
    public Piece(char couleur, int coordx, int coordy){
        this.couleur = couleur;
        this.coordx = coordx;
        this.coordy = coordy;
    }

    public char getCouleur() { //méthode pour savoir de quelle couleur est une pièce (Noir ou Blanc)
        return this.couleur;
    }

    void setCoordonnees(int x2, int y2) {
        this.coordx = x2;
        this.coordy = y2;
    }

    int getCoordx() {
        return this.coordx;
    }

    int getCoordy() {
         return this.coordy;
    }

    public String getSymbole() {
        if (this instanceof Pion) {
            return (this.getCouleur() == 'N') ? "PN" : "PB";
        } else if (this instanceof Cavalier) {
            return (this.getCouleur() == 'N') ? "CN" : "CB";
        } else {
            return ".";
        }
}

}
