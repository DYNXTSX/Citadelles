package application;

import controleur.Interaction;
import modele.*;

import java.util.Random;

public class Jeu {
    private int nbTour = 0;
    private PlateauDeJeu plateauDeJeu;
    private boolean citeCompletePremier;
    private int nuemroConfiguration = 0;
    private Random generateur;
    private String nomJoueurCiteCompletePremier;

    public Jeu(int nbConfig){
        this.plateauDeJeu = new PlateauDeJeu();
        this.nuemroConfiguration = nbConfig;
        this.generateur = new Random();
    }

    /**
     * Méthode jouer() ==> void
     * ===================================
     *
     * Cette méthode permet la gestion du jeu (menu, ...)
     */
    public void jouer(){
        //Affichage bienvenue
        System.out.println(
                "\n" +
                "================================"+
                "\t\t  Bienvenue \n"+
                "\t\tsur Citadelles\n"+
                "\tPar Abdel, Alex & François \n"+
                "================================"
        );
        //Affichage du menu
        System.out.print(
                "\nPar quoi voulez vous commencer ? \n\n" +
                        "=> 1 - Afficher les regles\n" +
                        "==> 2 - Jouer une partie\n" +
                        "===> 3 - Quitter\n" +
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
                System.out.println("Au revoir, a bientot !!");
                System.exit(0);
            default:
                break;
        }

    }

    /**
     * Méthode afficherLesRegles() ==> void
     * ===================================
     *
     * Cette méthode permet l'affichage des règles
     */
    private void afficherLesRegles(){
        System.out.println("Voici les regles !!");
    }

    /**
     * Méthode jouerPartie() ==> void
     * ===================================
     *
     * Cette méthode permet la gestion de la partie
     */
    private void jouerPartie(){
        this.initialisation();

        do{
            this.tourDeJeu();
            this.gestionCouronne();
            this.reinitialisationPersonnages();
        } while(!this.partieFinie());
    }

    /**
     * Méthode initialisation() ==> void
     * ===================================
     *
     * Cette méthode permet l'initialisation du jeu.
     */
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

