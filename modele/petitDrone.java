package modele;

import application.SimuDrone;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Morneau Théo Molin Quentin Pillot Lucas

public class petitDrone {
    // Petit drones , ils ne peuvent pas passer sur les arbres 2 ( les petits arbres)
    // Couleurs des petits drones
    static final public Color coulPetitDrone = Color.color(1, 0.5, 1);
    Parcelle parcelle;
    // direction dans laquelle se deplace le drone
    Direction d;
    /**
     * reference a l'environnement
     */
    Environnement evt;
    /**
     * sa representation graphique associee (simple cercle)
     */
    Circle circle;
    // boulean qui permet de desactiver les petits drones
    boolean desactiver = false;
    // boulean qui permet d'avoir une periode d'invincibilite pour eviter que les drones quand ils se foncent dessus reste bloquer pour toujours
    boolean invincibilite = false;

    public petitDrone(Environnement evt, int x, int y) {
        // constructeur
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
    }

    void errer() {
        // fonction qui fait bouger les petits drones
        // si le drone n'est pas desactiver il bouge aleatoirement
        if (!desactiver) {
            if (!SimuDrone.isSpotted) {
                d = d.nextDirection();
                Parcelle prochaineParcelle = getParcelleDir(d);
                if (prochaineParcelle.getType() != TypeParcelle.Arbre && prochaineParcelle.getType() != TypeParcelle.Arbre2) {
                    parcelle = prochaineParcelle;
                    evt.bougerPetitDrone(this);

                }
            } else {
                // si l'intrus est spotted , les drones se dirige vers la dernière position connu de l'intrus
                int xdif = SimuDrone.coSpotted.get(0) - parcelle.x;
                int ydif = SimuDrone.coSpotted.get(1) - parcelle.y;
                if (xdif != 0) {
                    parcelle.x += xdif / Math.abs(xdif);
                }
                if (ydif != 0) {
                    parcelle.y += ydif / Math.abs(ydif);
                }
                evt.bougerPetitDrone(this);
            }
        }
    }

    void detecterpetitDronesDesactive() {
        // fonction qui detecte les petits drones , si deux drones sont a moins d'une case il se desactive pendant 2 secondes , puis ont une periode d'invisibilité de 1,5 seconde
        int distanceDetection = 1;
        int dureeDesactivation = 2000;
        int dureeInvincibilite = 1500;

        for (petitDrone petitDrone : evt.lesPetitDrones) {
            int distanceX = Math.abs(parcelle.x - petitDrone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - petitDrone.parcelle.y);
            int distance = Math.max(distanceX, distanceY);
            if (distance <= distanceDetection && this != petitDrone && !invincibilite && !desactiver) {
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
        // fonction qui renvoie la prochaine parcelle en fonction de la direction
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }


    public void setCircle(Circle circle) {
        /**
         * parametre le cercle
         */
        this.circle = circle;
        circle.setFill(coulPetitDrone);
        circle.setRadius(3);
    }
}
