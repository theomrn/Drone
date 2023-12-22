package gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modele.Parcelle;
import modele.TypeParcelle;

/**image d'une parcelle de terrain
 * une image contient un carre representant sa nature (terrain, arbre, arbre2, sortie, tresor)
 * @author emmanueladam morneau Théo Molin Quentin Pillot Lucas*/

public class ImgParcelle  {
    // couleurs des types de parcelles différents
    static final public Color coulFond = Color.color(0.349, 0.325, 0.349);
    static final public Color coulArbre = Color.color(0,1,0);
    static final public Color coulSortie = Color.color(0.047, 0.302, 0.471);
    static final public Color coulTresor = Color.color(1,1,0.012);
    static final public Color coulArbre2 = Color.color(0.1,0.6,0.1);


    /**parcelle liee a cette image*/
    private Parcelle parcelle;
    /**carre liee au type*/
    private Rectangle elt;
    private Rectangle overlay;

    /** ajuste l'opacité de l'overlay */
    public void ajusterOpaciteOverlay(double nouvelleOpacite) {
        overlay.setOpacity(nouvelleOpacite);
    }

    /**
     * creation d'une image de parcelle
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
        // Ajouter l'overlay noir
        overlay = new Rectangle(x, y, dim, dim);
        overlay.setFill(Color.BLACK);
        troupe.getChildren().add(overlay);
    }

    public ImgParcelle getImg() {
        return this;
    }
    // Méthode pour ajuster l'opacité de l'overlay

    /**choisit la couleur de l'elt selon la nature de la parcelle */
    public void choisirCouleur()
    {
        switch (parcelle.getType())
        {
            case Terrain -> elt.setFill(coulFond);
            case Arbre -> elt.setFill(coulArbre);
            case Sortie -> elt.setFill(coulSortie);
            case Tresor -> elt.setFill(coulTresor);
            case Arbre2 -> elt.setFill(coulArbre2);
        }
    }
}
