package modele;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Intrus {
    Environnement evt;
    Direction d;
    public Circle circle;
    Parcelle parcelle;
    static final public Color coulIntrus = Color.color(0,0,1);
    private double vitesse;

    public Intrus(Environnement evt, int x, int y) {
        this.evt = evt;
        this.parcelle = evt.getGrille()[x][y];
        this.d = Direction.getRandom();
        this.vitesse = 1.0;
    }

    private Parcelle getParcelleDir(Direction dir)
    {
        int x = Math.max(0, Math.min(evt.taille - 1, parcelle.x + dir.x));
        int y = Math.max(0, Math.min(evt.taille - 1, parcelle.y + dir.y));
        return evt.grille[x][y];
    }

    void errer()
    {
        d = d.nextDirection();
        parcelle = getParcelleDir(d);
        while (parcelle.getType()==TypeParcelle.Arbre){
            d = d.nextDirection();
            parcelle = getParcelleDir(d);
        }
        evt.bougerIntrus(this);
    }


    public void setCircle(Circle circle) {
        this.circle = circle;
        circle.setFill(coulIntrus);
        circle.setRadius(4);
    }

    public void setDirection(Direction d) {
        this.d = d;
    }

    public void bougerVersDirection(){
        Parcelle prochaineParcelle = getParcelleDir(d);
        // Vérifiez si la prochaine parcelle est accessible (pas un arbre)
        if (prochaineParcelle.getType() != TypeParcelle.Arbre) {
            // Déplacez l'intrus vers la nouvelle parcelle
            parcelle = prochaineParcelle;
            evt.bougerIntrus(this);
        }
    }

    public Circle getDessin() {
        return this.circle;
    }
}
