package modele;

abstract public class Personnage {
    private String nom;
    private Integer rang = 0;
    private String caracteristiques;
    private Joueur joueur;
    private Boolean assasine = false;
    private Boolean vole = false;
    private modele.PlateauDeJeu PlateauDeJeu;

    public Personnage(String nom, Integer rang, String caracteristiques){
        this.nom = nom;
        this.rang = rang;
        this.caracteristiques = caracteristiques;
        this.joueur = null;
        this.vole = false;
        this.assasine = false;
    }

    public String getNom() {
        return nom;
    }
    public Integer getRang() {
        return rang;
    }
    public String getCaracteristiques() {
        return caracteristiques;
    }
    public Joueur getJoueur() {
        return joueur != null ? joueur : null;
    }
    public Boolean getAssassine() {
        return assasine;
    }
    public Boolean getVole() {
        return vole;
    }

    public void setJoueur(Joueur j) {
        this.joueur = j;
    }
    public void setVole() {
        this.vole = true;
    }
    public void setAssassine(){
        this.assasine = true;
    }

    public void ajouterPieces(){
        if(joueur != null && !assasine){
            joueur.ajouterPieces(2);
        }
    }
    public void ajouterQuartier(Quartier nouveau){
        if(joueur != null && !assasine){
            joueur.ajouterQuartierDansMain(nouveau);
        }
    }
    public void construire(Quartier nouveau){
        if(joueur != null && !assasine){
            joueur.ajouterQuartierDansCite(nouveau);
        }
    }

    public void percevoirRessourcesSpecifiques(){
        if(joueur != null && !assasine){
            System.out.println("aucune ressource sp�cifique");
        }
    }
    abstract public void utiliserPouvoir();
    abstract public void utiliserPouvoirAvatar();

    public void reinitialiser(){
        this.joueur = null;
        this.vole = false;
        this.assasine = false;
    }

    public PlateauDeJeu getPlateau() {
        return PlateauDeJeu != null ? PlateauDeJeu : null;
    }

    public void setPlateauDeJeu(modele.PlateauDeJeu plateauDeJeu) {
        PlateauDeJeu = plateauDeJeu;
    }
}
