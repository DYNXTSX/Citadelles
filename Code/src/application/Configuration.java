package application;
import modele.*;

public class Configuration {
    /**
     * Méthode nouvellePioche() ==> Pioche
     * ===================================
     *
     * Cette méthode permet de créer la pioche du jeu avec les quartiers.
     * @return la pioche du jeu
     */
    public static Pioche nouvellePioche() {
        Pioche p = new Pioche();
        Quartier q = new Quartier();

        for(int i = 0; i<5; i++) {
            if(i<2) {
                q = new Quartier("cathédrale",Quartier.TYPE_QUARTIERS[0],5);p.ajouter(q); //2
                q = new Quartier("forteresse",Quartier.TYPE_QUARTIERS[1],5);p.ajouter(q); //2
                q = new Quartier("hôtel de ville",Quartier.TYPE_QUARTIERS[3],5); p.ajouter(q);//2
            }
            if(i<3) {
                q = new Quartier("temple",Quartier.TYPE_QUARTIERS[0],1); p.ajouter(q); //3
                q = new Quartier("église",Quartier.TYPE_QUARTIERS[0],2); p.ajouter(q); //3
                q = new Quartier("monastère",Quartier.TYPE_QUARTIERS[0],3); p.ajouter(q); //3
                q = new Quartier("tour de guet",Quartier.TYPE_QUARTIERS[1],1); p.ajouter(q);//3
                q = new Quartier("prison",Quartier.TYPE_QUARTIERS[1],2); p.ajouter(q);//3
                q = new Quartier("caserne",Quartier.TYPE_QUARTIERS[1],3); p.ajouter(q);//3
                q = new Quartier("palais",Quartier.TYPE_QUARTIERS[2],5); p.ajouter(q);//3
                q = new Quartier("échoppe",Quartier.TYPE_QUARTIERS[3],2); p.ajouter(q);//3
                q = new Quartier("comptoir",Quartier.TYPE_QUARTIERS[3],3); p.ajouter(q);//3
                q = new Quartier("port",Quartier.TYPE_QUARTIERS[3],4); p.ajouter(q);//3
            }
            if(i<4) {
                q = new Quartier("château",Quartier.TYPE_QUARTIERS[2],4); p.ajouter(q);//4
                q = new Quartier("marché",Quartier.TYPE_QUARTIERS[3],2); p.ajouter(q);//4
            }
            q = new Quartier("manoir",Quartier.TYPE_QUARTIERS[2],3); p.ajouter(q);//5
            q = new Quartier("taverne",Quartier.TYPE_QUARTIERS[3],1); p.ajouter(q);//5
        }
        return p;
    }

