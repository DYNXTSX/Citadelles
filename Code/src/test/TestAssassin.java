package test;
import modele.Assassin;
import modele.PlateauDeJeu;
import modele.Roi;

public class TestAssassin {
    public static void main(String[] args) {
        TestAssassin test = new TestAssassin();
        //test.test1();
        //test.test2();
        test.test3();
    }

    public void test1() {
        System.out.println("TEST DU CONSTRUCTEUR");
        PlateauDeJeu plateau = new PlateauDeJeu();
        Roi roi = new Roi();
        plateau.ajouterPersonnage(roi);
        Assassin assassin = new Assassin();
        plateau.ajouterPersonnage(assassin);
        Test.test(plateau.getNombrePersonnages()== 2,"nombre de joueurs");
        Test.test(plateau.getPersonnage(1)==assassin,
                "récupération du personnage de l'assassin");
        Test.test(plateau.getPersonnage(1).getRang()==1,
                "récupération du rang");
    }
    public void test2() {
        System.out.println("TEST DE L'ASSASSINAT DU ROI");
        PlateauDeJeu plateau = new PlateauDeJeu();
        Roi roi = new Roi();
        plateau.ajouterPersonnage(roi);
        Assassin assassin = new Assassin();
        plateau.ajouterPersonnage(assassin);

        assassin.utiliserPouvoir();
        Test.test(roi.getAssassine(),"le roi est assassiné");
    }
    public void test3() {
        System.out.println("TEST DE L'ASSASSINAT DU ROI");
        PlateauDeJeu plateau = new PlateauDeJeu();
        Roi roi = new Roi();
        plateau.ajouterPersonnage(roi);
        Assassin assassin = new Assassin();
        plateau.ajouterPersonnage(assassin);
    }
}
