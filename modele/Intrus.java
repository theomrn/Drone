package modele;

import application.SimuDrone;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Set;

public class Intrus {
    Environnement evt;
    Direction d;
    public Circle circle;
    public int revelationRadius;
    Parcelle parcelle;
    private int taille;
    public Parcelle[][] cases;
    private Set<Parcelle> obstaclesMemorises;
    private int champPerceptionRayon;
    static final public Color coulIntrus = Color.color(0,0,1);
    private double vitesse;
    boolean tresorTrouve = false;

    public Intrus(Environnement evt, int x, int y) {
        this.evt = evt;
        taille = evt.taille;
        cases = evt.getGrille();
        // Modification ici pour positionner l'intrus près d'un bord
        if (x == 0 || y == 0 || x == evt.taille - 1 || y == evt.taille - 1) {
            this.parcelle = evt.getGrille()[x][y];
        } else {
            // Si les coordonnées fournies ne sont pas déjà sur un bord, nous choisissons aléatoirement un bord
            int randomBorder = (int) (Math.random() * 4);
            switch (randomBorder) {
                case 0: // Bord supérieur
                    this.parcelle = evt.getGrille()[0][y];
                    break;
                case 1: // Bord droit
                    this.parcelle = evt.getGrille()[x][evt.taille - 1];
                    break;
                case 2: // Bord inférieur
                    this.parcelle = evt.getGrille()[evt.taille - 1][y];
                    break;
                case 3: // Bord gauche
                    this.parcelle = evt.getGrille()[x][0];
                    break;
            }
        }

        this.d = Direction.getRandom();
        this.vitesse = 1.0;
        obstaclesMemorises = new HashSet<>();
        champPerceptionRayon = 4;  // Valeur par défaut
        revelationRadius = 2;  // Valeur par défaut
    }

    private Parcelle getParcelleDir(Direction dir)
    {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }
    private void revealMap() {
        int intrusX = parcelle.x;
        int intrusY = parcelle.y;

        // Révéler les cases autour de l'intrus
        for (int dx = -revelationRadius; dx <= revelationRadius; dx++) {
            for (int dy = -revelationRadius; dy <= revelationRadius; dy++) {
                int x = intrusX + dx;
                int y = intrusY + dy;

                // Assurer que les indices sont dans les limites de la grille
                if (x >= 0 && x < taille && y >= 0 && y < taille) {
                    // Ajuster l'opacité de l'overlay pour révéler la case inférieure
                    cases[x][y].img.getImg().ajusterOpaciteOverlay(0);
                }
            }
        }
    }


    public void setCircle(Circle circle) {
        this.circle = circle;
        circle.setFill(coulIntrus);
        circle.setRadius(4);
    }

    public void setDirection(Direction d) {
        this.d = d;
    }

    public void bougerVersDirection() throws InterruptedException {
        Parcelle prochaineParcelle = getParcelleDir(d);

        if (prochaineParcelle.getType() == TypeParcelle.Tresor) {
            tresorTrouve = true;
            System.out.println("TRESOR TROUVE");
        }

        if (prochaineParcelle.getType() != TypeParcelle.Arbre && prochaineParcelle.getType() != TypeParcelle.Arbre2) {
            parcelle = prochaineParcelle;
            evt.bougerIntrus(this);
            revealMap();
            // Détecter les drones proches
            detecterDronesProches();
        }

        if (prochaineParcelle.getType() == TypeParcelle.Sortie && tresorTrouve) {
            evt.bougerIntrus(this);
            SimuDrone.tempo = 0;
            gagne();
            fermerApplication();
        }
    }

    public Circle getDessin() {
        return this.circle;
    }

    private void gagne() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("VOUS AVEZ GAGNE !!");
        alert.showAndWait();
    }

    private void perdu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("VOUS AVEZ Perdu ...");
        alert.showAndWait();
    }

    private void fermerApplication() {
        Platform.exit();
        System.exit(0);
    }

    private void detecterDronesProches() {
        int distanceDetection = 3;
        int dureeDetection = 3000;

        for (Drone drone : evt.lesDrones) {
            int distanceX = Math.abs(parcelle.x - drone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - drone.parcelle.y);

            int distance = Math.max(distanceX, distanceY);

            if (distance < distanceDetection) {

                new Thread(() -> {
                    long debutDetection = System.currentTimeMillis();

                    while (System.currentTimeMillis() - debutDetection < dureeDetection) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (distance(parcelle, drone.parcelle) < distanceDetection) {
                        Platform.runLater(() -> {
                            System.out.println("Perdu ! Resté à proximité du drone pendant trop longtemps !");
                            perdu();
                            fermerApplication();
                        });
                    }
                }).start();
            }
        }

        for (petitDrone petitDrone : evt.lesPetitDrones) {
            int distanceX = Math.abs(parcelle.x - petitDrone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - modele.petitDrone.parcelle.y);

            int distance = Math.max(distanceX, distanceY);

            if (distance < distanceDetection) {

                new Thread(() -> {
                    long debutDetection = System.currentTimeMillis();

                    while (System.currentTimeMillis() - debutDetection < dureeDetection) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (distance(parcelle, petitDrone.parcelle) < distanceDetection) {
                        Platform.runLater(() -> {
                            System.out.println("Perdu ! Resté à proximité du drone pendant trop longtemps !");
                            perdu();
                            fermerApplication();
                        });
                    }
                }).start();
            }
        }
    }

    private int distance(Parcelle p1, Parcelle p2) {
        int distanceX = Math.abs(p1.x - p2.x);
        int distanceY = Math.abs(p1.y - p2.y);
        return Math.max(distanceX, distanceY);
    }

}