    /**
     * Méthode configurationDeBase(Pioche p) ==> PlateauDeJeu
     * ======================================================
     *
     * Cette méthode permet de créer le plateau de jeu, avec les personnages, les joueurs et les merveilles.
     *
     * @param p ==> prend en paramètre une pioche
     * @return  ==> retourne le plateau de jeu
     */
    public static PlateauDeJeu configurationDeBase(Pioche p) {
        PlateauDeJeu plat = new PlateauDeJeu();
        Quartier q = new Quartier();

        //Ajout Des Personnages
        Assassin assassin = new Assassin();
        plat.ajouterPersonnage(assassin);
        Voleur voleur = new Voleur();
        plat.ajouterPersonnage(voleur);
        Magicienne magicienne = new Magicienne();
        plat.ajouterPersonnage(magicienne);
        Roi roi = new Roi();
        plat.ajouterPersonnage(roi);
        Eveque eveque = new Eveque();
        plat.ajouterPersonnage(eveque);
        Marchande marchande = new Marchande();
        plat.ajouterPersonnage(marchande);
        Architecte architecte = new Architecte();
        plat.ajouterPersonnage(architecte);
        Condottiere condottiere = new Condottiere();
        plat.ajouterPersonnage(condottiere);

        //Ajout Des Joueurs
        Joueur joueur = new Joueur("Joueur1");
        plat.ajouterJoueur(joueur);
        joueur = new Joueur("PNJ 2");
        plat.ajouterJoueur(joueur);
        joueur = new Joueur("PNJ 3");
        plat.ajouterJoueur(joueur);
        joueur = new Joueur("PNJ 4");
        plat.ajouterJoueur(joueur);

        //Ajout Merveilles
        q = new Quartier("bibliothèque", Quartier.TYPE_QUARTIERS[4],6,"Si vous choisissez de piocher des cartes au début du tour, conservez-les toutes.");
        p.ajouter(q);
        q = new Quartier("carrière", Quartier.TYPE_QUARTIERS[4],5,"Vous pouvez bâtir des quartiers identiques à d’autres quartiers de votre cit´e. Le propriétaire de la carrière peut bâtir autant de quartiers identiques qu’il le souhaite");
        p.ajouter(q);
        q = new Quartier("cour des miracles", Quartier.TYPE_QUARTIERS[4],2,"Pour le calcul du score final, la Cour des Miracles est considérée comme un quartier de type (couleur) de votre choix. Dans la cas où le propriétaire la considère comme un quartier noble, militaire, marchant ou religieux, la Cour des Miracles ne peut plus être considérée comme une merveille.");
        p.ajouter(q);
        q = new Quartier("donjon", Quartier.TYPE_QUARTIERS[4],3,"Le Donjon ne peut être affect´e par les pouvoirs des personnages de rang 8.");
        p.ajouter(q);
        q = new Quartier("dracoport", Quartier.TYPE_QUARTIERS[4],6,"Marquez 2 points supplémentaires à la fin de la partie.");
        p.ajouter(q);
        q = new Quartier("école de magie", Quartier.TYPE_QUARTIERS[4],6,"Pour la perception des revenus des personnages, l’Ecole de Magie est considérée comme un quartier du type (couleur) de votre choix.");
        p.ajouter(q);
        q = new Quartier("fontaine aux souhaits", Quartier.TYPE_QUARTIERS[4],5,"A la fin de la partie, marquez 1 point supplémentaire par merveille dans votre cité, y compris la Fontaine aux Souhaits. ");
        p.ajouter(q);
        q = new Quartier("forge", Quartier.TYPE_QUARTIERS[4],5,"Une fois par tour, vous pouvez payez 2 pièces d’or pour piocher 3 cartes");
        p.ajouter(q);
        q = new Quartier("laboratoire", Quartier.TYPE_QUARTIERS[4],5," Une fois par tour, vous pouvez défausser 1 carte pour recevoir 2 pièces d’or.");
        p.ajouter(q);
        q = new Quartier("manufacture", Quartier.TYPE_QUARTIERS[4],5," Payez 1 pièce d’or de moins lorsque vous bâtissez une autre merveille.");
        p.ajouter(q);
        q = new Quartier("salle des cartes", Quartier.TYPE_QUARTIERS[4],5,"A la fin de la partie, marquez 1 point suppl´ementaire par carte dans votre main.");
        p.ajouter(q);
        q = new Quartier("statue équestre", Quartier.TYPE_QUARTIERS[4],3," Si vous détenez le Couronne à la fin de la partie, marquez 5 points supplémentaires.");
        p.ajouter(q);
        q = new Quartier("trésor impérial", Quartier.TYPE_QUARTIERS[4],5," A la fin de la partie, marquez 1 point supplémentaire par pièce d’or dans votre trésor.");
        p.ajouter(q);
        q = new Quartier("tripot", Quartier.TYPE_QUARTIERS[4],6,"Vous pouvez payer tout ou partie du coût de construction du Tripot en cartes de votre main, au prix de 1 carte pour 1 pièce d’or. ");
        p.ajouter(q);

        p.melanger();
        plat.setPioche(p);

        return plat;
    }
}
