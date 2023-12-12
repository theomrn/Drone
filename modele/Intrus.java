package modele;

import application.SimuDrone;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Intrus {
    Environnement evt;
    Direction d;
    public Circle circle;
    Parcelle parcelle;
    static final public Color coulIntrus = Color.color(0,0,1);
    private double vitesse;
    boolean tresorTrouve = false;

    public Intrus(Environnement evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
        this.vitesse = 2.0;
    }

    private Parcelle getParcelleDir(Direction dir)
    {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
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

        if (prochaineParcelle.getType() != TypeParcelle.Arbre) {
            if (prochaineParcelle.getType() == TypeParcelle.Tresor) {
                tresorTrouve = true;
                System.out.println("Tresor trouve");
                System.out.println(tresorTrouve);
            }
            parcelle = prochaineParcelle;
            evt.bougerIntrus(this);
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
    }

    private int distance(Parcelle p1, Parcelle p2) {
        int distanceX = Math.abs(p1.x - p2.x);
        int distanceY = Math.abs(p1.y - p2.y);
        return Math.max(distanceX, distanceY);
    }

}
