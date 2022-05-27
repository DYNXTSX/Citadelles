package modele;

import controleur.Interaction;

public class Condottiere extends Personnage{
    public Condottiere() {
        super("Condottiere",8,Caracteristiques.CONDOTTIERE);
    }
    //méthodes
    public void percevoirRessourcesSpecifiques() {
        if(this.getJoueur()!=null && !this.getAssassine()) {
            for(int i=0;i<this.getJoueur().nbQuartiersDansCite();i++) {
                if(this.getJoueur().getQuartier(i).getType()=="MILITAIRE") {
                    this.getJoueur().ajouterPieces(1);
                }
            }
        }
    }

    public void utiliserPouvoir() {
        System.out.println("Voulez-vous utiliser votre pouvoir de destruction ?");
        boolean reponse=Interaction.lireOuiOuNon();
        if(reponse) {
            System.out.println("Voici la liste des joueurs et le contenu de leur cité :");
            for(int i=1;i<=this.getPlateau().getNombreJoueurs();i++) {
                System.out.print(i+" "+this.getPlateau().getJoueur(i-1).getNom()+": ");
                for(int j=1;j<=this.getPlateau().getJoueur(i-1).nbQuartiersDansCite();j++) {
                    System.out.print(j+" "+this.getPlateau().getJoueur(i-1).getQuartier(j-1).getNom()+"(coût "+this.getPlateau().getJoueur(i-1).getQuartier(j-1).getCout()+") ");
                }
                System.out.println("");
            }
            System.out.println("Pour information, vous avez "+this.getJoueur().nbPieces()+" pièces d'or dans votre trésor.");
            System.out.println("Quel joueur choisissez vous ? (0 pour ne rien faire)");
            boolean continu=true;
            boolean possible=false;
            int joueur=0;
            do {
                try {
                    joueur=Interaction.lireUnEntier(0, this.getPlateau().getNombreJoueurs()+2);
                    if(joueur==0) {
                        continu=false;
                    }
                    for(int i=0;i<this.getPlateau().getJoueur(joueur-1).nbQuartiersDansCite();i++) {
                        if(this.getPlateau().getJoueur(joueur-1).getQuartier(i).getCout()-1<=this.getJoueur().nbPieces()) {
                            possible=true;
                        }
                    }
                    if(!possible){
                        System.out.println("Vous ne pouvez pas choisir ce joueur car ces quartiers valent trop cher.");
                        throw new Exception();
                    } else if(this.getPlateau().getJoueur(joueur-1).getPersonnage().getRang()==8) {
                        System.out.println("Vous ne pouvez pas vous choisir.");
                        throw new Exception();
                    } else if(this.getPlateau().getJoueur(joueur-1).getPersonnage().getRang()==5) {
                        System.out.println("L'Eveque est protégé de votre pouvoir.");
                        throw new Exception();
                    } else {
                        continu=false;
                    }
                } catch(Exception e) {
                    System.out.print("Votre choix : ");
                }
            } while(continu);
            if(joueur==0) {
                System.out.println("Vous ne faites rien.");
            } else {
                System.out.print(this.getPlateau().getJoueur(joueur-1).getNom()+": ");
                for(int j=1;j<=this.getPlateau().getJoueur(joueur-1).nbQuartiersDansCite();j++) {
                    System.out.print(j+" "+this.getPlateau().getJoueur(joueur-1).getQuartier(j-1).getNom()+"(coût "+this.getPlateau().getJoueur(joueur-1).getQuartier(j-1).getCout()+") ");
                }
                System.out.println("");
                System.out.println("Quel quartier choisissez-vous ?");
                continu=true;
                int quartier=0;
                do {
                    try {
                        quartier= Interaction.lireUnEntier(1, this.getPlateau().getJoueur(joueur-1).nbQuartiersDansCite()+1);
                        if(quartier-1>this.getPlateau().getJoueur(joueur-1).nbQuartiersDansCite()) {
                            System.out.println("Il n'y a pas plus de "+quartier+" quartiers.");
                            throw new Exception();
                        } else if(this.getPlateau().getJoueur(joueur-1).getQuartier(quartier-1).getCout()-1>this.getJoueur().nbPieces()) {
                            System.out.println("Votre trésor n'est pas suffisant");
                            throw new Exception();
                        } else if(this.getPlateau().getJoueur(joueur-1).getQuartier(quartier-1).getNom()=="Donjon") {
                            System.out.println("Ce quartier n'est pas affecté");
                            throw new Exception();
                        } else {
                            continu=false;
                        }
                    } catch(Exception e) {
                        System.out.print("Votre choix : ");
                    }
                } while(continu);
                System.out.println("Le quartier \""+this.getPlateau().getJoueur(joueur-1).getQuartier(quartier-1).getNom()+"\" de "+this.getPlateau().getJoueur(joueur-1).getNom()+" a été supprimé");
                this.getPlateau().getJoueur(joueur-1).retirerQuartierDansCite(this.getPlateau().getJoueur(joueur-1).getQuartier(quartier-1).getNom());
            }
        } else {
            System.out.println("Vous n'utilisez pas votre pouvoir.");
        }
    }
}
