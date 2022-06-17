package extension.complete;
import modele.Quartier;

public class Parc extends Quartier {
    public Parc(){
        super("Parc","MERVEILLE",6,"Si vous n’avez aucune carte en main à la fin de votre tour, piochez 2 cartes. Si la Sorcière ne reprend pas son tour, elle ne peut pas utiliser l’effet du Parc à la fin de son tour.");
    }
}