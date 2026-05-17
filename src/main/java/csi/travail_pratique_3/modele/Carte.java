
/**
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe Carte
 *
 * Cette classe represente une carte dans le jeu de memoire.
 * Chaque carte possede un identifiant de paire qui permet de determiner
 * si deux cartes retournees sont identiques, un nom de fichier image
 * representant un animal, et deux etats : retournee (face visible)
 * et trouvee (paire completee). Deux cartes partagent le meme
 * identifiant de paire dans le jeu.
 */


package csi.travail_pratique_3.modele;


public class Carte {

    /** Identifiant unique de la paire — deux cartes partagent le meme identifiant. */
    private int idPaire;

    /** Nom du fichier image associe a la carte (ex: carte1.jpg). */
    private String nomImage;

    /** Indique si la carte est actuellement retournee, face visible pour le joueur. */
    private boolean estRetournee;

    /** Indique si la paire a ete trouvee et que la carte reste visible definitivement. */
    private boolean estTrouvee;

    /**
     * Cree une nouvelle carte avec son identifiant de paire et son image.
     * La carte est creee face cachee et non trouvee par defaut.
     *
     * @param idPaire  l'identifiant de la paire partagee par deux cartes identiques
     * @param nomImage le nom du fichier image associe a la carte
     */
    public Carte(int idPaire, String nomImage) {
        this.idPaire = idPaire;
        this.nomImage = nomImage;
        this.estRetournee = false;
        this.estTrouvee = false;
    }

    /**
     * Retourne l'identifiant de la paire de la carte.
     *
     * @return l'identifiant de la paire
     */
    public int getIdPaire() {
        return idPaire;
    }

    /**
     * Retourne le nom du fichier image associe a la carte.
     *
     * @return le nom du fichier image
     */
    public String getNomImage() {
        return nomImage;
    }

    /**
     * Indique si la carte est actuellement retournee face visible.
     *
     * @return true si la carte est retournee, false sinon
     */
    public boolean estRetournee() {
        return estRetournee;
    }

    /**
     * Modifie l'etat de retournement de la carte.
     *
     * @param retournee true pour retourner la carte face visible, false pour la cacher
     */
    public void setRetournee(boolean retournee) {
        this.estRetournee = retournee;
    }

    /**
     * Indique si la paire de cette carte a ete trouvee.
     *
     * @return true si la paire a ete trouvee, false sinon
     */
    public boolean estTrouvee() {
        return estTrouvee;
    }

    /**
     * Marque la carte comme trouvee et la garde face visible definitivement.
     *
     * @param trouvee true pour marquer la carte comme trouvee
     */
    public void setTrouvee(boolean trouvee) {
        this.estTrouvee = trouvee;
        this.estRetournee = true;
    }
}