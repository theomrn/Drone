package modele;

import application.SimuFourmis;

import java.util.ArrayList;


/**
 * matrice des cellules du jeu de la vie
 *
 * @author Emmanuel Adam
 */
public class Environnement {
    /**grille des parcelles de terrain*/
    Parcelle[][] grille;
    /** taille de la grille */
    int taille;

    /**les fourmis presentes*/
    ArrayList<Fourmi> lesFourmis;
    ArrayList<Intru> intru;


    /**lien vers l'application graphiquue*/
    SimuFourmis application;


    /**
     * constructeur initialisant l'application et la taille,
     * la grille et la liste des fourmis
     */
    public Environnement(SimuFourmis application, int taille) {
        this.application = application;
        this.taille = taille;
        grille = new Parcelle[taille][taille];
        lesFourmis = new ArrayList<>();
        init();
    }


    /**
     * remplit la grille de parcelles de type terrain
     */
    void init() {
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++) {
                grille[i][j] = new Parcelle(grille, i, j, TypeParcelle.Terrain);
                if (Math.random()<0.15)grille[i][j].type=TypeParcelle.Arbre;
            }
    }

    /**
     * place un nid au centre de la grille
     */

    /**ajoute une zone de nourriture aux point nx,ny*/


    /**demande l'evaporation dans chaque parcelle*/


    /**demande la diffusion de pheromone a chaque parcelle*/


    /**demande au cerle lie a la fourmi de se deplacer dans le point identifie par la parcelle
     * @param f fourmi qui se deplace*/
    public void bouger(Fourmi f)
    {
        application.move(f.circle, f.parcelle.x, f.parcelle.y);
    }

/*    public void bouger(Intru i)
    {
        application.move(i.circle, i.parcelle.x, i.parcelle.y);
    }*/
    /**
     * demande a chaque fourmi d'evoluer
     */
    public void avancer() {
        lesFourmis.forEach(Fourmi::errer);
    }

    public void avant()
    {
        intru.forEach(Intru::errer);
    }

    /**demande si possible a la parcelle  de retirer un dose de nourriture
     * */


    /**cree et ajoute une fourmi initialisee dans la case x,y
     * @return la fourmis creee*/
    public Fourmi addFourmi(int x, int y)
    {
        ;Fourmi f = new Fourmi(this, x, y);
        lesFourmis.add(f);
        return f;
    }

    /**
     * @return la grille des parcelles
     */
    public Parcelle[][] getGrille() {
        return grille;
    }


    public Intru addIntru(int x, int y) {
        Intru i = new Intru(this, x, y);
        intru.add(i);
        return i;
    }
}
