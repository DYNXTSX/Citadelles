package modele;

import controleur.Interaction;

public class Assassin extends Personnage{

    public Assassin() {
        super("Assassin", 1, Caracteristiques.ASSASSIN);
    }

    @Override
    public void utiliserPouvoir() {
        int i=1;
        int lecture=0;
        System.out.println("Quel personnage voulez-vous assassiner ?");
        while(i<=this.getPlateau().getNombrePersonnages()) {
            System.out.println(i+" "+this.getPlateau().getPersonnage(i-1).getNom());
            i++;
        }
        System.out.print("Votre choix : ");
        boolean continu=true;
        do {
            try {
                lecture= Interaction.lireUnEntier();
                if(this.getPlateau().getPersonnage(lecture-1).getRang()==1) {
                    throw new Exception();
                } else {
                    this.getPlateau().getPersonnage(lecture-1).setAssassine();
                    continu=false;
                }
            } catch(Exception e) {
                System.out.println("Vous ne pouvez pas vous assassiner.");
                System.out.print("Votre choix : ");
            }
        } while(continu);
    }
}
