package modele;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * cette classe represente une fourmis
 *
 * @author Emmanuel Adam
 */
public class Drone {
    // des couleurs
    static final public Color coulDrone = Color.color(1,0,0);

    /**coeff de degradation de la dose de pheromone entre chaque depot */

    /**parcelle ou se trouve la fourmi*/
    Parcelle parcelle;

    /**direction actuelle de la fourmis*/
    Direction d;

    /**reference a l'environnement*/
    Environnement evt;

    /**sa representation graphique associee (simple cercle)*/
    Circle circle;

    public Drone(Environnement  evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
    }

    void errer()
    {
        Direction nouvelleDirection = d.nextDirection();
        Parcelle prochaineParcelle = getParcelleDir(nouvelleDirection);
        int i=0;
        while ((prochaineParcelle.getType() == TypeParcelle.Arbre)&&(i<2)) {
            nouvelleDirection = d.nextDirection();
            prochaineParcelle = getParcelleDir(nouvelleDirection);
            i++;
        }
        if (i>1)
        {
            nouvelleDirection=d.inverse();
            prochaineParcelle= getParcelleDir(nouvelleDirection);
        }
        parcelle = prochaineParcelle;
        evt.bougerDrone(this);
    }


    private Parcelle getParcelleDir(Direction dir)
    {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }


    public void setCircle(Circle circle) {
        this.circle = circle;
        circle.setFill(coulDrone);
        circle.setRadius(4);
    }
}