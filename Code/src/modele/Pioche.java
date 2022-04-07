package modele;

import java.util.ArrayList;
import java.util.Collections;

public class Pioche {
    private ArrayList<Quartier> liste;

    public Pioche(){
        this.liste = new ArrayList<Quartier>();
    }

    public Quartier piocher(){
        if(liste.size() == 0)
            return null;
        else{
            Quartier q = liste.get(0);
            liste.remove(0);
            return q;
        }
    }
    public void ajouter(Quartier nouveau){
        liste.add(nouveau);
    }
    public Integer nombreElements(){
        return liste.size();
    }
    public void melanger(){
        Collections.shuffle(liste);
    }

}
