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

    private void afficherLesRegles(){
        System.out.println("Voici les regles !!");
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
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            if(this.plateauDeJeu.getJoueur(i).getPersonnage() instanceof Roi)
                this.plateauDeJeu.getJoueur(i).setPossedeCouronne(true);
        }
    }

    private void reinitialisationPersonnages(){
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++)
            this.plateauDeJeu.getPersonnage(i).reinitialiser();
    }

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


    private void tourDeJeu() {
        choixPersonnages();
        for(int i=0;i<this.plateauDeJeu.getNombrePersonnages();i++) {
            System.out.println("C'est au tour du personnage de rang "+this.plateauDeJeu.getPersonnage(i).getRang()+" : "+this.plateauDeJeu.getPersonnage(i).getNom());
            if (this.plateauDeJeu.getPersonnage(i).getVole() && this.plateauDeJeu.getPersonnage(i).getJoueur()!=null) {
                System.out.println("le personnage "+this.plateauDeJeu.getPersonnage(i).getNom()+" a été volé");
                for(int j=0;j<this.plateauDeJeu.getNombrePersonnages();j++) {
                    if(this.plateauDeJeu.getPersonnage(j).getCaracteristiques()==Caracteristiques.VOLEUR) {
                        this.plateauDeJeu.getPersonnage(j).getJoueur().ajouterPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces());
                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces());
                    }
                }
            } else if(this.plateauDeJeu.getPersonnage(i).getVole() && this.plateauDeJeu.getPersonnage(i).getJoueur()==null){
                System.out.println("Le personnage n'est pas attribué, le voleur ne reçoit pas de pièces");
            }
            if (this.plateauDeJeu.getPersonnage(i).getAssassine()) {
                System.out.println("Personnage assassiné, le tour passe");
            } else {
                if(this.plateauDeJeu.getPersonnage(i).getJoueur()!=null && this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()=="Player1") {
                    percevoirRessource(plateauDeJeu.getJoueur(i));
                    this.plateauDeJeu.getPersonnage(i).percevoirRessourcesSpecifiques();
                    System.out.println("Utilisation du pouvoir de votre personnage :");
                    this.plateauDeJeu.getPersonnage(i).utiliserPouvoir();
                    if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Forge") && this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces()>=2) {
                        System.out.println("Voulez-vous payer 2 pièces d'or pour piocher 3 cartes ?");
                        if(Interaction.lireOuiOuNon()) {
                            this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(2);
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
                        }
                    }
                    if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Laboratoire") && this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()>=1) {
                        System.out.println("Voulez-vous défausser une carte pour recevoir 2 pièces d'or ?");
                        if(Interaction.lireOuiOuNon()) {
                            for (int j = 0; j < this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain(); j++) {
                                System.out.println((j+1)+" "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getNom()+" (coût:"+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getCout()+")");
                            }
                            int choix=Interaction.lireUnEntier(1,this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()+1);
                            this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(choix-1);
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterPieces(2);
                        }
                    }
                    System.out.println("Voulez vous construire ?");
                    if(Interaction.lireOuiOuNon()) {
                        System.out.println("Quel quartier voulez vous construire ?");
                        for(int j=0;j<this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain();j++) {
                            System.out.println((j+1)+" "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getNom()+" (coût:"+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getCout()+")");
                        }
                        boolean continu=true;
                        do {
                            try {
                                int choix=Interaction.lireUnEntier(0, this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().size()+1);
                                if(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()>this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces()) {
                                    System.out.println("Coût trop élevé");
                                    throw new Exception();
                                } else if(this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getNom()) && !this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Carrière")) {
                                    System.out.println("Vous ne pouvez pas construire deux fois le même quartier.");
                                    throw new Exception();
                                } else if(choix==0){
                                    continu=false;
                                } else {
                                    this.plateauDeJeu.getPersonnage(i).construire(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1));
                                    System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()+" construit le quartier "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getNom());
                                    if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Manufacture") && this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getType()==Quartier.TYPE_QUARTIERS[4]) {
                                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()-1);
                                    } else if(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getNom()=="Tripot") {
                                        System.out.println("Vous pouvez payer la carte Tripot avec des cartes quartier,");
                                        System.out.println("Souhaitez vous le faire ?");
                                        if (Interaction.lireOuiOuNon()) {
                                            System.out.println("Combien de quartiers souhaitez vous défausser");
                                            int nbDefausse=Interaction.lireUnEntier(1, this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()+1);
                                            for (int k = 0; k < nbDefausse; k++) {
                                                for(int j=0;j<this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain();j++) {
                                                    System.out.println((j+1)+" "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getNom()+" (coût:"+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getCout()+")");
                                                }
                                                System.out.println("Choisissez le quartier :");
                                                int defausse=Interaction.lireUnEntier(1,this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()+1);
                                                this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(defausse-1);
                                            }
                                            this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()-nbDefausse);
                                        } else {
                                            this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout());
                                        }
                                    } else {
                                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout());
                                    }
                                    this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(choix-1);
                                    continu=false;
                                }
                            } catch (Exception e) {
                                System.out.println("Votre choix :");
                            }
                        } while (continu);
                        if(this.plateauDeJeu.getPersonnage(i).getCaracteristiques()==Caracteristiques.ARCHITECTE) {
                            for(int k=2;k<4;k++) {
                                System.out.println("Voulez vous construire un "+(k)+"eme quartier ?");
                                if (Interaction.lireOuiOuNon()) {
                                    System.out.println("Quel quartier voulez vous construire ? (0 pour ne rien faire)");
                                    for(int j=0;j<this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansCite();j++) {
                                        System.out.println((j+1)+" "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getNom()+" (coût:"+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(j).getCout()+")");
                                    }
                                    continu=true;
                                    do {
                                        try {
                                            int choix=Interaction.lireUnEntier(0, this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().size()+1);
                                            if(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()>this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces()) {
                                                System.out.println("Coût trop élevé");
                                                throw new Exception();
                                            } else if(this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getNom()) && !this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Carrière")) {
                                                System.out.println("Vous ne pouvez pas construire deux fois le même quartier.");
                                                throw new Exception();
                                            } else if(choix==0){
                                                continu=false;
                                            } else {
                                                this.plateauDeJeu.getPersonnage(i).construire(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1));
                                                System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()+" construit le quartier "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getNom());
                                                if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Manufacture") && this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getType()==Quartier.TYPE_QUARTIERS[4]) {
                                                    this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()-1);
                                                } else if(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getNom()=="Tripot") {
                                                    int rand=generateur.nextInt(2);
                                                    if (rand==1) {
                                                        int nbDefausse=generateur.nextInt(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()+1);
                                                        for (int j = 0; j < nbDefausse; j++) {
                                                            for(int j2=0;j2<this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain();j2++) {
                                                                int defausse=generateur.nextInt(this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()+1);
                                                                this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(defausse);
                                                            }
                                                        }
                                                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout()-nbDefausse);
                                                    } else {
                                                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout());
                                                    }
                                                } else {
                                                    this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix-1).getCout());
                                                }
                                                this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(choix-1);
                                                continu=false;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Votre choix :");
                                        }
                                    } while (continu);
                                }
                            }
                        }
                        if (this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansCite()>=7 && !this.citeCompletePremier) {
                            this.citeCompletePremier=true;
                            this.nomJoueurCiteCompletePremier=this.plateauDeJeu.getPersonnage(i).getJoueur().getNom();
                        }
                    }
                } else if(this.plateauDeJeu.getPersonnage(i).getJoueur()!=null) {
                    percevoirRessource(plateauDeJeu.getJoueur(i));
                    this.plateauDeJeu.getPersonnage(i).percevoirRessourcesSpecifiques();
                    System.out.println("Le personnage utilise son pouvoir");
                    this.plateauDeJeu.getPersonnage(i).utiliserPouvoirAvatar();
                    int rand=0;
                    if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Forge") && this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces()>=2) {
                        rand=generateur.nextInt(2);
                        if(rand==1) {
                            System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()+" utilise sa merveille Forge");
                            this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(2);
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
                            this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
                        }
                        if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Laboratoire") && this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()>=1) {
                            rand=generateur.nextInt(2);
                            if(rand==1) {
                                System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()+" utilise sa merveille Laboratoire");
                                rand=generateur.nextInt(this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansMain()+1);
                                this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(rand);
                                this.plateauDeJeu.getPersonnage(i).getJoueur().ajouterPieces(2);
                            }
                        }
                    }
                    rand=generateur.nextInt(2);
                    if(rand==1) {
                        boolean continu=true;
                        do {
                            try {
                                int choix=generateur.nextInt(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().size()+2);
                                int securite=0;
                                System.out.println(choix);
                                if(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getCout()>this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces()) {
                                    securite++;
                                    throw new Exception();
                                } else if(this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getNom()) && !this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Carrière")) {
                                    securite++;
                                    throw new Exception();
                                } else if(securite>30){
                                    continu=false;
                                } else {
                                    this.plateauDeJeu.getPersonnage(i).construire(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix));
                                    System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()+" construit le quartier "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getNom()+" dans sa cité.");
                                    if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Manufacture") && this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getType()==Quartier.TYPE_QUARTIERS[4]) {
                                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getCout()-1);
                                    } else {
                                        this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getCout());
                                    }
                                    this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(choix);
                                    continu=false;
                                }
                            } catch (Exception e) {}
                        } while (continu);
                        if(this.plateauDeJeu.getPersonnage(i).getCaracteristiques()==Caracteristiques.ARCHITECTE) {
                            for(int k=1;k<3;k++) {
                                rand=generateur.nextInt(2);
                                if (rand==1) {
                                    continu=true;
                                    do {
                                        try {
                                            int choix=generateur.nextInt(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().size()+2);
                                            int securite=0;
                                            if(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getCout()>this.plateauDeJeu.getPersonnage(i).getJoueur().nbPieces()) {
                                                securite++;
                                                throw new Exception();
                                            } else if(this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getNom()) && !this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Carrière")) {
                                                securite++;
                                                throw new Exception();
                                            } else if(securite>20){
                                                continu=false;
                                            } else {
                                                this.plateauDeJeu.getPersonnage(i).construire(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix));
                                                System.out.println("Le joueur "+this.plateauDeJeu.getPersonnage(i).getJoueur().getNom()+" construit le quartier "+this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getNom());
                                                if (this.plateauDeJeu.getPersonnage(i).getJoueur().quartierPresentDansCite("Manufacture") && this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getType()==Quartier.TYPE_QUARTIERS[4]) {
                                                    this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getCout()-1);
                                                } else {
                                                    this.plateauDeJeu.getPersonnage(i).getJoueur().retirerPieces(this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().get(choix).getCout());
                                                }
                                                this.plateauDeJeu.getPersonnage(i).getJoueur().getMain().remove(choix);
                                                continu=false;
                                            }
                                        } catch (Exception e) {}
                                    } while (continu);
                                } else {
                                    System.out.println("L'Architecte ne construit pas de quartier");
                                }
                            }
                        }
                        if (this.plateauDeJeu.getPersonnage(i).getJoueur().nbQuartiersDansCite()>=7 && !this.citeCompletePremier) {
                            this.citeCompletePremier=true;
                            this.nomJoueurCiteCompletePremier=this.plateauDeJeu.getPersonnage(i).getJoueur().getNom();
                        }
                    } else {
                        System.out.println("Le personnage ne construit pas de quartier");
                    }
                }
            }
        }
    }

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

        // ^ a refaire

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

    private void percevoirRessource(Joueur j){
        System.out.println("\n       == PERCEPTION DES RESSOURCES ==\n");

        System.out.println(
                "Vous disposez de :\n" +
                        "\t" + j.nbPieces() + " pièces d'or\n"+
                        "\t" + j.nbQuartiersDansMain() + " quartiers en main\n"
        );

        System.out.print(
                "\nQue souhaitez vous percevoir ? \n\n" +
                        "\t1 - 2 pièces d'or\n" +
                        "\t2 - Piocher \n" +
                        "\nVotre choix > "
        );
        int choixPerception;
        if(j.getNom().contains("PNJ"))
            choixPerception = Interaction.intRandom(1, 3);
        else
            choixPerception = Interaction.lireUnEntier(1, 3);

        if(choixPerception == 1){
            j.getPersonnage().ajouterPieces();
            System.out.println("\n2 PO ajoutés à votre trésor.");
        } else if(choixPerception == 2) {
            Quartier[] choixQuartiers = new Quartier[2];

            System.out.println(
                    "\nCartes piochées disponibles :\n"+
                            "\t n° - nom | type | coût \n"
            );

            for(int i = 0; i < choixQuartiers.length; i++){
                choixQuartiers[i] = this.plateauDeJeu.getPioche().piocher();
                System.out.println("\t" + (i+1) + " - " + choixQuartiers[i].getNom() + " | " + choixQuartiers[i].getType() + " | " + choixQuartiers[i].getCout() + " PO");
            }

            // Gestion de la merveille bibliothque.
            if(j.quartierPresentDansCite("Bibliotheque")){
                System.out.println("\t▀▀ MERVEILLE BIBLIOTHEQUE ▀▀\n"+
                        "Les deux cartes ont été ajoutés à votre main"
                );

                for(int i = 0; i < choixQuartiers.length; i++)
                    j.ajouterQuartierDansCite(choixQuartiers[i]);

            } else {
                System.out.print("Votre choix > ");
                int choixPioche;
                if(j.getNom().contains("PNJ"))
                    choixPioche = Interaction.intRandom(1, 3);
                else
                    choixPioche = Interaction.lireUnEntier(1, 3);

                j.ajouterQuartierDansMain(choixQuartiers[choixPioche-1]);
                this.plateauDeJeu.getPioche().ajouter(choixQuartiers[choixPioche % 2]);

                System.out.println("\n" + choixQuartiers[choixPioche-1].getNom() + " ajoutée à votre main.\n");
            }

        }
    }
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
