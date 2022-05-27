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
        if(getJoueur() != null){
            System.out.println("Je prends la couronne");
            getJoueur().setPossedeCouronne(true);
        }
    }

    @Override
    public void percevoirRessourcesSpecifiques() {
        int count = 0;
        if(getJoueur() != null){
            for(Quartier q : getJoueur().getCite()){
                if(q.getType().equals(Quartier.TYPE_QUARTIERS[2]))
                    count += 1;
            }
            System.out.println(count+" pi�ce(s) ont �t� ajouter au tr�sor du roi !");
            getJoueur().ajouterPieces(count);
        }
    }

    public void utiliserPouvoirAvatar(){
        this.utiliserPouvoir();
    }
}
