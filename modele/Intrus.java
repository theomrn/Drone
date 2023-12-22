package modele;

import application.SimuDrone;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// Morneau Théo Molin Quentin Pilot Lucas
// Classe basé sur drone mais largement modifier
// Cette classe représente l'intrus donc le joueur qui va se deplacer dans le jeu.

public class Intrus {
    // referance a l'environnement
    Environnement evt;
    // direction
    Direction d;
    // cercle qui permet l'affichage
    public Circle circle;
    // rayon de la recherche
    public int revelationRadius;
    Parcelle parcelle;
    // taille de la grille
    private int taille;
    // position de l'intrus
    public Parcelle[][] cases;
    // Memorisation des obstacles
    private Set<Parcelle> obstaclesMemorises;
    // taille du champ de perception
    private int champPerceptionRayon;
    // couleur de l'intrus
    static final public Color coulIntrus = Color.color(0, 0, 1);
    // vitesse de l'intrus
    private double vitesse;
    // boolean qui verifie si le joueur a trouver le tresor
    boolean tresorTrouve = false;
    // boolean qui verifie si le joueur est toujours en jeu
    boolean jouerTJRS = true;

    public Intrus(Environnement evt, int x, int y) {
        // constructeur de l'intrus , l'intrus apparait dans la grille au hasard sur un bord
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

    private Parcelle getParcelleDir(Direction dir) {
        // retourne la case correspondant à la direction
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }

    private void revealMap() {
        // Fonction permetant de relever la carte selon le deplacement de l'intrus
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
        // parametre le cercle
        this.circle = circle;
        circle.setFill(coulIntrus);
        circle.setRadius(4);
    }

    public void setDirection(Direction d)
    {
        // parametre la direction
        this.d = d;
    }

    public void bougerVersDirection() throws InterruptedException {
        // permet de bouger l'intrus vers la direction donnée , tout en empechant l'intrus de marcher sur les arbres
        // cette méthode permet aussi de detecter si l'intrus est passé sur le tresor
        // Elle permet aussi de faire gagner le joueur s'il a trouver le tresor et une sortie
        Parcelle prochaineParcelle = getParcelleDir(d);

        if (prochaineParcelle.getType() == TypeParcelle.Tresor) {
            tresorTrouve = true;
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
        // Retourne le dessin de l'intrus
        return this.circle;
    }

    private void gagne() {
        // permet d'afficher un message de victoire et de finir le jeu
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("VOUS AVEZ GAGNE !!");
        alert.showAndWait();
    }

    private void perdu() {
        // permet d'afficher un message comme quoi le joeur a perdu et de finir le jeu
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("VOUS AVEZ Perdu ...");
        alert.showAndWait();
    }

    private void fermerApplication() {
        // Permet de fermer l'application
        Platform.exit();
        System.exit(0);
    }

    private void detecterDronesProches() {
        // cette méthode permet de detecter si l'intrus est détecté par un drone proche et si oui , le signaler a tous les autres drones
        // si l'intrus est detecté a moins de 3 cases d'un drone pendant 2 secondes le jeu est perdu
        int distanceDetection = 3;
        int dureeDetection = 3000;

        for (Drone drone : evt.lesDrones) {
            int distanceX = Math.abs(parcelle.x - drone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - drone.parcelle.y);

            int distance = Math.max(distanceX, distanceY);

            if (distance < distanceDetection) {
                // permet de donner les coordonnée a tous les drones
                new Thread(()->{
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    SimuDrone.isSpotted = true;
                    SimuDrone.coSpotted = new ArrayList<>();
                    SimuDrone.coSpotted.add(parcelle.x);
                    SimuDrone.coSpotted.add(parcelle.y);
                }).start();

                new Thread(() -> {
                    // Gele le temps de detection de l'intrus , quand il est detecté on attends 5 secondes puis on enlève les coordonnées aux autrs drones
                    // , on réinitialise leurs mouvments
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int newdistanceX = Math.abs(parcelle.x - drone.parcelle.x);
                    int newdistanceY = Math.abs(parcelle.y - drone.parcelle.y);

                    int newdistance = Math.max(newdistanceX, newdistanceY);
                    if (newdistance > distanceDetection) {
                        SimuDrone.isSpotted = false;
                        SimuDrone.coSpotted = new ArrayList<>();
                    }
                }).start();

                new Thread(() -> {
                    // gére le temps de detection de l'intrus , quand il est detecté on attends 3 secondes et s'il est encore dans les 3 case du meme drone le jeu est perdu
                    long debutDetection = System.currentTimeMillis();

                    while (System.currentTimeMillis() - debutDetection < dureeDetection) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (distance(parcelle, drone.parcelle) < distanceDetection && jouerTJRS) {
                        Platform.runLater(() -> {
                            System.out.println("Perdu ! Resté à proximité du drone pendant trop longtemps !");
                            jouerTJRS = false;
                            perdu();
                            fermerApplication();
                        });
                    }
                }).start();
            }
        }
        // même principe pour les petits drones
        for (petitDrone petitDrone : evt.lesPetitDrones) {
            int distanceX = Math.abs(parcelle.x - petitDrone.parcelle.x);
            int distanceY = Math.abs(parcelle.y - petitDrone.parcelle.y);

            int distance = Math.max(distanceX, distanceY);

            if (distance < distanceDetection) {
                SimuDrone.isSpotted = true;
                SimuDrone.coSpotted = new ArrayList<>();
                SimuDrone.coSpotted.add(parcelle.x);
                SimuDrone.coSpotted.add(parcelle.y);
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int newdistanceX = Math.abs(parcelle.x - petitDrone.parcelle.x);
                    int newdistanceY = Math.abs(parcelle.y - petitDrone.parcelle.y);

                    int newdistance = Math.max(newdistanceX, newdistanceY);
                    if (newdistance > distanceDetection) {
                        SimuDrone.isSpotted = false;
                        SimuDrone.coSpotted = new ArrayList<>();
                    }
                }).start();

                new Thread(() -> {
                    long debutDetection = System.currentTimeMillis();

                    while (System.currentTimeMillis() - debutDetection < dureeDetection) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (distance(parcelle, petitDrone.parcelle) < distanceDetection && jouerTJRS) {
                        Platform.runLater(() -> {
                            System.out.println("Perdu ! Resté à proximité du drone pendant trop longtemps !");
                            jouerTJRS = false;
                            perdu();
                            fermerApplication();
                        });
                    }
                }).start();
            }
        }
    }

    private int distance(Parcelle p1, Parcelle p2) {
        // retourne la distance entre deux parcelles
        int distanceX = Math.abs(p1.x - p2.x);
        int distanceY = Math.abs(p1.y - p2.y);
        return Math.max(distanceX, distanceY);
    }

}
