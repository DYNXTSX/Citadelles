package application;

import controleur.Interaction;
import modele.*;

import java.util.Random;

public class Jeu {
    private int nbTour = 0;
    private PlateauDeJeu plateauDeJeu;
    private int nuemroConfiguration;
    private Random generateur;

    public Jeu(int nbConfig){
        this.plateauDeJeu = new PlateauDeJeu();
        this.nuemroConfiguration = nbConfig;
        this.generateur = new Random();
    }

    public void jouer(){
        //Affichage bienvenue
        System.out.println(
                "\n" +
                "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n"+
                "\t\t  Bienvenue \n"+
                "\t\tsur Citadelles\n"+
                "\tPar Abdel, Alex & François \n"+
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n"
        );
        //Affichage du menu
        System.out.print(
                "\nPar quoi voulez vous commencer ? \n\n" +
                        "\t=> 1 - Afficher les règles\n" +
                        "\t==> 2 - Jouer une partie\n" +
                        "\t===> 3 - Quitter\n" +
                        "\n====> Votre choix > "
        );

        //Lecture entier menu
        int choix = Interaction.lireUnEntier(1, 3);

        switch (choix) {
            case 1:
                this.afficherLesRegles();
                break;

            case 2:
                this.jouerPartie();
                break;
            case 3:
                System.out.println("Au revoir, à bientôt !!");
                System.exit(0);
            default:
                break;
        }

    }

    private void afficherLesRegles(){
        System.out.println("Voici les règles !!");
    }

    private void jouerPartie(){
        this.initialisation();

        do{
            this.tourDeJeu();
            this.gestionCouronne();
            this.reinitialisationPersonnages();
        } while(!this.partieFinie());
    }

