package csi.travail_pratique_3.modele;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Balle {

    // Forme circulaire de la balle
    private Circle forme;
    private TypeBalle type;
    private double dx;
    private double dy;

    // Rayon de la balle
    private static final double RAYON = 25;

    public Balle(double x, double y, TypeBalle type, double dx, double dy) {
        this.type = type;
        this.dx = dx;
        this.dy = dy;

        // Créer un cercle
        this.forme = new Circle(RAYON);

        // Position initiale (centre du cercle)
        this.forme.setCenterX(x);
        this.forme.setCenterY(y);

        // Appliquer l'image comme remplissage du cercle
        appliquerImage(type);
    }

    /**
     * Applique l'image correspondante au type comme remplissage du cercle.
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

    public Circle getForme() {
        return forme;
    }

    public TypeBalle getType() {
        return type;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void transformerEn(TypeBalle nouveauType) {
        this.type = nouveauType;
        appliquerImage(nouveauType);
    }
}