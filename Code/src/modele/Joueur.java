package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joueur {
    private String nom;
    private Integer tresor = 0;
    private Quartier[] cite;
    private Integer nbQuartiers = 0;
    private ArrayList<Quartier> main;
    private Boolean possedeCouronne;
    protected Personnage nomPersonnage;

    public Joueur(String nom){
        this.nom = nom;
        this.tresor = 0;
        this.nbQuartiers = 0;
        this.possedeCouronne = false;
        this.main = new ArrayList<Quartier>();
        this.cite = new Quartier[8];
        this.nomPersonnage = null;
    }

    public String getNom() {
        return nom;
    }
    public Integer nbPieces(){
        return tresor == null ? tresor : 0;
    }
    public Integer nbQuartiersDansCite(){
        return nbQuartiers;
    }
    public Quartier[] getCite(){
        return this.cite;
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
        if(this.nbQuartiersDansCite() < 8){
            this.cite[this.nbQuartiersDansCite()] = quartier;
            this.nbQuartiers++;
        }
    }
    public Boolean quartierPresentDansCite(String nom){
        Boolean isPresent = false;

        for(Quartier qt : this.cite){
            if(qt instanceof Quartier && qt.getNom() == nom){
                isPresent = true;
                break;
            }
        }

        return isPresent;
    }
    public Quartier retirerQuartierDansCite(String nom){
        Quartier quartierTrouve = null;

        for(int i = 1; i < this.cite.length; i++){
            if(this.cite[i-1] instanceof Quartier && this.cite[i-1].getNom().equals(nom)){
                quartierTrouve = this.cite[i-1];
                this.cite[i-1] = null;
            }

            if(this.cite[i-1] == null){
                this.cite[i-1] = this.cite[i];
                this.cite[i] = null;
            }
        }

        this.nbQuartiers--;
        return quartierTrouve != null ? quartierTrouve : null;
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

    public ArrayList<Quartier> getMain() {
        return main;
    }

    public Quartier getQuartier(int q) {
        return cite[q];
    }

    public void reinitialiser(){
        this.tresor = 0;

        while (nbQuartiersDansMain() > 0) {
            retirerQuartierDansMain();
        }

        for (int i = 0; i < nbQuartiersDansCite(); i ++) {
            retirerQuartierDansCite(cite[i].getNom());
        }
    }

    public Personnage getPersonnage() {
        return nomPersonnage;
    }
}
