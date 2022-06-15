package modele;

public class Eveque extends Personnage{

    public Eveque(){
        super("Eveque",5,Caracteristiques.EVEQUE);
    }

    public void percevoirRessourcesSpecifiques() {
        for(int i=0;i<this.getJoueur().nbQuartiersDansCite();i++) {
            if(this.getJoueur().getQuartier(i).getType()=="RELIGIEUX") {
                this.getJoueur().ajouterPieces(1);
            }
        }
    }

    @Override
    public void utiliserPouvoir() {}
    public void utiliserPouvoirAvatar(){}
}
