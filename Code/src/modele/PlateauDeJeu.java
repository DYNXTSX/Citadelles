package modele;

import java.util.ArrayList;
import java.util.List;

public class PlateauDeJeu {
    private Personnage[] listePersonnages;
    private Joueur[] listeJoueurs;
    private Pioche pioche;
    private Integer nombrePersonnages = 0;
    private Integer nombreJoueurs = 0;

    public PlateauDeJeu(){
        this.listeJoueurs = new Joueur[9];
        this.listePersonnages = new Personnage[9];
        this.pioche = new Pioche();
        pioche.melanger();
    }

    public Integer getNombrePersonnages() {
        return nombrePersonnages != null ? nombrePersonnages : 0;
    }
    public Integer getNombreJoueurs() {
        return nombreJoueurs != null ? nombreJoueurs : 0;
    }
    public Pioche getPioche() {
        return pioche;
    }
    public Personnage getPersonnage(int i){
        if(i >= 0 && i < this.listePersonnages.length-1)
            return this.listePersonnages[i];
        else
            return null;
    }
    public Joueur getJoueur(int i){
        if(i >= 0 && i < this.listeJoueurs.length-1)
            return this.listeJoueurs[i];
        else
            return null;
    }

    public void ajouterPersonnage(Personnage personnage){
        if(personnage != null && this.getNombrePersonnages() != this.listePersonnages.length){
            this.listePersonnages[this.getNombrePersonnages()] = personnage;
            personnage.setPlateauDeJeu(this);
            this.nombrePersonnages += 1;
        }
    }

    public void ajouterJoueur(Joueur joueur){
        if(this.nombreJoueurs<=8 && joueur!=null){
            this.listeJoueurs[this.nombreJoueurs] = joueur;
            this.nombreJoueurs++;
        }
    }

    public void setPioche(Pioche p) {
        this.pioche=p;
    }

    public Personnage[] getListePersonnages(){
        return this.listePersonnages;
    }

    public Joueur[] getListeJoueurs(){
        return this.listeJoueurs;
    }




}
