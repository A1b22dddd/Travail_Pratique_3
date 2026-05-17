
/**
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe Balle
 *
 * Cette classe represente une balle dans le jeu de balles rebondissantes.
 * Chaque balle est un cercle qui affiche l'image correspondant a son type
 * (Pierre, Papier ou Ciseaux). Elle possede une position, une vitesse
 * horizontale et verticale, et peut se transformer en un autre type
 * lors d'une collision selon les regles Pierre-Papier-Ciseaux.
 */



package csi.travail_pratique_3.modele;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;



public class Balle {

    /** Forme circulaire de la balle affichee dans la zone de jeu. */
    private Circle forme;

    /** Type de la balle : PIERRE, PAPIER ou CISEAU. */
    private TypeBalle type;

    /** Vitesse horizontale de la balle en pixels par frame. */
    private double dx;

    /** Vitesse verticale de la balle en pixels par frame. */
    private double dy;

    /** Rayon fixe de toutes les balles en pixels. */
    private static final double RAYON = 25;

    /**
     * Cree une nouvelle balle a la position donnee avec le type et la vitesse specifies.
     *
     * @param x   la position horizontale initiale du centre de la balle
     * @param y   la position verticale initiale du centre de la balle
     * @param type le type de la balle (PIERRE, PAPIER ou CISEAU)
     * @param dx  la vitesse horizontale initiale
     * @param dy  la vitesse verticale initiale
     */
    public Balle(double x, double y, TypeBalle type, double dx, double dy) {
        this.type = type;
        this.dx = dx;
        this.dy = dy;

        // Creer un cercle avec le rayon fixe
        this.forme = new Circle(RAYON);

        // Position initiale du centre du cercle
        this.forme.setCenterX(x);
        this.forme.setCenterY(y);

        // Appliquer l'image correspondant au type comme remplissage du cercle
        appliquerImage(type);
    }

    /**
     * Applique l'image correspondant au type comme remplissage du cercle.
     * L'image est chargee depuis le dossier des ressources.
     *
     * @param type le type de la balle dont on veut appliquer l'image
     */
    private void appliquerImage(TypeBalle type) {
        String chemin = switch (type) {
            case PIERRE -> "/csi/travail_pratique_3/images/pierre.jpg";
            case PAPIER -> "/csi/travail_pratique_3/images/papier.jpg";
            case CISEAU -> "/csi/travail_pratique_3/images/ciseau.jpg";
        };

        var url = getClass().getResource(chemin);
        if (url != null) {
            Image image = new Image(url.toExternalForm());
            forme.setFill(new ImagePattern(image));
        }
    }

    /**
     * Retourne la forme circulaire de la balle pour l'affichage.
     *
     * @return le cercle representant la balle
     */
    public Circle getForme() {
        return forme;
    }

    /**
     * Retourne le type actuel de la balle.
     *
     * @return le type de la balle (PIERRE, PAPIER ou CISEAU)
     */
    public TypeBalle getType() {
        return type;
    }

    /**
     * Retourne la vitesse horizontale de la balle.
     *
     * @return la vitesse horizontale en pixels par frame
     */
    public double getDx() {
        return dx;
    }

    /**
     * Retourne la vitesse verticale de la balle.
     *
     * @return la vitesse verticale en pixels par frame
     */
    public double getDy() {
        return dy;
    }

    /**
     * Modifie la vitesse horizontale de la balle.
     * Utilise lors des rebonds sur les murs gauche et droit.
     *
     * @param dx la nouvelle vitesse horizontale
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Modifie la vitesse verticale de la balle.
     * Utilise lors des rebonds sur les murs du haut et du bas.
     *
     * @param dy la nouvelle vitesse verticale
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Transforme la balle en un nouveau type lors d'une collision.
     * Met a jour le type et applique la nouvelle image correspondante.
     *
     * @param nouveauType le nouveau type de la balle apres transformation
     */
    public void transformerEn(TypeBalle nouveauType) {
        this.type = nouveauType;
        appliquerImage(nouveauType);
    }
}