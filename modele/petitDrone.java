package modele;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class petitDrone {
    static final public Color coulPetitDrone = Color.color(1,0.5,0.6);
    // des couleurs
    /**coeff de degradation de la dose de pheromone entre chaque depot */

    /**parcelle ou se trouve la fourmi*/
    static Parcelle parcelle;

    /**direction actuelle de la fourmis*/
    Direction d;

    /**reference a l'environnement*/
    Environnement evt;

    /**sa representation graphique associee (simple cercle)*/
    Circle circle;

    public petitDrone(Environnement  evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
    }

    void errer()
    {
        d = d.nextDirection();
        Parcelle prochaineParcelle = getParcelleDir(d);
        if (prochaineParcelle.getType() != TypeParcelle.Arbre && prochaineParcelle.getType() != TypeParcelle.Arbre2) {
            parcelle = prochaineParcelle;
            evt.bougerPetitDrone(this);
        }
    }


    private Parcelle getParcelleDir(Direction dir)
    {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }


    public void setCircle(Circle circle) {
        this.circle = circle;
        circle.setFill(coulPetitDrone);
        circle.setRadius(4);
    }
}
