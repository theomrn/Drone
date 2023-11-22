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

    /**
     * accumule la phero recue par dillution dans la pheromone
     * (la phero recue par dillution est ensuite annulee)
     * puis degrade la pheromone par evaporation
     * et la met a zero si < au taux d'oubli
     */



    /**prise de nourriture par une fourmis
     * decremente le nb de nourriture
     * si le nb de nourriture tombe a zero, le type de la parcelle devient un terrain
     * */



    /**ajoute une dose de pheromone a la pacelle*/


    /**definit le type de la parcelle; si c'est une zone de nourriture, en ajoute 10 */
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