package modele;

import javafx.scene.shape.Circle;

public class Intrus {
    Environnement evt;
    Direction d;
    public Circle circle;
    public Object parcelle;

    public Intrus(Environnement environnement, int x, int y) {
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
