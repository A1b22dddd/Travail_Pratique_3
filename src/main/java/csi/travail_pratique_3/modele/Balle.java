package csi.travail_pratique_3.modele;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Balle {

    private ImageView forme;
    private TypeBalle type;
    private double dx;
    private double dy;

    public Balle(double x, double y, TypeBalle type, double dx, double dy) {
        this.type = type;
        this.dx = dx;
        this.dy = dy;

        this.forme = new ImageView(getImage(type));

        this.forme.setFitWidth(40);
        this.forme.setFitHeight(40);

        this.forme.setLayoutX(x);
        this.forme.setLayoutY(y);
    }

    public ImageView getForme() {
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
        this.forme.setImage(getImage(nouveauType));
    }

    private Image getImage(TypeBalle type) {
        String chemin = switch (type) {
            case PIERRE -> "/csi/travail_pratique_3/images/pierre.jpg";
            case PAPIER -> "/csi/travail_pratique_3/images/papier.jpg";
            case CISEAU -> "/csi/travail_pratique_3/images/ciseau.jpg";
        };

        var url = getClass().getResource(chemin);
        System.out.println("URL trouvée : " + url);
        return new Image(url.toExternalForm());
    }
}