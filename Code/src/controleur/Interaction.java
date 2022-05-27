package controleur;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Interaction {
    private static Scanner sc = new Scanner(System.in);

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

    // renvoie un entier lu au clavier compris dans l'intervalle
    //     [borneMin, borneMax[
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

    // lit les r?ponses "oui", "non", "o" ou "n" et renvoie un bool?en
    public static boolean lireOuiOuNon() {
        boolean retour = true;
        boolean continu = true;
        String i = null;

        do {
            try {
                i = sc.nextLine();
                continu = !(i.equals("yes")||i.equals("y")||i.equals("no")||i.equals("n"));

            } catch (InputMismatchException e) {
                System.out.print("Veuillez rentrer un chiffre : ");
                sc.next();
            }
        } while(continu);

        switch (i){
            case "yes":
                retour = true;
                break;

            case "y":
                retour = true;
                break;

            case "no":
                retour = false;
                break;

            case "n":
                retour = false;
                break;
        }
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