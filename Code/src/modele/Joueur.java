package modele;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class Joueur {
    private String nom;
    private Integer tresor;
    private Quartier[] cite;
    private Integer nbQuartiers;
    private ArrayList<Quartier> main;
    private Boolean possedeCouronne;

    public Joueur(String nom){
        this.nom = nom;
        this.tresor = 0;
        this.nbQuartiers = 0;
        this.possedeCouronne = false;
        this.main = new ArrayList<Quartier>();
    }

    public String getNom() {
        return nom;
    }
    public Integer nbPieces(){
        return tresor;
    }
    public Integer nbQuartiersDansCite(){

    }

    public void ajouterPiece(Integer nbPieces){

    }

    public void retirerPiece(Integer nbPieces){

    }

    public Integer nbQuartiersDansCite(){

    }

    public Boolean quartierPresentDansCite(String nom){

    }

    public Quartier retirerQuartierDansCite(String nom){

    }

    public void ajouterQuartierDansMain(Quartier quartier){

    }

    public Integer nbQuartiersDansMain(){

    }

    public void reinitialier(){

    }



}
