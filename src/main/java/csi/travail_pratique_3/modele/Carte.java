package csi.travail_pratique_3.modele;

/**
 * Représente une carte du jeu de mémoire.
 * Chaque carte possède un identifiant, une image et un état (retournée ou trouvée).
 */
public class Carte {

    // Identifiant unique de la paire (2 cartes partagent le même id)
    private int idPaire;

    // Nom du fichier image associé à la carte
    private String nomImage;

    // Indique si la carte est actuellement retournée (face visible)
    private boolean estRetournee;

    // Indique si la paire a été trouvée
    private boolean estTrouvee;

    public Carte(int idPaire, String nomImage) {
        this.idPaire = idPaire;
        this.nomImage = nomImage;
        this.estRetournee = false;
        this.estTrouvee = false;
    }

    public int getIdPaire() {
        return idPaire;
    }

    public String getNomImage() {
        return nomImage;
    }

    public boolean estRetournee() {
        return estRetournee;
    }

    public void setRetournee(boolean retournee) {
        this.estRetournee = retournee;
    }

    public boolean estTrouvee() {
        return estTrouvee;
    }

    public void setTrouvee(boolean trouvee) {
        this.estTrouvee = trouvee;
        this.estRetournee = true;
    }
}