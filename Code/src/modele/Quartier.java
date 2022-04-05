package modele;

import java.util.Arrays;
import java.util.List;

public class Quartier {
    private String nom;
    private String type;
    private Integer coutConstruction;
    private String caracteristiques;
    public static final String[] TYPE_QUARTIERS = {"RELIGIEUX", "MILITAIRE", "NOBLE", "COMMERCANT", "MERVEILLE"};

    public Quartier(String nom, String type, Integer cout, String caracteristiques){
        setNom(nom);
        setType(type);
        setCout(cout);
        setCaracteristiques(caracteristiques);
    }

    public Quartier(String nom, String type, Integer cout){
        setNom(nom);
        setType(type);
        setCout(cout);
    }

    public Quartier(){

    }

    public String getNom() {
        return nom != null ? nom : "";
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type != null ? type : "";
    }

    public void setType(String type) {
        if(Arrays.asList(TYPE_QUARTIERS).contains(type))
            this.type = type;
        else
            this.type = "";
    }

    public int getCout() {
        return coutConstruction != null ? coutConstruction : 0;
    }

    public void setCout(Integer coutConstruction) {
        if( (coutConstruction >= 1) && (coutConstruction <= 6))
            this.coutConstruction = coutConstruction;
        else
            this.coutConstruction = 0;
    }

    public String getCaracteristiques() {
        return caracteristiques != null ? caracteristiques : "";
    }

    public void setCaracteristiques(String caracteristiques) {
        this.caracteristiques = caracteristiques;
    }
}