    /**
     * Méthode gestionCouronne() ==> void
     * ===================================
     *
     * Cette méthode permet la gestion de la couronne
     */
    private void gestionCouronne(){
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            if(this.plateauDeJeu.getJoueur(i).getPersonnage() instanceof Roi)
                this.plateauDeJeu.getJoueur(i).setPossedeCouronne(true);
        }
    }

    /**
     * Méthode reinitialisationPersonnages() ==> void
     * ===================================
     *
     * Cette méthode permet la réinitialisation des personnages
     */
    private void reinitialisationPersonnages(){
        for(int i = 0; i < this.plateauDeJeu.getNombrePersonnages(); i++)
            this.plateauDeJeu.getPersonnage(i).reinitialiser();
    }

    /**
     * Méthode partieFinie() ==> Boolean
     * ===================================
     *
     * Cette méthode permet de savoir si la partie est finie ou non
     */
    private Boolean partieFinie(){
        Boolean end = false;
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            if(this.plateauDeJeu.getJoueur(i).nbQuartiersDansCite() > 7){
                end = true;
                break;
            }

        }
        return end;
    }

    /**
     * Méthode tourDeJeu() ==> void
     * ===================================
     *
     * Cette méthode permet la gestion du tour de jeu (appel des méthodes de perception des ressources, etc)
     * ==> Utilisation pouvoir ?
     * ==> Gestion bot ?
     */
    private void tourDeJeu(){
        //choix personnage
        choixPersonnages();
        Joueur winner = null;
        this.nbTour++;

        System.out.println("\n================ Tour n°" + this.nbTour + " ================\n");

        //pour chaque personnage dans le jeu
        for(int i = 0; i < this.plateauDeJeu.getNombrePersonnages(); i++){
            //Personne n'a ce personnage :
            if(!(this.plateauDeJeu.getPersonnage(i).getJoueur() instanceof Joueur))
                continue;

            Personnage personnage = this.plateauDeJeu.getPersonnage(i);
            Joueur joueur = personnage.getJoueur();
            System.out.println("\n================" + personnage.getNom() + "================\n");

            if (this.plateauDeJeu.getPersonnage(i).getVole() && this.plateauDeJeu.getPersonnage(i).getJoueur()!=null) {
                System.out.println("le personnage "+this.plateauDeJeu.getPersonnage(i).getNom()+" a été volé");
                for(int j=0;j<this.plateauDeJeu.getNombrePersonnages();j++) {
                    if(this.plateauDeJeu.getPersonnage(j).getCaracteristiques()==Caracteristiques.VOLEUR) {
                        this.plateauDeJeu.getPersonnage(j).getJoueur().ajouterPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces());
                    }
                }
            } else if(this.plateauDeJeu.getPersonnage(i).getVole() && this.plateauDeJeu.getPersonnage(i).getJoueur()==null){
                System.out.println("Le personnage n'est pas attribué, le voleur ne reçoit pas de pièces");
            }
            if (this.plateauDeJeu.getPersonnage(i).getVole() && this.plateauDeJeu.getPersonnage(i).getAssassine()) {
                System.out.println("Personnage assassiné, le tour passe");
            } else {

                System.out.println("\n== PERCEPTION RESSOURCES SPECIFIQUES ==\n");
                this.percevoirRessource(i);
                this.plateauDeJeu.getPersonnage(i).percevoirRessourcesSpecifiques();

                // Gestion de la merveille forge
                if(joueur.quartierPresentDansCite("Forge")){
                    System.out.println(
                            "\t== MERVEILLE FORGE ==\n"+
                                    "\tVoulez vous échanger 2 PO pour 3 cartes ?\n"
                    );

                    Boolean exchangeGold;
                    if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                        exchangeGold = Interaction.boolRandom();
                    else
                        exchangeGold = Interaction.lireOuiOuNon();

                    if(exchangeGold){
                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(2);
                        for(int p = 0; p < 3; p++){
                            Quartier quartierAjoute = this.plateauDeJeu.getPioche().piocher();
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(quartierAjoute);
                            System.out.println(quartierAjoute.getNom() + " ajouté à votre main.");
                        }
                    }
                }

                // Gestion de la merveille laboratoire
                if(joueur.quartierPresentDansCite("Laboratoire")){
                    System.out.print("Vous bénéficiez de l'effet de la merveille Laboratoire.\nVoulez vous défausser une carte pour 1 or ? (o/n) ");

                    System.out.println(
                            "\t== MERVEILLE LABORATOIRE ==\n"+
                                    "\tVoulez vous défausser une carte pour 1 PO?\n"
                    );

                    Boolean exchangeCard;
                    if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                        exchangeCard = Interaction.boolRandom();
                    else
                        exchangeCard = Interaction.lireOuiOuNon();

                    if(exchangeCard){
                        System.out.println("Liste des cartes défaussables: ");

                        for(int p = 0; p < joueur.nbQuartiersDansMain(); p++)
                            System.out.println("\t" + (p+1) + " - " + joueur.getMain().get(p).getNom());

                        int choixExchange;
                        if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                            choixExchange = Interaction.intRandom(1, joueur.nbQuartiersDansMain()+1);
                        else
                            choixExchange = Interaction.lireUnEntier(1, joueur.nbQuartiersDansMain()+1);

                        this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(joueur.getMain().get(choixExchange-1));
                        this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterPieces(1);
                    }
                }


                System.out.print("Voulez vous utiliser votre pouvoir ? (o/n) > ");

                Boolean usePower;
                if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                    usePower = Interaction.boolRandom();
                else
                    usePower = Interaction.lireOuiOuNon();

                if(usePower){
                    System.out.println(
                            "\n==                                          ==\n"+
                                    "\t" + plateauDeJeu.getPersonnage(i).getNom() + " UTILISE SON POUVOIR\n"
                    );
                    if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                        this.plateauDeJeu.getPersonnage(i).utiliserPouvoirAvatar();
                    else
                        this.plateauDeJeu.getPersonnage(i).utiliserPouvoir();

                    System.out.println("==                                          ==\n");
                }

                System.out.print("Voulez vous constuire ? (o/n) > ");

                Boolean build;

                if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                    build = Interaction.boolRandom();
                else
                    build = Interaction.lireOuiOuNon();

                if(build){
                    System.out.println(
                            "\n==                                          ==\n"+
                                    "\t    " + this.plateauDeJeu.getPersonnage(i).getNom() + " CONSTRUIT\n"+
                                    "\nVous avez " + this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces() + " PO\n"+
                                    (this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansCite() == 0 ? "Votre quartier est vide\n" : "Votre quartier est composé de :")
                    );

                    // Affichage des quartiers de la cite:
                    if(this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansCite() > 0){
                        for(int k = 0; k < this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansCite(); k++)
                            System.out.println("\t- " + this.plateauDeJeu.getPersonnage(i).getJoueur().getCite()[k].getNom());
                    }

                    int nbConstruction = this.plateauDeJeu.getPersonnage(i) instanceof Architecte ? 3 : 1;
                    for(int n = 1; n <= nbConstruction; n++){
                        System.out.println("\nVotre main est composée de :");

                        for(int k = 0; k < this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain(); k++){
                            Quartier quartier = this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(k);
                            System.out.println("\t" + (k+1) + " - " + quartier.getNom() + " | " + quartier.getType() + " | " + quartier.getCout() + " PO");
                        }

                        int constructionChoix;
                        Boolean keepAsking = false;

                        do {
                            System.out.print("\nConstruction n°" + n + ": Que voulez vous construire ? (0: ne rien faire) > ");

                            if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                                constructionChoix = Interaction.intRandom(0, this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()+1);
                            else
                                constructionChoix = Interaction.lireUnEntier(0, this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()+1);

                            if(constructionChoix == 0){
                                System.out.println("/!\\ Construction annulée");
                                keepAsking = false;
                            } else {
                                Quartier quartierChoisi = this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(constructionChoix-1);

                                if(quartierChoisi.getNom().equals("Tripot")){
                                    if((this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()-1) + this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces() < quartierChoisi.getCout()){
                                        System.out.println("Vous ne possèderez pas assez pour l'achat du tripot!");
                                    } else {

                                        System.out.println(
                                                "\t== MERVEILLE TRIPOT ==\n"+
                                                        "Combien de cartes voulez vous échanger pour payer le tripot ?\n"+
                                                        "Vous possèdez " + (this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()-1)+ " cartes échangeables dans votre main \n"
                                        );

                                        Boolean loopContinue = true;
                                        do{
                                            System.out.print("Votre choix > ");
                                            int nbCartes;
                                            if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
                                                nbCartes = Interaction.intRandom(0, this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain());
                                            else
                                                nbCartes = Interaction.lireUnEntier(0, this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain());

                                            if(nbCartes > this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()-1){
                                                System.out.println("/!\\ Vous n'avez pas assez de cartes en main.");
                                                loopContinue = true;
                                            } else if (this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces() < quartierChoisi.getCout()-nbCartes){
                                                System.out.println("Vous ne possèdez pas le montant restant.");
                                                loopContinue = true;
                                            } else {
                                                System.out.println("Il vous reste " + (quartierChoisi.getCout()-nbCartes) + " PO à payer.");
                                                loopContinue = false;
                                                keepAsking = false;
                                                this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansCite(quartierChoisi);
                                                this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(quartierChoisi);
                                                this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(quartierChoisi.getCout()-nbCartes);
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
                                            System.out.println("== MERVEILLE CARRIERE ==");

                                        if(joueur.quartierPresentDansCite("Manufacture") && quartierChoisi.getType().equals(Quartier.TYPE_QUARTIERS[4])){
                                            System.out.println("== MERVEILLE MANUFACTURE ==");
                                            joueur.retirerPieces(quartierChoisi.getCout()-1);
                                        } else {
                                            joueur.retirerPieces(quartierChoisi.getCout());
                                        }
                                        // Gestion de la Merveille : cour des miracles
                                        if(quartierChoisi.getNom().equals("Cour des Miracles")){
                                            System.out.println("\n== MERVEILLE COUR DES MIRACLES ==\nDe quel type doit être considéré cette carte ?");
                                            for(int x = 0; x < Quartier.TYPE_QUARTIERS.length; x++)
                                                System.out.println("\t" + (x+1) + " - " + Quartier.TYPE_QUARTIERS[x]);

                                            System.out.println("Votre choix > ");
                                            int choixMiracle;
                                            if(this.plateauDeJeu.getPersonnage(i).getJoueur().getNom().contains("PNJ"))
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

                System.out.println("\n==                                          ==\n");
            }

            System.out.println(
                    "\tTOUR DE " + personnage.getNom() + " TERMMINE\n"+
                            "================================\n"
            );
        }

        if(this.partieFinie())
            this.calculDesPoints(winner);
    }

    /**
     * Méthode choixPersonnages() ==> void
     * ===================================
     *
     * Cette méthode permet le choix des personnages
     */
    private void choixPersonnages(){
        System.out.println(
                "\n================================\n"+
                        "\t    CHOIX DES PERSONNAGES\n"
        );

        int carteVisible1 = 0;
        int carteVisible2 = 0;
        int carteCachee1 = 0;

        do{
            carteVisible1 = Interaction.intRandom(0, this.plateauDeJeu.getNombrePersonnages());
            carteVisible2 = Interaction.intRandom(0, this.plateauDeJeu.getNombrePersonnages());
            carteCachee1 = Interaction.intRandom(0, this.plateauDeJeu.getNombrePersonnages());
        } while(carteVisible1 == carteVisible2 || carteVisible2 == carteCachee1 || carteVisible1 == carteCachee1);

        System.out.println("Le personnage " + this.plateauDeJeu.getPersonnage(carteVisible1).getNom() + " est écarté face visible.");
        System.out.println("Le personnage " + this.plateauDeJeu.getPersonnage(carteVisible2).getNom() + " est écarté face visible.");
        System.out.println("Un personnage est écarté face caché");

        Personnage[] availableCharacters = this.plateauDeJeu.getListePersonnages().clone();
        availableCharacters[carteVisible1] = null;
        availableCharacters[carteVisible2] = null;
        availableCharacters[carteCachee1] = null;

        int crownPlayerId = 0;

        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++)
            crownPlayerId = this.plateauDeJeu.getJoueur(i).getPossedeCouronne() ? i : crownPlayerId;

        int playerIteration = 0;
        do{
            int currentPlayer = (crownPlayerId + playerIteration) % this.plateauDeJeu.getNombreJoueurs();

            System.out.println(
                    "\n==                                          ==\n"+
                            "\t\t  " + this.plateauDeJeu.getJoueur(currentPlayer).getNom() + " " + (this.plateauDeJeu.getJoueur(currentPlayer).getPossedeCouronne() == true ? "👑" : "") + "\n"+
                            "     Quel personnage choisissez vous ?\n"+
                            "==                                          ==\n"
            );

            System.out.print("Liste des personnages disponibles:\n");
            for(int i = 0; i < this.plateauDeJeu.getNombrePersonnages(); i++){
                if(availableCharacters[i] instanceof Personnage)
                    System.out.println("\t" + i + " - " + this.plateauDeJeu.getPersonnage(i).getNom());
            }

            int choix;
            do{
                System.out.print("\nVotre choix > ");
                if(this.plateauDeJeu.getJoueur(currentPlayer).getNom().contains("PNJ"))
                    choix = Interaction.intRandom(0, this.plateauDeJeu.getNombrePersonnages());
                else
                    choix = Interaction.lireUnEntier(0, this.plateauDeJeu.getNombrePersonnages());


                if(!(availableCharacters[choix] instanceof Personnage))
                    System.out.println("/!\\ Attention! Ce choix n'est pas disponible!");

            } while(!(availableCharacters[choix] instanceof Personnage));

            this.plateauDeJeu.getPersonnage(choix).setJoueur(this.plateauDeJeu.getJoueur(currentPlayer));
            availableCharacters[choix] = null;

            playerIteration++;

        } while(playerIteration <= this.plateauDeJeu.getNombreJoueurs()-1);

        System.out.println(
                "\n\tCHOIX DES PERSONNAGES TERMINE\n"+
                        "\t Le tour peut commencer!\n"+
                        "================================\n"
        );
    }

    /**
     * Méthode percevoirRessource() ==> void
     * ===================================
     *
     * Cette méthode permet la perception des ressources (or, merveilles, ...)
     */
    private void percevoirRessource(int perso){
        if(this.plateauDeJeu.getPersonnage(perso).getJoueur()!=null && this.plateauDeJeu.getPersonnage(perso).getJoueur().getNom()=="Joueur1") {
            System.out.println("Que souhaitez vous faire ?");
            System.out.println("1 Prendre deux pièces d'or (vous en avez "+this.plateauDeJeu.getPersonnage(perso).getJoueur().nbPieces()+" dans votre trésor)");
            System.out.println("2 piocher deux quartier et en garder un (vous en avez "+this.plateauDeJeu.getPersonnage(perso).getJoueur().nbQuartiersDansMain()+" dans votre main et "+this.plateauDeJeu.getPersonnage(perso).getJoueur().nbQuartiersDansCite()+" dans votre cité)");
            int choix=Interaction.lireUnEntier(1, 3);
            if(choix==1) {
                this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterPieces(1);
                //this.plateauDeJeu.getJoueur(perso).ajouterPieces(2);
                System.out.println("Vous avez maintenant "+this.plateauDeJeu.getPersonnage(perso).getJoueur().nbPieces()+" pièces dans votre trésor.");
            } else if(choix==2) {
                if (this.plateauDeJeu.getPersonnage(perso).getJoueur().quartierPresentDansCite("Bibliothèque")) {
                    System.out.println("Grace à la carte Bibliothèque, vous gardez les deux quartiers suivants :");
                    Quartier quartier1=this.plateauDeJeu.getPioche().piocher();
                    System.out.println("1 "+quartier1.getNom()+" ,type: "+quartier1.getType()+" ,coût:"+quartier1.getCout());
                    this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier1);
                    Quartier quartier2=this.plateauDeJeu.getPioche().piocher();
                    System.out.println("2 "+quartier2.getNom()+" ,type: "+quartier2.getType()+" ,coût:"+quartier2.getCout());
                    this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier2);
                } else {
                    Quartier quartier1=this.plateauDeJeu.getPioche().piocher();
                    System.out.println("1 "+quartier1.getNom()+" ,type: "+quartier1.getType()+" ,coût:"+quartier1.getCout());
                    Quartier quartier2=this.plateauDeJeu.getPioche().piocher();
                    System.out.println("2 "+quartier2.getNom()+" ,type: "+quartier2.getType()+" ,coût:"+quartier2.getCout());
                    System.out.println("Lequel gardez vous ?");
                    int garder=Interaction.lireUnEntier(1, 3);
                    if(garder==1){
                        this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier1);
                        this.plateauDeJeu.getPioche().ajouter(quartier2);
                    } else if(garder==2) {
                        this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier2);
                        this.plateauDeJeu.getPioche().ajouter(quartier1);
                    }
                }
            }
        } else if(this.plateauDeJeu.getPersonnage(perso).getJoueur()!=null) {
            int choix=generateur.nextInt(2);
            if(choix==0) {
                System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(perso).getJoueur().getNom()+" prends 2 pièces.");
                this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterPieces(2);
            } else if(choix==1) {
                System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(perso).getJoueur().getNom()+" prends 1 quartier.");
                Quartier quartier1=this.plateauDeJeu.getPioche().piocher();
                Quartier quartier2=this.plateauDeJeu.getPioche().piocher();
                if (this.plateauDeJeu.getPersonnage(perso).getJoueur().quartierPresentDansCite("Bibliothèque")) {
                    this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier1);
                    this.plateauDeJeu.getPioche().ajouter(quartier2);
                    this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier2);
                    this.plateauDeJeu.getPioche().ajouter(quartier1);
                } else {
                    int garder=generateur.nextInt(2);
                    if(garder==0){
                        this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier1);
                        this.plateauDeJeu.getPioche().ajouter(quartier2);
                    } else if(garder==1) {
                        this.plateauDeJeu.getPersonnage(perso).getJoueur().ajouterQuartierDansMain(quartier2);
                        this.plateauDeJeu.getPioche().ajouter(quartier1);
                    }
                }

            }
        }
    }

    /**
     * Méthode calculDesPoints() ==> void
     * ===================================
     *
     * Cette méthode permet le calcul des points en fin de partie
     */
    private void calculDesPoints(Joueur winner){
        System.out.println(
                "\n================================\n"+
                        "\tFIN DE LA PARTIE\n"+
                        "\t" + this.nbTour + " TOURS COMPLETES\n"+
                        "\t" + winner.getNom() + " est le premier à possèder une cité complète\n"
        );
        System.out.println("\t== SCORE FINAUX ==");
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            Joueur joueur = this.plateauDeJeu.getJoueur(i);
            String[] typeQuartierCopy = Quartier.TYPE_QUARTIERS.clone();
            Boolean typeBonus = true;
            int points = 0;
            if(joueur.equals(winner))
                points += 4;
            else if(joueur.nbQuartiersDansCite() >= 7)
                points += 2;
            for(int j = 0; j < joueur.nbQuartiersDansCite(); j++){
                Quartier quartier = joueur.getCite()[i];
                if(quartier instanceof Quartier)
                    points += quartier.getCout();
                if(joueur.quartierPresentDansCite("Fontaine aux Souhaits") && quartier.getType().equals(Quartier.TYPE_QUARTIERS[4]))
                    points += 1;
                for(int k = 0; k < typeQuartierCopy.length; k++){
                    if(quartier.getType().equals(typeQuartierCopy[k]))
                        typeQuartierCopy[k] = null;
                }
            }
            for(String type : typeQuartierCopy){
                if(type != null){
                    typeBonus = false;
                    break;
                }
            }
            if(typeBonus)
                points += 3;
            // gestion des merveilles
            if(joueur.quartierPresentDansCite("Dracoport"))
                points += 2;
            if(joueur.quartierPresentDansCite("Salle des cartes"))
                points += joueur.nbQuartiersDansMain();
            if(joueur.quartierPresentDansCite("Statue Equestre") && joueur.getPossedeCouronne())
                points += 5;
            if(joueur.quartierPresentDansCite("Trésor Impérial"))
                points += joueur.nbPieces();
            System.out.println("\t" + joueur.getNom() + " - " + points + " points.");
        }
    }
}
