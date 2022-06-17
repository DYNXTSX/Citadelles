package modele;

import controleur.Interaction;

import java.util.ArrayList;
import java.util.Random;

public class Magicienne extends Personnage{


    public Magicienne() {
        super("Magicienne", 3, Caracteristiques.MAGICIENNE);
    }

    public void utiliserPouvoir() {
        System.out.println(this.getPlateau().getPioche().nombreElements());
        System.out.println("Voulez-vous échanger vos cartes avec celles d'un autre joueur ?(o/n)");
        boolean ask = Interaction.lireOuiOuNon();
        if(this.getJoueur().nbQuartiersDansMain()==0) {
            System.out.println("Vous ne possédez pas de cartes à échanger.");
        } else if(ask) {
            System.out.println("Avec quel personnage voulez-vous échanger vos cartes ?");
            int i=1;
            while(i<=this.getPlateau().getNombrePersonnages()) {
                System.out.println(i+" "+this.getPlateau().getPersonnage(i-1).getNom()+" "+this.getPlateau().getJoueur(i-1).nbQuartiersDansMain());
                i++;
            }
            boolean continu=true;
            int lecture=0;
            do {
                try {
                    lecture=Interaction.lireUnEntier(1,this.getPlateau().getNombrePersonnages()+1);
                    if(this.getPlateau().getJoueur(lecture-1).getPersonnage().getRang()==3) {
                        System.out.println("Vous ne pouvez pas vous choisir");
                        throw new Exception();
                    } else {
                        continu=false;
                    }
                } catch(Exception e) {
                    System.out.print("Votre choix : ");
                }
            } while(continu);
            ArrayList<Quartier> copieTableauMagicienne=new ArrayList<Quartier>(this.getJoueur().getMain());
            ArrayList<Quartier> copieTableauJoueurChoix=new ArrayList<Quartier>(this.getPlateau().getJoueur(lecture-1).getMain());
            this.getJoueur().getMain().clear();
            this.getPlateau().getJoueur(lecture-1).getMain().clear();
            this.getJoueur().getMain().addAll(copieTableauJoueurChoix);
            this.getPlateau().getJoueur(lecture-1).getMain().addAll(copieTableauMagicienne);
        } else {
            System.out.println("Combien de cartes voulez-vous prendre dans la pioche ?");
            int nb=Interaction.lireUnEntier(0, this.getJoueur().getMain().size()+1);
            if(nb==0) {
                System.out.println("Vous n'échangez aucune cartes.");
            } else if(nb==this.getJoueur().getMain().size()) {
                ArrayList<Quartier> copieTableauMagicienne=new ArrayList<Quartier>(this.getJoueur().getMain());
                this.getJoueur().getMain().clear();
                for(int i=0;i<nb;i++) {
                    this.getPlateau().getPioche().ajouter(copieTableauMagicienne.get(i));
                    copieTableauMagicienne.remove(i);
                    copieTableauMagicienne.add(i, this.getPlateau().getPioche().piocher()) ;
                }
                this.getJoueur().getMain().addAll(copieTableauMagicienne);
            } else {
                ArrayList<Quartier> copieTableauMagicienne=new ArrayList<Quartier>(this.getJoueur().getMain());
                this.getJoueur().getMain().clear();
                System.out.println("Voici les cartes de votre main : ");
                for(int i=1;i<=copieTableauMagicienne.size();i++) {
                    System.out.println(i+" "+copieTableauMagicienne.get(i-1).getNom()+" - type : "+copieTableauMagicienne.get(i-1).getType()+" - pièces : "+copieTableauMagicienne.get(i-1).getCout());
                }
                for(int i=0;i<nb;i++) {
                    if(i<nb) {
                        System.out.println("Quel est le numéro de la carte que vous voulez retirer ? ");
                    }
                    int num=Interaction.lireUnEntier(1, copieTableauMagicienne.size()+2);
                    this.getPlateau().getPioche().ajouter(copieTableauMagicienne.get(num-1));
                    copieTableauMagicienne.remove(num-1);
                    copieTableauMagicienne.add(this.getPlateau().getPioche().piocher());
                    if(i<nb-1) {
                        for(int j=1;j<=copieTableauMagicienne.size();j++) {
                            System.out.println(j+" "+copieTableauMagicienne.get(j-1).getNom()+" - type : "+copieTableauMagicienne.get(j-1).getType()+" - pièces : "+copieTableauMagicienne.get(j-1).getCout());
                        }
                    }
                }
                this.getJoueur().getMain().addAll(copieTableauMagicienne);
            }
        }
    }
    public void utiliserPouvoirAvatar() {
        //échanger ses cartes avec un autre joueur
        int ask=1;
        System.out.println(ask);
        if(this.getJoueur().nbQuartiersDansMain()==0) {
            System.out.println("Le joueur "+this.getJoueur().getNom()+" ne possède pas de cartes à échanger.");
        } else if(ask==1) {
            System.out.println("échange de cartes avec un joueur");
            boolean continu=true;
            int lecture=0;
            do {
                try {
                    lecture = Interaction.intRandom(0, this.getPlateau().getNombrePersonnages()+1);
                    if(this.getPlateau().getJoueur(lecture).getPersonnage().getRang()==3) {
                        throw new Exception();
                    } else {
                        continu=false;
                    }
                } catch(Exception e) {}
            } while(continu);
            ArrayList<Quartier> copieTableauMagicienne=new ArrayList<Quartier>(this.getJoueur().getMain());
            ArrayList<Quartier> copieTableauJoueurChoix=new ArrayList<Quartier>(this.getPlateau().getJoueur(lecture).getMain());
            this.getJoueur().getMain().clear();
            this.getPlateau().getJoueur(lecture).getMain().clear();
            this.getJoueur().getMain().addAll(copieTableauJoueurChoix);
            this.getPlateau().getJoueur(lecture).getMain().addAll(copieTableauMagicienne);
        } else if(ask==0){
            System.out.println("échange de cartes avec la pioche");
            int nb = Interaction.intRandom(0, this.getJoueur().getMain().size()+1);
            System.out.println(nb+" cartes échangées");
            if(nb==0) {
                System.out.println("Le joueur "+this.getJoueur().getNom()+" n'échange aucune carte");
            } else if(nb==this.getJoueur().getMain().size()) {
                ArrayList<Quartier> copieTableauMagicienne=new ArrayList<Quartier>(this.getJoueur().getMain());
                this.getJoueur().getMain().clear();
                for(int i=0;i<nb;i++) {
                    this.getPlateau().getPioche().ajouter(copieTableauMagicienne.get(i));
                    copieTableauMagicienne.remove(i);
                    copieTableauMagicienne.add(i, this.getPlateau().getPioche().piocher()) ;
                }
                this.getJoueur().getMain().addAll(copieTableauMagicienne);
            } else {
                ArrayList<Quartier> copieTableauMagicienne=new ArrayList<Quartier>(this.getJoueur().getMain());
                this.getJoueur().getMain().clear();
                for(int i=0;i<nb;i++) {
                    int num=Interaction.intRandom(0, nb+1);
                    System.out.println(num);
                    this.getPlateau().getPioche().ajouter(copieTableauMagicienne.get(num));
                    copieTableauMagicienne.remove(num);
                    copieTableauMagicienne.add(this.getPlateau().getPioche().piocher());
                }
                this.getJoueur().getMain().addAll(copieTableauMagicienne);
            }
        }
    }
}
