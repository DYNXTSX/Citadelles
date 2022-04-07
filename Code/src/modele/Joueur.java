package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joueur {
    private String nom;
    private Integer tresor;
    private List<Quartier> cite;
    private Integer nbQuartiers;
    private ArrayList<Quartier> main;
    private Boolean possedeCouronne;

    public Joueur(String nom){
        this.nom = nom;
        this.tresor = 0;
        this.nbQuartiers = 0;
        this.possedeCouronne = false;
        this.main = new ArrayList<Quartier>();
        this.cite = new ArrayList<Quartier>();
    }

    public String getNom() {
        return nom;
    }
    public Integer nbPieces(){
        return tresor;
    }
    public Integer nbQuartiersDansCite(){
        return cite.size();
    }
    public List<Quartier> getCite(){
        return cite;
    }
    public Integer nbQuartiersDansMain(){
        return main.size();
    }
    public Boolean getPossedeCouronne(){
        return possedeCouronne;
    }
    public void setPossedeCouronne(Boolean b){
        this.possedeCouronne = b;
    }

    public void ajouterPieces(Integer nbPieces){
        this.tresor += nbPieces > 0 ? nbPieces : 0;
    }
    public void retirerPieces(Integer nbPieces){
        this.tresor -= ((nbPieces > 0)&&(nbPieces <= tresor)) ? nbPieces : 0;
    }

    public void ajouterQuartierDansCite(Quartier quartier){
        if(nbQuartiersDansCite() < 8){
            System.out.println("Le quartier " + quartier.getNom() + "["+ quartier.getType()+"]" +" a été construit !");
            cite.add(quartier);
        }
    }
    public Boolean quartierPresentDansCite(String nom){
        for(Quartier q : cite){
            if(q.getNom().equals(nom))
                return true;
        }
        return false;
    }
    public Quartier retirerQuartierDansCite(String nom){
        for(Quartier q : cite){
            if(q.getNom().equals(nom)){
                cite.remove(q);
                return q;
            }
        }
        return null;
    }

    public void ajouterQuartierDansMain(Quartier quartier){
        main.add(quartier);
    }
    public Quartier retirerQuartierDansMain(){
        if(nbQuartiersDansMain() == 0)
            return null;
        else{
            Random generateur = new Random();
            int numeroHasard = generateur.nextInt(this.nbQuartiersDansMain());
            Quartier q = main.get(numeroHasard);
            main.remove(numeroHasard);
            return q;
        }
    }


    public void reinitialiser(){
        this.tresor = 0;
        this.main = new ArrayList<Quartier>();
        this.cite.clear();
    }



}
