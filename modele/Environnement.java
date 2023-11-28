package modele;

import application.SimuDrone;
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
    /* % d'arbres */
    double aleaArbres = 0.15;
    int nb_sortie = 2;

    /**les fourmis presentes*/
    ArrayList<Drone> lesDrones;
    ArrayList<Intrus> intrus;


    /**lien vers l'application graphiquue*/
    SimuDrone application;


    /**
     * constructeur initialisant l'application et la taille,
     * la grille et la liste des fourmis
     */
    public Environnement(SimuDrone application, int taille) {
        this.application = application;
        this.taille = taille;
        grille = new Parcelle[taille][taille];
        lesDrones = new ArrayList<>();
        intrus = new ArrayList<>();
        init();
        addSortie();
        ajouterTresor();
    }


    /**
     * remplit la grille de parcelles de type terrain
     */
    void init() {
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++) {
                grille[i][j] = new Parcelle(grille, i, j, TypeParcelle.Terrain);
                if (Math.random()<aleaArbres)grille[i][j].type=TypeParcelle.Arbre;
            }
    }

    /**demande au cerle lie a la fourmi de se deplacer dans le point identifie par la parcelle
     * @param f fourmi qui se deplace*/
    public void bougerDrone(Drone f)
    {
        application.move(f.circle, f.parcelle.x, f.parcelle.y);
    }

    public void bougerIntrus(Intrus i)
    {
        application.move(i.circle, i.parcelle.x, i.parcelle.y);
    }

    public void avancer() {
        lesDrones.forEach(Drone::errer);
    }

    /**cree et ajoute un drone initialisee dans la case x,y
     * @return la fourmis creee*/
    public Drone addDrone(int x, int y)
    {
        Drone f = new Drone(this, x, y);
        lesDrones.add(f);
        return f;
    }

    public Intrus addIntrus(int x,int y){
        Intrus f = new Intrus(this, x, y);
        intrus.add(f);
        return f;
    }

    public Intrus getIntrus(){
        return intrus.get(0);
    }


    /**
     * @return la grille des parcelles
     */
    public Parcelle[][] getGrille() {
        return grille;
    }

    /**
     * ajouter une sortie a la grille , les sortie sont au milieu des cotés de l'environnement
     */
    void addSortie(){
        // Déclaration et initialisation du tableau de coordonnées
        int[][] coordonnees = new int[4][2];
        int t = taille/2;

        // Ajout de coordonnées au tableau
        coordonnees[0][0] = 0;
        coordonnees[0][1] = t;

        coordonnees[1][0] = t;
        coordonnees[1][1] = 0;

        for (int i = 0;i<nb_sortie;i++){
            grille[coordonnees[i][0]][coordonnees[i][1]] = new Parcelle(grille, 0, t, TypeParcelle.Sortie);
            grille[coordonnees[i][0]][coordonnees[i][1]].type = TypeParcelle.Sortie;
        }

    }

    /**
     * fonction pour ajouter un tresor a trouver avant de pouvoir utiliser la sortie
     */
    void ajouterTresor() {
        int i = (int)(Math.random()*taille);
        int y = (int)(Math.random()*taille);

        grille[i][y] = new Parcelle(grille, i, y, TypeParcelle.Sortie);
        grille[i][y].type = TypeParcelle.Sortie;

    }
}
