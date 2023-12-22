package modele;

import java.util.Random;

/**
 * enumeration codant les directions d'une rose des vents
 * N, NE, E, SE, S, SO, O, NO
 * chaque enumeration contient son vecteur directeur
 * (ex. un deplacement N implique un d�placement de 0 en x, -1 en y
 * un deplacement SE implique un d�placement de 1 en x, 1 en y)
 * @author Morneau Théo Molin Quentin Pilot Lucas Emmanuel Adam
 */
public enum Direction {N(0,-1), E(1,0), S(0,1), O(-1,0),NE(1,-1),NO(-1,-1),SE(1,1),SO(-1,1);
    int x;
    int y;
    /**objet utilise pour choix aleatoire*/
    static Random r = new Random();

    /**constructeur initalisant les donnees du vecteur directeur*/
    Direction(int x, int y) {this.x = x; this.y = y;}

    /**retourne une direction aleatoire autour de la direction courante
     * ex. si direction = N, retourne soit NO, soit N, soit NE*/
    Direction nextDirection()
    {
        int h = r.nextInt(3)-1;
        int no = this.ordinal();
        no = (no +h+ 4)%4;
        return Direction.values()[no];
    }


    /**retourne une direction au hasard*/
    static Direction getRandom() {
        return Direction.values()[(int)(Math.random()*Direction.values().length)];
    }
}
