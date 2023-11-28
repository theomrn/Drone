package gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modele.Parcelle;
import modele.TypeParcelle;

/**image d'une parcelle de terrain
 * une image contient un carre representant sa nature (terrain, nid, nourriture)
 * et un carre blanc le recouvrant representant la dose de pheromone
 * le carre de pheromone est plus ou moins opaque selon la dose de pheromone
 *
 * @author emmanueladam */
public class ImgParcelle  {
    // des couleurs
    static final public Color coulFond = Color.color(0.349, 0.325, 0.349);
    static final public Color coulArbre = Color.color(0,1,0);
    static final public Color coulSortie = Color.color(0.047, 0.302, 0.471);



    /**parcelle liee a cette image*/
    private Parcelle parcelle;
    /**carre liee au type*/
    private Rectangle elt;

    /**
     * creation d'une image de parcelle
     * le carre elt porte la couleur du type de la parcelle
     * le carre phero est plus ou moins opaque selon la dose de pheromone
     * initialement ce carre est transparent (opacity = 0)
     * @param troupe troupe des elements graphiques a laquelle doivent se raccrocher elt et phero
     * @param parcelle parcelle liee a cette image
     * @param x position x en pixel
     * @param y position y en pixel
     * @param dim taille de la case en pixel
     * */
    public ImgParcelle(Group troupe, Parcelle parcelle, int x, int y, int dim) {
        this.parcelle = parcelle;
        elt = new Rectangle(x,y,dim, dim);
        troupe.getChildren().add(elt);
    }

    /**choisit la couleur de l'elt selon la nature de la parcelle */
    public void choisirCouleur()
    {
        switch (parcelle.getType())
        {
            case Terrain -> elt.setFill(coulFond);
            case Arbre -> elt.setFill(coulArbre);
            case Sortie -> elt.setFill(coulSortie);
        }
//        elt.setOpacity(parcelle.getOdeurNid());
    }

    public Parcelle getParcelle() { return parcelle; }
}
