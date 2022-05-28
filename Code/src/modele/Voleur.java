package modele;

import controleur.Interaction;

public class Voleur extends Personnage {

    public Voleur(){
        super("Voleur", 2, Caracteristiques.VOLEUR);
    }

    public void utiliserPouvoir() {
        int i=1;
        int lecture=0;
        System.out.println("Quel personnage voulez-vous voler ?");
        while(i<=this.getPlateau().getNombrePersonnages()) {
            System.out.println(i+" "+this.getPlateau().getPersonnage(i-1).getNom());
            i++;
        }
        System.out.print("Votre choix : ");
        boolean continu=true;
        do {
            try {
                lecture= Interaction.lireUnEntier();
                if(this.getPlateau().getPersonnage(lecture-1).getRang()<=2) {
                    throw new Exception();
                } else {
                    this.getPlateau().getPersonnage(lecture-1).setVole();
                    this.getJoueur().ajouterPieces(this.getPlateau().getJoueur(lecture-1).nbPieces());
                    this.getPlateau().getJoueur(lecture-1).retirerPieces(this.getJoueur().nbPieces());
                    continu=false;
                }
            }catch (Exception e){
                System.out.println("Vous ne pouvez pas vous voler ou voler l'assassin.");
                System.out.print("Votre choix : ");
            }
        } while (continu);
    }

    public void utiliserPouvoirAvatar(){
        int choix;
        do{
            choix = Interaction.intRandom(0, this.getPlateau().getNombreJoueurs());
            Personnage target = this.getPlateau().getJoueur(choix).getPersonnage();

            if(!target.getNom().equals(this.getNom()) && target.getRang() != 1 && !target.getAssassine())
                target.setVole();

        } while (!this.getPlateau().getJoueur(choix).getPersonnage().getVole());
    }

}
