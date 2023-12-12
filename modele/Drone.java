package modele;

import javafx.application.Platform;
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
    boolean intrusTrouvé = false;
    int x ;
    int y;

    public Drone(Environnement  evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
    }

    void errer()
    {
        if (!intrusTrouvé) {
            d = d.nextDirection();
            Parcelle prochaineParcelle = getParcelleDir(d);
            if (prochaineParcelle.getType() != TypeParcelle.Arbre){
                evt.bougerDrone(this);
                parcelle = getParcelleDir(d);
            }

            // detecterIntrusProches();
        }
        else {
            //cheminVersIntrus();
        }
    }
/*
    void cheminVersIntrus() {
        d = d.nextDirection();
        parcelle = getParcelleDir(d);
        //if (!(distance(getParcelleDir(d, intrus) < distance(getParcelleDir(d, drone)))) {
            d = d.nextDirection();
            parcelle = getParcelleDir(d);
        }
        evt.bougerDrone(this);
    }
*/
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

    private void detecterIntrusProches() {
        int distanceDetection = 3;

        for (Drone drone : evt.lesDrones) {
            int distanceX = Math.abs(parcelle.x - drone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - drone.parcelle.y);

            int distance = Math.max(distanceX, distanceY);

            if (distance < distanceDetection) {
                intrusTrouvé = true;
                x = drone.parcelle.x;
                y = drone.parcelle.y;
            }
        }
    }
}