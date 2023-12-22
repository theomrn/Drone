package application;

import gui.ImgParcelle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.*;
import javafx.scene.control.Alert;

import java.util.ArrayList;


public class SimuDrone extends Application {
    /**
     * environnement liee a cet objet graphique
     */
    Environnement evt;
    /**
     * elements graphiques représentant les parcelles de terrain
     */
    public ImgParcelle[][] cases;
    /** largeur du terrain en nb de cases  */
    private int taille;
    /** taille d'une parcelle en pixel*/
    int espace = 10;
    /** largeur du terrain en pixel*/
    int largeur = 600;
    /** heuteur du terrain en pixel*/
    int hauteur = 600;
    /** nombre de drones **/
    int nbdrone=5;
    // nombre de drones 2
    int nbdrone2 = 5;
    // boolean pour dire si le joueur est detecté
    public static boolean isSpotted=false;
    // coordonnees du joueur
    public static ArrayList<Integer> coSpotted;
    // tempo
    public static int tempo = 150;
    /** troupe des elements graphiques*/
    Group troupe;

    /**lancement de l'application*/
    @Override
    public void start(Stage primaryStage) {
        taille = 100;
        construireScene(primaryStage);
        coSpotted = new ArrayList<>();
    }

    /**
     * construit le theatre des opérations, la fenetre, l'environnement, les acteurs (elements graphiques),
     * et cycle de simulation
     * */
    void construireScene(Stage primaryStage) {
        espace = largeur / taille;
        //definir la scene principale
        troupe = new Group();
        Scene scene = new Scene(troupe, largeur, hauteur, Color.WHITE);
        primaryStage.setTitle("Simu Drone");
        primaryStage.setScene(scene);
        //definir les acteurs
        evt = new Environnement(this,taille);
        //definir les acteurs
        cases = new ImgParcelle[taille][taille];
        creerParcellesEtImg();
        colorerParcelles();
        //afficher le theatre
        primaryStage.show();
        //-----lancer le timer pour faire vivre la simulation
        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo),
                event -> evt.avancer()));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
        regle();
        //ecoute de evenements claviers
        scene.setOnKeyTyped(e->agirSelonTouche( e.getCharacter(), littleCycle));
        // ajoute les drones
        for(int i=0;i<nbdrone;i++)
        {
            addDrone();
        }
        for(int i=0;i<nbdrone2;i++)
        {
            addpetitDrone();
        }
        // ajoute l'intrus
        addIntrus();

        Intrus intrus = evt.getIntrus();
        Circle dessinIntrus = intrus.getDessin();
        dessinIntrus.requestFocus();
        // gestion des mouvements de l'intrus
        dessinIntrus.setOnKeyPressed(e->{
            System.err.println(e.getCode());
            switch(e.getCode()) {
                case Z ->{
                    intrus.setDirection(Direction.N);
                    try {
                        intrus.bougerVersDirection();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case Q -> {
                    intrus.setDirection(Direction.O);
                    try {
                        intrus.bougerVersDirection();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case S -> {
                    intrus.setDirection(Direction.S);
                    try {
                        intrus.bougerVersDirection();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case D -> {
                    intrus.setDirection(Direction.E);
                    try {
                        intrus.bougerVersDirection();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }


    void agirSelonTouche(String touche, Timeline chrono)
    {
        // fonction garder des fourmis permet de mettre le jeu sur pause pour des tests par exemple
        switch (touche)
        {
            case "d"->chrono.play();
            case "p"->chrono.stop();
                //TODO: Ajouter comme choix "+" qui accélère la simulation et "-" qui la ralentit
            default->System.out.println("touche percue = " + touche);
        }
    }

    /**
     * creation des parcelles de la grille, et de leurs images
     */
    void creerParcellesEtImg() {
        Parcelle[][] grille = evt.getGrille();
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++) {
                Parcelle cell = grille[i][j];
                cases[i][j] = new ImgParcelle(troupe, cell, (i * espace + espace), (j * espace + espace), espace);
                cell.setImg(cases[i][j]);
            }
    }

    /**colorer les parcelles*/
    void colorerParcelles() {
        for (ImgParcelle[] ligne: cases)
            for (ImgParcelle img:ligne)
                img.choisirCouleur();
    }

    /**ajout d'un drone et de son image (cercle) de façon aléatoire */
    public void addDrone()
    {
        Drone f = evt.addDrone((int) (Math.random()*taille), (int) (Math.random()*taille));
        Circle c = new Circle(largeur/2d + 3*espace/2d, largeur/2d+ 3*espace/2d, espace);
        f.setCircle(c);
        troupe.getChildren().add(c);
    }

    public void regle()
    {
        // affiche les controles
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Les contrôles sont :");
        alert.setContentText("Z pour avancer\nS pour reculer\nQ pour aller à gauche\nD pour aller à droite\n");
        alert.showAndWait();
    }

    public void addIntrus()
    {
        // ajoute l'intrus
        int randomBorder = (int) (Math.random() * 4);
        int x, y;

        switch (randomBorder) {
            // choisis un bord ou ajouter l'intrus
            case 0: // Bord supérieur
                x = (int) (Math.random() * taille);
                y = 0;
                break;
            case 1: // Bord droit
                x = taille - 1;
                y = (int) (Math.random() * taille);
                break;
            case 2: // Bord inférieur
                x = (int) (Math.random() * taille);
                y = taille - 1;
                break;
            case 3: // Bord gauche
                x = 0;
                y = (int) (Math.random() * taille);
                break;
            default:
                x = taille / 2;
                y = taille / 2;
                break;
        }

        Intrus f = evt.addIntrus(x, y);
        Circle c = new Circle((x * largeur) / taille + 3 * espace / 2, (y * largeur) / taille + 3 * espace / 2, espace);
        f.setCircle(c);
        troupe.getChildren().add(c);
    }

    public void addpetitDrone()
    {
        // ajout les drones qui ne peuvent passsé sur aucun arbres
        petitDrone f = evt.addPetitDrone((int) (Math.random()*taille), (int) (Math.random()*taille));
        Circle c = new Circle(largeur/2d + 3*espace/2d, largeur/2d+ 3*espace/2d, espace);
        f.setCircle(c);
        troupe.getChildren().add(c);
    }


    /**
     * deplace un cerle vers le point identifie en  xx,yy dans la grille
     * (donc necessité de calculer l'arrivee x,y en pixel)
     * */
    public void move(Circle c, int xx, int yy)
    {
        Timeline timeline = new Timeline();
        int xdest = (xx*largeur) / taille + 3*espace/2;
        int ydest = (yy*largeur) / taille + 3*espace/2;
        KeyFrame bougeDrone = new KeyFrame(new Duration(tempo),
                new KeyValue(c.centerXProperty(), xdest),
                new KeyValue(c.centerYProperty(), ydest));
        timeline.getKeyFrames().add(bougeDrone);
        timeline.play();
    }

    /**methode principale*/
    public static void main(String[] args) {
        launch(args);
    }
}
