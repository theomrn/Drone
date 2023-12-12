package modele;

import gui.ImgParcelle;

/**
 * cette classe represente une parcelle de terrain
 * La parcelle peut etre de type herbe, nid, nourriture, et contenir de la pheromone
 *
 * @author Emmanuel Adam
 */
public class Parcelle {
    /**taux de dillution de la pheromone*/

    /**type de la parcelle*/
    TypeParcelle type;
    /**coordonnee x de la parcelle dans la grille*/
    final int x;
    /**coordonnee y de la parcelle dans la grille*/
    final int y;

    /**dose de nourriture*/
    /** reference a la grille des parcelles*/
    Parcelle[][] grille;
    /**representation graphique associee*/
    ImgParcelle img;

    /**
     * constructeur par defaut, inutilise
     */
    public Parcelle() { x = y = 0;}

    /**
     * constructeur initialisant la grille, les coordonnees et le type de la parcelle
     */
    public Parcelle(Parcelle[][] grille, int x, int y, TypeParcelle type) {
        this.x = x;
        this.y = y;
        this.grille = grille;
        this.type = type;
    }

    /**definit le type de la parcelle */
    public void setType(TypeParcelle type) {
        this.type = type;
        img.choisirCouleur();
    }


    public void setImg(ImgParcelle img) {
        this.img = img;
    }

    public TypeParcelle getType() { return type; }

    @Override
    public String toString() {
        return "Parcelle{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }


}