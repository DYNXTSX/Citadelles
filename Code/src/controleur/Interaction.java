package controleur;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Interaction {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Méthode lireUnEntier() ==> int
     * ==============================
     *
     * Cette méthode demande à l'utilisateur d'entrer un entier.
     *
     * @return ==> entier choisi par l'utilisateur
     */
    public static int lireUnEntier() {
        int i = 0;
        boolean continu = true;
        do {
            try {
                i = sc.nextInt();
                continu = false;
            } catch (InputMismatchException e) {
                System.out.print("Veuillez rentrer un chiffre : ");
                sc.next(); // passe l'entier pour ?viter de boucler
            }
        } while(continu);
        return i;
    }

    /**
     * Méthode lireUnEntier(int borneMin, int borneMax) ==> int
     * ==============================
     *
     * Renvoie un entier lu au clavier compris dans l'intervalle.
     *
     * @param borneMin ==> borne minimum [borneMin,
     * @param borneMax ==> borne max ,borneMax[
     * @return ==> entier
     */
    public static int lireUnEntier(int borneMin, int borneMax) {
        int i = 0;
        boolean continu = true;
        do {
            try {
                i = sc.nextInt();
                continu = !((i >= borneMin)&&(i<borneMax));
            } catch (InputMismatchException e) {
                System.out.print("Veuillez rentrer un chiffre : ");
                sc.next();
            }
        } while(continu);
        return i;
    }

    /**
     *
     * @return
     */
    public static boolean lireOuiOuNon() {
        String reponse;
        boolean continu=true, retour=false;
        do {
            try {
                reponse=sc.nextLine();
                if(reponse.equals("oui")||reponse.equals("o")) {
                    retour=true;
                    continu=false;
                } else if(reponse.equals("non")||reponse.equals("n")) {
                    retour=false;
                    continu=false;
                } else {
                    throw new Exception();
                }

            } catch (Exception e) {
                System.out.print("N'accepte que \"oui\", \"non\", \"o\" ou \"n\" : ");
            }
        }while(continu);
        return retour;
    }

    // renvoie une cha?ne de caract?re lue au clavier:
    public static String lireUneChaine() {
        String retour = "";
        boolean continu = true;

        do {
            try {
                retour = sc.nextLine();
                continu = retour.length() == 0;
            } catch (InputMismatchException e) {
                System.out.print("Veuillez rentrer un chiffre : ");
                sc.next();
            }
        } while(continu);

        return retour;
    }

    public static int intRandom(int a, int b){
        Random rand = new Random();
        return rand.ints(a, b).findFirst().getAsInt();
    }

    public static Boolean boolRandom(){
        Random rand = new Random();
        return rand.nextBoolean();
    }



}