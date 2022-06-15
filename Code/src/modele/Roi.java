package modele;

public class Roi extends Personnage {

    public Roi(String nom, Integer rang, String caracteristiques) {
        super(nom, rang, caracteristiques);
    }

    public Roi(){
        super("Roi", 4, Caracteristiques.ROI);
    }

    @Override
    public void utiliserPouvoir() {
        if(this.getJoueur() instanceof Joueur && !this.getAssassine()){
            for(int i = 0; i < this.getPlateau().getNombreJoueurs(); i++){
                if(this.getPlateau().getJoueur(i).getPossedeCouronne())
                    this.getPlateau().getJoueur(i).setPossedeCouronne(false);
            }
            this.getJoueur().setPossedeCouronne(true);
            System.out.println("Je prends la couronne.");
        }
    }

    public void percevoirRessourcesSpecifiques() {
        int compteur = 0;
        if (getJoueur() != null && this.getAssassine() != true) {
            for (Quartier unQuartier : getJoueur().getCite()) {
                if (unQuartier != null && unQuartier.getType() == "NOBLE") {
                    compteur ++;
                }
            }
            getJoueur().ajouterPieces(compteur);
        }
    }

    public void utiliserPouvoirAvatar(){
        this.utiliserPouvoir();
    }
}
