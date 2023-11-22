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
    ArrayList<Drone> lesDrones;
    ArrayList<Intrus> intrus;


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
        lesDrones = new ArrayList<>();
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
    public void bouger(Drone f)
    {
        application.move(f.circle, f.parcelle.x, f.parcelle.y);
    }

/*    public void bouger(intrus i)
    {
        application.move(i.circle, i.parcelle.x, i.parcelle.y);
    }*/
    /**
     * demande a chaque fourmi d'evoluer
     */
    public void avancer() {
        lesDrones.forEach(Drone::errer);
    }
    /*
    public void avant()
    {
        intrus.forEach(intrus::errer);
    }
    */
    /**demande si possible a la parcelle  de retirer un dose de nourriture
     * */


    /**cree et ajoute une fourmi initialisee dans la case x,y
     * @return la fourmis creee*/
    public Drone addDrone(int x, int y)
    {
        ;
        Drone f = new Drone(this, x, y);
        lesDrones.add(f);
        return f;
    }

    /**
     * @return la grille des parcelles
     */
    public Parcelle[][] getGrille() {
        return grille;
    }


    public Intrus addintrus(int x, int y) {
        Intrus i = new Intrus(this, x, y);
        intrus.add(i);
        return i;
    }
}
