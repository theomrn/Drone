package modele;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.function.Function;

/**
 * cette classe represente une fourmis
 *
 * @author Emmanuel Adam
 */
public class Fourmi {
    // des couleurs
    static final public Color coulFourmi = Color.color(1,0,0);

    /**coeff de degradation de la dose de pheromone entre chaque depot */

    /**parcelle ou se trouve la fourmi*/
    Parcelle parcelle;

    /**direction actuelle de la fourmis*/
    Direction d;

    /**reference a l'environnement*/
    Environnement evt;

    /**indique si la fourmis est vide ou si elle porte de la nourriture*/
    boolean vide;
    /**indique la dose de pheromone que peut deposer la fourmis*/
    double dosePhero;

    /**sa representation graphique associee (simple cercle)*/
    Circle circle;
    private Object Fourmi;

    /**constructeur par defaut, inutilise */
    public Fourmi() { }

    public Fourmi(Environnement  evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
        vide = true;
        dosePhero = 1;
    }

    void errer()
    {
        d = d.nextDirection();
        parcelle = getParcelleDir(d);
        evt.bouger(this);
    }


    private Parcelle getParcelleDir(Direction dir)
    {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }


    public void setCircle(Circle circle) {
        this.circle = circle;
        circle.setFill(coulFourmi);
        circle.setRadius(4);
    }


}