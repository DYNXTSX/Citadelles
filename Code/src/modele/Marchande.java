package modele;

public class Marchande extends Personnage{
    public Marchande() {
        super("Marchande",6,Caracteristiques.MARCHANDE);
    }

    public void utiliserPouvoir(){
        this.getJoueur().ajouterPieces(1);
    }
    public void percevoirRessourcesSpecifiques() {
        if(this.getJoueur()!=null && !this.getAssassine()) {
            for(int i=0;i<this.getJoueur().nbQuartiersDansCite();i++) {
                if(this.getJoueur().getQuartier(i).getType()=="COMMERCANT") {
                    this.getJoueur().ajouterPieces(1);
                }
            }
            if (this.getJoueur().quartierPresentDansCite("École de Magie")) {
                this.getJoueur().ajouterPieces(1);
            }
        }
    }
}
