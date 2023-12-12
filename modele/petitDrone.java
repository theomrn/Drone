package modele;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class petitDrone {
    static final public Color coulDrone = Color.color(1,0.5,0.6);

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

    public petitDrone(Environnement evt,int x, int y) {
        this.evt = evt;
        this.x = x;
        this.y = y;
    }

    void errer()
    {
        if (!intrusTrouvé) {
            d = d.nextDirection();
            Parcelle prochaineParcelle = getParcelleDir(d);
            if (prochaineParcelle.getType() != TypeParcelle.Arbre && prochaineParcelle.getType() != TypeParcelle.Arbre2){
                evt.bougerPetitDrone(this);
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
