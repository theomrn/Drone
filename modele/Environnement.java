package modele;

import application.SimuDrone;
import java.util.ArrayList;


/**
 * matrice des cellules du jeu de la vie
 *
 * @author Emmanuel Adam Morneau Théo Molin Quentin Pilot Lucas
 */
public class Environnement {
    /**grille des parcelles de terrain*/
    Parcelle[][] grille;
    /** taille de la grille */
    int taille;
    /* % d'arbres */
    double aleaArbres = 0.05;
    double aleaArbres2 = 0.10;
    // liste qui stocke les drones , intru et petitDrone
    ArrayList<Drone> lesDrones;
    ArrayList<Intrus> intrus;
    ArrayList<petitDrone> lesPetitDrones;

    /**lien vers l'application graphiquue*/
    SimuDrone application;

    /**
     * constructeur initialisant l'application et la taille, et initialisant la grille
     */
    public Environnement(SimuDrone application, int taille) {
        this.application = application;
        this.taille = taille;
        grille = new Parcelle[taille][taille];
        lesDrones = new ArrayList<>();
        intrus = new ArrayList<>();
        lesPetitDrones = new ArrayList<>();
        init();
        sortie();
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
                if (Math.random()<aleaArbres2)grille[i][j].type=TypeParcelle.Arbre2;
            }
    }

    public void bougerDrone(Drone f)
    {
        // fonction qui permet de bouger les drones
        application.move(f.circle, f.parcelle.x, f.parcelle.y);
    }

    public void bougerIntrus(Intrus i)
    {
        // fonction qui permet de bouger l'intrus
        application.move(i.circle, i.parcelle.x, i.parcelle.y);
    }

    public void bougerPetitDrone(petitDrone i)
    {
        // fonction qui permet de bouger les petits drones
        application.move(i.circle, i.parcelle.x, i.parcelle.y);
    }

    public void avancer() {
        // fonction qui appelle les méthode de mouvements et de detection des drones
        lesDrones.forEach(Drone::errer);
        lesDrones.forEach(Drone::detecterDronesDesactive);
        lesPetitDrones.forEach(petitDrone::errer);
        lesPetitDrones.forEach(petitDrone::detecterpetitDronesDesactive);
    }

    /**cree et ajoute un drone initialisee dans la case x,y */
    public Drone addDrone(int x, int y)
    {
        // ajoute des drones
        Drone f = new Drone(this, x, y);
        lesDrones.add(f);
        return f;
    }

    public Intrus addIntrus(int x,int y){
        // ajoute l'intrus
        Intrus f = new Intrus(this, x, y);
        intrus.add(f);
        return f;
    }

    public petitDrone addPetitDrone(int x, int y)
    {
        // ajoute les petits drones
        petitDrone f = new petitDrone(this, x, y);
        lesPetitDrones.add(f);
        return f;
    }

    public Intrus getIntrus(){
        // retourne l'intrus
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
    void sortie(){
        int t=taille/2;
        grille[0][t] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[0][t].type=TypeParcelle.Sortie;
        grille[1][t] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[1][t].type=TypeParcelle.Sortie;
        grille[99][t] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[99][t].type=TypeParcelle.Sortie;
        grille[98][t] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[98][t].type=TypeParcelle.Sortie;
        grille[t][0] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[t][0].type=TypeParcelle.Sortie;
        grille[t][1] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[t][1].type=TypeParcelle.Sortie;
        grille[t][99] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[t][99].type=TypeParcelle.Sortie;
        grille[t][98] = new Parcelle(grille, 0, t, TypeParcelle.Terrain);
        grille[t][98].type=TypeParcelle.Sortie;
        for(int i =1;i<6; i++){
            grille[0][t-i] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[0][t-i].type=TypeParcelle.Sortie;
            grille[1][t-i] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[1][t-i].type=TypeParcelle.Sortie;
            grille[99][t-i] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[99][t-i].type=TypeParcelle.Sortie;
            grille[98][t-i] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[98][t-i].type=TypeParcelle.Sortie;
            grille[t-i][0] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[t-i][0].type=TypeParcelle.Sortie;
            grille[t-i][1] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[t-i][1].type=TypeParcelle.Sortie;
            grille[t-i][99] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[t-i][99].type=TypeParcelle.Sortie;
            grille[t-i][98] = new Parcelle(grille, 0, i-t, TypeParcelle.Terrain);
            grille[t-i][98].type=TypeParcelle.Sortie;
        }
        for(int i =1;i<6; i++){
            grille[0][t+i] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[0][t+i].type=TypeParcelle.Sortie;
            grille[1][t+i] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[1][t+i].type=TypeParcelle.Sortie;
            grille[99][t+i] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[99][t+i].type=TypeParcelle.Sortie;
            grille[98][t+i] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[98][t+i].type=TypeParcelle.Sortie;
            grille[t+i][0] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[t+i][0].type=TypeParcelle.Sortie;
            grille[t+i][1] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[t+i][1].type=TypeParcelle.Sortie;
            grille[t+i][99] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[t+i][99].type=TypeParcelle.Sortie;
            grille[t+i][98] = new Parcelle(grille, 0, i+t, TypeParcelle.Terrain);
            grille[t+i][98].type=TypeParcelle.Sortie;
        }
    }

    /**
     * fonction pour ajouter un tresor a trouver avant de pouvoir utiliser la sortie
     */
    void ajouterTresor() {
        int i = (int)(Math.random()*(taille-5));
        int y = (int)(Math.random()*(taille-5));

        grille[i][y] = new Parcelle(grille, i, y, TypeParcelle.Tresor);
        grille[i][y].type = TypeParcelle.Tresor;
        grille[i+1][y] = new Parcelle(grille, i, y, TypeParcelle.Tresor);
        grille[i][y].type = TypeParcelle.Tresor;
        grille[i][y+1] = new Parcelle(grille, i, y, TypeParcelle.Tresor);
        grille[i][y].type = TypeParcelle.Tresor;
        grille[i+1][y+1] = new Parcelle(grille, i, y, TypeParcelle.Tresor);
        grille[i][y].type = TypeParcelle.Tresor;


    }
}