    private void initialisation(){
        Pioche pioche = Configuration.nouvellePioche();

        switch (this.nuemroConfiguration) {
            case 1:
                this.plateauDeJeu = Configuration.configurationDeBase(pioche);
                break;
            default:
                this.plateauDeJeu = Configuration.configurationDeBase(pioche);
                break;
        }

        // melange de la pioche
        pioche.melanger();

        // On remet les personnages dans l'ordre croissant des rangs
        Personnage tempPersonnage;
        for(int i = 1; i < this.plateauDeJeu.getNombrePersonnages(); i++){
            if(this.plateauDeJeu.getPersonnage(i-1).getRang() > this.plateauDeJeu.getPersonnage(i).getRang()){
                tempPersonnage = this.plateauDeJeu.getPersonnage(i-1);
                this.plateauDeJeu.getListePersonnages()[i-1] = this.plateauDeJeu.getPersonnage(i);
                this.plateauDeJeu.getListePersonnages()[i] = tempPersonnage;
            }
        }

        // Génération de la couronne
        int crownPlayerId = Interaction.intRandom(0, this.plateauDeJeu.getNombreJoueurs());
        this.plateauDeJeu.getJoueur(crownPlayerId).setPossedeCouronne(true);

        // Ajout des ressources de base aux personnages
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            this.plateauDeJeu.getJoueur(i).ajouterPieces(2);

            for(int j = 0; j < 4; j++)
                this.plateauDeJeu.getJoueur(i).ajouterQuartierDansMain(pioche.piocher());
        }
    }

    private void gestionCouronne(){

    }

    private void reinitialisationPersonnages(){

    }

    private Boolean partieFinie(){
        return true;
    }


    private void tourDeJeu(){
        //choix personnage
        choixPersonnages();
        Joueur winner = null;
        this.nbTour++;

        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀ Tour n°" + this.nbTour + " ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");

        //pour chaque personnage dans le jeu
        for(int i = 0; i < this.plateauDeJeu.getNombrePersonnages(); i++){
            //Personne n'a ce personnage :
            if(!(this.plateauDeJeu.getPersonnage(i).getJoueur() instanceof Joueur))
                continue;

            Personnage personnage = this.plateauDeJeu.getPersonnage(i);
            Joueur joueur = personnage.getJoueur();
            System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀" + personnage.getNom() + "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");

            //Cas personnage assassiné
            if(!joueur.getPersonnage().getAssassine()){
                //Cas personnage volé
                if(joueur.getPersonnage().getVole()){
                    for(int k = 0; k < this.plateauDeJeu.getNombreJoueurs(); k++){
                        Joueur j = this.plateauDeJeu.getJoueur(k);
                        if(j.getPersonnage() instanceof Voleur)
                            j.ajouterPieces(joueur.nbPieces());
                    }
                    joueur.retirerPieces(joueur.nbPieces());
                }

                System.out.println("\n▀▀ PERCEPTION RESSOURCES SPECIFIQUES ▀▀\n");
                this.percevoirRessource(joueur);
                joueur.getPersonnage().percevoirRessourcesSpecifiques();

                // Gestion de la merveille forge
                if(joueur.quartierPresentDansCite("Forge")){
                    System.out.println(
                            "\t▀▀ MERVEILLE FORGE ▀▀\n"+
                                    "\tVoulez vous échanger 2 PO pour 3 cartes ?\n"
                    );

                    Boolean exchangeGold;
                    if(joueur.getNom().contains("PNJ"))
                        exchangeGold = Interaction.boolRandom();
                    else
                        exchangeGold = Interaction.lireOuiOuNon();

                    if(exchangeGold){
                        joueur.retirerPieces(2);
                        for(int p = 0; p < 3; p++){
                            Quartier quartierAjoute = this.plateauDeJeu.getPioche().piocher();
                            joueur.ajouterQuartierDansMain(quartierAjoute);
                            System.out.println(quartierAjoute.getNom() + " ajouté à votre main.");
                        }
                    }
                }

                // Gestion de la merveille laboratoire
                if(joueur.quartierPresentDansCite("Laboratoire")){
                    System.out.print("Vous bénéficiez de l'effet de la merveille Laboratoire.\nVoulez vous défausser une carte pour 1 or ? (o/n) ");

                    System.out.println(
                            "\t▀▀ MERVEILLE LABORATOIRE ▀▀\n"+
                                    "\tVoulez vous défausser une carte pour 1 PO?\n"
                    );

                    Boolean exchangeCard;
                    if(joueur.getNom().contains("PNJ"))
                        exchangeCard = Interaction.boolRandom();
                    else
                        exchangeCard = Interaction.lireOuiOuNon();

                    if(exchangeCard){
                        System.out.println("Liste des cartes défaussables: ");

                        for(int p = 0; p < joueur.nbQuartiersDansMain(); p++)
                            System.out.println("\t" + (p+1) + " - " + joueur.getMain().get(p).getNom());

                        int choixExchange;
                        if(joueur.getNom().contains("PNJ"))
                            choixExchange = Interaction.intRandom(1, joueur.nbQuartiersDansMain()+1);
                        else
                            choixExchange = Interaction.lireUnEntier(1, joueur.nbQuartiersDansMain()+1);

                        joueur.getMain().remove(joueur.getMain().get(choixExchange-1));
                        joueur.ajouterPieces(1);
                    }
                }


                System.out.print("Voulez vous utiliser votre pouvoir ? (o/n) > ");

                Boolean usePower;
                if(joueur.getNom().contains("PNJ"))
                    usePower = Interaction.boolRandom();
                else
                    usePower = Interaction.lireOuiOuNon();

                if(usePower){
                    System.out.println(
                            "\n▀▀                                          ▀▀\n"+
                                    "\t" +joueur.getPersonnage().getNom() + " UTILISE SON POUVOIR\n"
                    );
                    if(joueur.getNom().contains("PNJ"))
                        joueur.getPersonnage().utiliserPouvoirAvatar();
                    else
                        joueur.getPersonnage().utiliserPouvoir();

                    System.out.println("▄▄                                          ▄▄\n");
                }

                System.out.print("Voulez vous constuire ? (o/n) > ");

                Boolean build;

                if(joueur.getNom().contains("PNJ"))
                    build = Interaction.boolRandom();
                else
                    build = Interaction.lireOuiOuNon();

                if(build){
                    System.out.println(
                            "\n▀▀                                          ▀▀\n"+
                                    "\t    " + joueur.getPersonnage().getNom() + " CONSTRUIT\n"+
                                    "\nVous avez " + joueur.nbPieces() + " PO\n"+
                                    (joueur.nbQuartiersDansCite() == 0 ? "Votre quartier est vide\n" : "Votre quartier est composé de :")
                    );

                    // Affichage des quartiers de la cite:
                    if(joueur.nbQuartiersDansCite() > 0){
                        for(int k = 0; k < joueur.nbQuartiersDansCite(); k++)
                            System.out.println("\t- " + joueur.getCite()[k].getNom());
                    }

                    int nbConstruction = joueur.getPersonnage() instanceof Architecte ? 3 : 1;
                    for(int n = 1; n <= nbConstruction; n++){
                        System.out.println("\nVotre main est composée de :");

                        for(int k = 0; k < joueur.nbQuartiersDansMain(); k++){
                            Quartier quartier = joueur.getMain().get(k);
                            System.out.println("\t" + (k+1) + " - " + quartier.getNom() + " | " + quartier.getType() + " | " + quartier.getCout() + " PO");
                        }

                        int constructionChoix;
                        Boolean keepAsking = false;

                        do {
                            System.out.print("\nConstruction n°" + n + ": Que voulez vous construire ? (0: ne rien faire) > ");

                            if(joueur.getNom().contains("PNJ"))
                                constructionChoix = Interaction.intRandom(0, joueur.nbQuartiersDansMain()+1);
                            else
                                constructionChoix = Interaction.lireUnEntier(0, joueur.nbQuartiersDansMain()+1);

                            if(constructionChoix == 0){
                                System.out.println("/!\\ Construction annulée");
                                keepAsking = false;
                            } else {
                                Quartier quartierChoisi = joueur.getMain().get(constructionChoix-1);

                                if(quartierChoisi.getNom().equals("Tripot")){
                                    if((joueur.nbQuartiersDansMain()-1) + joueur.nbPieces() < quartierChoisi.getCout()){
                                        System.out.println("Vous ne possèderez pas assez pour l'achat du tripot!");
                                    } else {

                                        System.out.println(
                                                "\t▀▀ MERVEILLE TRIPOT ▀▀\n"+
                                                        "Combien de cartes voulez vous échanger pour payer le tripot ?\n"+
                                                        "Vous possèdez " + (joueur.nbQuartiersDansMain()-1)+ " cartes échangeables dans votre main \n"
                                        );

                                        Boolean loopContinue = true;
                                        do{
                                            System.out.print("Votre choix > ");
                                            int nbCartes;
                                            if(joueur.getNom().contains("PNJ"))
                                                nbCartes = Interaction.intRandom(0, joueur.nbQuartiersDansMain());
                                            else
                                                nbCartes = Interaction.lireUnEntier(0, joueur.nbQuartiersDansMain());

                                            if(nbCartes > joueur.nbQuartiersDansMain()-1){
                                                System.out.println("/!\\ Vous n'avez pas assez de cartes en main.");
                                                loopContinue = true;
                                            } else if (joueur.nbPieces() < quartierChoisi.getCout()-nbCartes){
                                                System.out.println("Vous ne possèdez pas le montant restant.");
                                                loopContinue = true;
                                            } else {
                                                System.out.println("Il vous reste " + (quartierChoisi.getCout()-nbCartes) + " PO à payer.");
                                                loopContinue = false;
                                                keepAsking = false;
                                                joueur.ajouterQuartierDansCite(quartierChoisi);
                                                joueur.getMain().remove(quartierChoisi);
                                                joueur.retirerPieces(quartierChoisi.getCout()-nbCartes);
                                                System.out.println("Le quartier " + quartierChoisi.getNom() + " à été ajouté à votre cité!\n");
                                            }

                                        } while(loopContinue);

                                    }

                                } else {

                                    if(quartierChoisi.getCout() > joueur.nbPieces()){
                                        System.out.println("/!\\ Vous n'avez pas assez de pièces.");
                                        keepAsking = true;
                                    } else if(joueur.quartierPresentDansCite(quartierChoisi.getNom()) && !joueur.quartierPresentDansCite("Carrière")){
                                        System.out.println("/!\\ Ce quartier est déjà dans votre cité.");
                                        keepAsking = true;
                                    } else {
                                        if(joueur.quartierPresentDansCite(quartierChoisi.getNom()) && joueur.quartierPresentDansCite("Carrière"))
                                            System.out.println("▀▀ MERVEILLE CARRIERE ▀▀");

                                        if(joueur.quartierPresentDansCite("Manufacture") && quartierChoisi.getType().equals(Quartier.TYPE_QUARTIERS[4])){
                                            System.out.println("▀▀ MERVEILLE MANUFACTURE ▀▀");
                                            joueur.retirerPieces(quartierChoisi.getCout()-1);
                                        } else {
                                            joueur.retirerPieces(quartierChoisi.getCout());
                                        }
                                        // Gestion de la Merveille : cour des miracles
                                        if(quartierChoisi.getNom().equals("Cour des Miracles")){
                                            System.out.println("\n▀▀ MERVEILLE COUR DES MIRACLES ▀▀\nDe quel type doit être considéré cette carte ?");
                                            for(int x = 0; x < Quartier.TYPE_QUARTIERS.length; x++)
                                                System.out.println("\t" + (x+1) + " - " + Quartier.TYPE_QUARTIERS[x]);

                                            System.out.println("Votre choix > ");
                                            int choixMiracle;
                                            if(joueur.getNom().equals("PNJ"))
                                                choixMiracle = Interaction.intRandom(1, Quartier.TYPE_QUARTIERS.length+1);
                                            else
                                                choixMiracle = Interaction.lireUnEntier(1, Quartier.TYPE_QUARTIERS.length+1);

                                            quartierChoisi.setType(Quartier.TYPE_QUARTIERS[choixMiracle-1]);
                                        }

                                        joueur.ajouterQuartierDansCite(quartierChoisi);
                                        joueur.getMain().remove(quartierChoisi);
                                        System.out.println("Le quartier " + quartierChoisi.getNom() + " à été ajouté à votre cité!\n");

                                        keepAsking = false;

                                        if(joueur.nbQuartiersDansCite() >= 7)
                                            winner = joueur;
                                    }

                                }
                            }
                        } while(keepAsking);
                    }
                }

                System.out.println("\n▄▄                                          ▄▄\n");
            }

            System.out.println(
                    "\tTOUR DE " + personnage.getNom() + " TERMMINE\n"+
                            "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n"
            );
        }

        if(this.partieFinie())
            this.calculDesPoints(winner);
    }

    private void choixPersonnages(){

    }

    private void percevoirRessource(Joueur j){


    }

    private void calculDesPoints(Joueur winner){

    }
}
