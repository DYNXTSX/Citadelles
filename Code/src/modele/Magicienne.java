package modele;

import controleur.Interaction;

import java.util.ArrayList;
import java.util.Random;

public class Magicienne extends Personnage{


    public Magicienne() {
        super("Magicienne", 3, Caracteristiques.MAGICIENNE);
    }

    public void utiliserPouvoir(){

        System.out.print("Voulez vous échanger vos cartes avec celles d'un autre joueur ? (o/n) > ");

        Boolean exchangeWithOpponent = Interaction.lireOuiOuNon();
        PlateauDeJeu currentPlateau = this.getPlateau();

        if(exchangeWithOpponent){

            // On échange avec un autre joueur
            for(int i = 0; i < currentPlateau.getNombreJoueurs(); i++){

                Joueur currentPlayer = currentPlateau.getJoueur(i);
                System.out.println("\t" + (i+1) + " - " + currentPlayer.getNom() + " | " + currentPlayer.nbQuartiersDansMain() + " quartiers.");

            }

            int selectedPlayer;
            Boolean keepAsking = true;

            do{
                System.out.print("Avec quel joueur voulez vous échanger vos cartes ? > ");
                selectedPlayer = Interaction.lireUnEntier(1, currentPlateau.getNombreJoueurs()+1);
                Joueur targetedPlayer = currentPlateau.getJoueur(selectedPlayer-1);

                if(targetedPlayer.equals(this.getJoueur())){
                    System.out.println("/!\\ Vous ne pouvez pas vous choisir.");
                } else {
                    keepAsking = false;

                    ArrayList<Quartier> copieMagicienne = new ArrayList<Quartier>(this.getJoueur().getMain());
                    ArrayList<Quartier> copieOpposant = new ArrayList<Quartier>(targetedPlayer.getMain());

                    for(int i = 0; i <= copieMagicienne.size(); i++)
                        this.getJoueur().retirerQuartierDansMain();

                    for(int i = 0; i <= copieOpposant.size(); i++)
                        targetedPlayer.retirerQuartierDansMain();

                    for(Quartier quartier : copieOpposant)
                        this.getJoueur().ajouterQuartierDansMain(quartier);

                    for(Quartier quartier : copieMagicienne)
                        targetedPlayer.ajouterQuartierDansMain(quartier);

                }

            } while(keepAsking);

        } else {

            // On défausse un certain nombre de cartes dans la pioche.

            System.out.print("Combien de cartes voulez vous remplacer ? > ");

            int nbCards = Interaction.lireUnEntier();

            // lecture du nombre de cartes à défausser

            if(nbCards == this.getJoueur().nbQuartiersDansMain()){

                int initialNbQuartierMain = this.getJoueur().nbQuartiersDansMain();

                for(int i = 0; i < initialNbQuartierMain; i++)
                    currentPlateau.getPioche().ajouter(this.getJoueur().retirerQuartierDansMain());

                for(int i = 0; i < initialNbQuartierMain; i++)
                    this.getJoueur().ajouterQuartierDansMain(currentPlateau.getPioche().piocher());


            } else if (nbCards > 0 && nbCards < this.getJoueur().nbQuartiersDansMain()){

                ArrayList<Quartier> copieMain = new ArrayList<Quartier>(this.getJoueur().getMain());

                System.out.println("\nVoici les cartes de votre main: ");

                for(int i = 0; i < this.getJoueur().nbQuartiersDansMain(); i++){
                    Quartier currentQuartier = this.getJoueur().getMain().get(i);
                    System.out.println("\t" + (i+1) + " - " + currentQuartier.getNom() + " | " + currentQuartier.getType() + " | " + currentQuartier.getCout() + " PO");
                }


                int choosenCard;
                for(int i = 0; i < nbCards; i++){
                    System.out.print("\nQuelle carte choisissez vous ? > ");
                    choosenCard = Interaction.lireUnEntier(1, this.getJoueur().nbQuartiersDansMain()+1);
                    currentPlateau.getPioche().ajouter(copieMain.remove(choosenCard-1));
                }

                for(int i = 0; i < nbCards; i++)
                    copieMain.add(currentPlateau.getPioche().piocher());

                for(int i = 0; i <= this.getJoueur().nbQuartiersDansMain(); i++)
                    this.getJoueur().retirerQuartierDansMain();

                for(Quartier quartier : copieMain)
                    this.getJoueur().ajouterQuartierDansMain(quartier);

            }

        }

    }

    public void utiliserPouvoirAvatar(){

        Boolean exchangeWithOpponent = Interaction.boolRandom();
        PlateauDeJeu currentPlateau = this.getPlateau();

        if(exchangeWithOpponent){

            int selectedPlayer;
            Boolean keepAsking = true;

            do{
                selectedPlayer = Interaction.intRandom(1, currentPlateau.getNombreJoueurs()+1);
                Joueur targetedPlayer = currentPlateau.getJoueur(selectedPlayer-1);

                if(targetedPlayer.equals(this.getJoueur())){
                    keepAsking = true;
                } else {
                    keepAsking = false;

                    ArrayList<Quartier> copieMagicienne = new ArrayList<Quartier>(this.getJoueur().getMain());
                    ArrayList<Quartier> copieOpposant = new ArrayList<Quartier>(targetedPlayer.getMain());

                    for(int i = 0; i <= copieMagicienne.size(); i++)
                        this.getJoueur().retirerQuartierDansMain();

                    for(int i = 0; i <= copieOpposant.size(); i++)
                        targetedPlayer.retirerQuartierDansMain();

                    for(Quartier quartier : copieOpposant)
                        this.getJoueur().ajouterQuartierDansMain(quartier);

                    for(Quartier quartier : copieMagicienne)
                        targetedPlayer.ajouterQuartierDansMain(quartier);
                }

            } while(keepAsking);

        } else {

            int nbCards = Interaction.intRandom(1, this.getJoueur().nbQuartiersDansMain());

            if(nbCards == this.getJoueur().nbQuartiersDansMain()){

                int initialNbQuartierMain = this.getJoueur().nbQuartiersDansMain();

                for(int i = 0; i < initialNbQuartierMain; i++)
                    currentPlateau.getPioche().ajouter(this.getJoueur().retirerQuartierDansMain());

                for(int i = 0; i < initialNbQuartierMain; i++)
                    this.getJoueur().ajouterQuartierDansMain(currentPlateau.getPioche().piocher());


            } else if (nbCards > 0 && nbCards < this.getJoueur().nbQuartiersDansMain()){

                ArrayList<Quartier> copieMain = new ArrayList<Quartier>(this.getJoueur().getMain());

                int choosenCard;
                for(int i = 0; i < nbCards; i++){
                    choosenCard = Interaction.intRandom(1, this.getJoueur().nbQuartiersDansMain()+1);
                    currentPlateau.getPioche().ajouter(copieMain.remove(choosenCard-1));
                }

                for(int i = 0; i < nbCards; i++)
                    copieMain.add(currentPlateau.getPioche().piocher());

                for(int i = 0; i <= this.getJoueur().nbQuartiersDansMain(); i++)
                    this.getJoueur().retirerQuartierDansMain();

                for(Quartier quartier : copieMain)
                    this.getJoueur().ajouterQuartierDansMain(quartier);

            }
        }
    }
}
