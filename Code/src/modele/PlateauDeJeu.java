package modele;

import java.util.ArrayList;
import java.util.List;

public class PlateauDeJeu {
    private List<Personnage> listePersonnage;
    private List<Joueur> listeJoueurs;
    private Pioche pioche;
    private Integer nombrePersonnages;
    private Integer nombreJoueurs;

    public PlateauDeJeu(){
        this.listeJoueurs = new ArrayList<Joueur>();
        this.listePersonnage = new ArrayList<Personnage>();
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
        return listePersonnage.get(i);
    }
    public Joueur getJoueur(int i){
            return listeJoueurs.get(i);
    }

    public void ajouterPersonnage(Personnage p){
        if(p != null && listePersonnage.size() <= 9){
            p.setPlateauDeJeu(this);
            listePersonnage.add(p);
            this.nombrePersonnages = listePersonnage.size();
        }
    }

    public void ajouterJoueur(Joueur j){
        if(j != null && listeJoueurs.size() <= 9){
            System.out.println("Joueur " +j.getNom()+" ajouté !");
            listeJoueurs.add(j);
            this.nombreJoueurs = listeJoueurs.size();
        }
    }




}
