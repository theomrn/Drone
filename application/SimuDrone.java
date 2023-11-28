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
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.*;


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
    /** délai en ms entre chaque etape de simulation*/
    int nbdrone=10;

    public static final int tempo = 100;
    /** troupe des elements graphiques*/
    Group troupe;

    /**lancement de l'application*/
    @Override
    public void start(Stage primaryStage) {
        taille = 100;
        construireScene(primaryStage);
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
        //ecoute de evenements claviers
        scene.setOnKeyTyped(e->agirSelonTouche( e.getCharacter(), littleCycle));
        for(int i=0;i<nbdrone;i++)
        {
            addDrone();
        }
        addIntrus();
        Intrus intrus = evt.getIntrus();
        Circle dessinIntrus = intrus.getDessin();
        dessinIntrus.requestFocus();
        dessinIntrus.setOnKeyPressed(e->{
            System.err.println(e.getCode());
            switch(e.getCode()) {
                case UP ->{
                    intrus.setDirection(Direction.N);
                    intrus.bougerVersDirection();
                }
                case LEFT -> {
                    intrus.setDirection(Direction.O);
                    intrus.bougerVersDirection();
                }
                case DOWN -> {
                    intrus.setDirection(Direction.S);
                    intrus.bougerVersDirection();
                }
                case RIGHT -> {
                    intrus.setDirection(Direction.E);
                    intrus.bougerVersDirection();
                }
            }
        });
    }

    /**
     * selon la touche :
     * f ajoute une fourmis dans le nid
     * d demarre l'animation
     * p pause l'animation
     * */
    void agirSelonTouche(String touche, Timeline chrono)
    {
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

    /**ajout d'une fourmis et de son image (cercle) au centre de la grille*/
    public void addDrone()
    {
        Drone f = evt.addDrone((int) (Math.random()*taille), (int) (Math.random()*taille));
        Circle c = new Circle(largeur/2d + 3*espace/2d, largeur/2d+ 3*espace/2d, espace);
        f.setCircle(c);
        troupe.getChildren().add(c);
    }

    public void addIntrus()
    {
        Intrus f = evt.addIntrus(taille/2,taille/2);
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
        KeyFrame bougeFourmi = new KeyFrame(new Duration(tempo),
                new KeyValue(c.centerXProperty(), xdest),
                new KeyValue(c.centerYProperty(), ydest));
        timeline.getKeyFrames().add(bougeFourmi);
        timeline.play();
    }

    /**methode principale*/
    public static void main(String[] args) {
        launch(args);
    }
}
