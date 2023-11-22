package modele;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import gui.ImgParcelle;
import modele.Fourmi;

public class Intru {
    Environnement evt;
    Direction d;
    public Circle circle;
    public Object parcelle;

    public Intru(Environnement environnement, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
    }

/*
    public void setCircle(Circle c) {
        this.circle = circle;
        circle.setFill(coulintru);
        circle.setRadius(4);
    }
*/


    public void errer() {
    }
}
