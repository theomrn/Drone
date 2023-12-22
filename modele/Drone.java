package modele;

import application.SimuDrone;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * cette classe represente un Drone
 *
 * @author Emmanuel Adam Morneau Théo Molin Quentin Pilot Lucas
 * même commentaire que pour les petits drones , la seul différence etant que ces drones ci peuvent passer au dessus des arbres 2
 */
public class Drone {
    // des couleurs
    static final public Color coulDrone = Color.color(1, 0, 0);

    /**coeff de degradation de la dose de pheromone entre chaque depot */

    /**
     * parcelle ou se trouve la fourmi
     */
    Parcelle parcelle;

    /**
     * direction actuelle de la fourmis
     */
    Direction d;


    /**
     * reference a l'environnement
     */
    Environnement evt;

    /**
     * sa representation graphique associee (simple cercle)
     */
    Circle circle;
    boolean desactiver = false;
    boolean invincibilite = false;

    public Drone(Environnement evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
    }

    void errer() {
        if (!desactiver) {
            if (!SimuDrone.isSpotted) {
                d = d.nextDirection();
                Parcelle prochaineParcelle = getParcelleDir(d);
                if (prochaineParcelle.getType() != TypeParcelle.Arbre) {
                    parcelle = prochaineParcelle;
                    evt.bougerDrone(this);
                }
            } else {
                int xdif = SimuDrone.coSpotted.get(0) - parcelle.x;
                int ydif = SimuDrone.coSpotted.get(1) - parcelle.y;
                if (xdif != 0) {
                    parcelle.x += xdif / Math.abs(xdif);
                }
                if (ydif != 0) {
                    parcelle.y += ydif / Math.abs(ydif);
                }
                evt.bougerDrone(this);
            }
        }
    }

    void detecterDronesDesactive() {
        int distanceDetection = 1;
        int dureeDesactivation = 2000;
        int dureeInvincibilite = 1500;

        for (Drone drone : evt.lesDrones) {
            int distanceX = Math.abs(parcelle.x - drone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - drone.parcelle.y);
            int distance = Math.max(distanceX, distanceY);
            if (distance <= distanceDetection && this != drone && !invincibilite && !desactiver) {
                desactiver = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(dureeDesactivation);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    desactiver = false;
                    invincibilite = true;
                    try {
                        Thread.sleep(dureeInvincibilite);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    invincibilite = false;


                }).start();
            }
        }
    }


    private Parcelle getParcelleDir(Direction dir) {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }


    public void setCircle(Circle circle) {
        this.circle = circle;
        circle.setFill(coulDrone);
        circle.setRadius(4.5);
    }
}