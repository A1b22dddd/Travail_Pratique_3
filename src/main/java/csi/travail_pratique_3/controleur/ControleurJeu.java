package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import csi.travail_pratique_3.modele.Balle;
import csi.travail_pratique_3.modele.TypeBalle;

public class ControleurJeu implements ControleurAvecPrincipal {

    @FXML
    private Pane zoneJeu;

    private ControleurPrincipal principal;

    private final List<Balle> balles = new ArrayList<>();
    private final Random random = new Random();

    private int indexOrdre = 0;

    @Override
    public void setPrincipal(ControleurPrincipal principal) {
        this.principal = principal;
    }

    public void initialize() {

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(zoneJeu.widthProperty());
        clip.heightProperty().bind(zoneJeu.heightProperty());
        zoneJeu.setClip(clip);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {

            for (Balle b : balles) {

                b.getForme().setLayoutX(b.getForme().getLayoutX() + b.getDx());
                b.getForme().setLayoutY(b.getForme().getLayoutY() + b.getDy());

                double largeur = zoneJeu.getWidth() > 0 ? zoneJeu.getWidth() : zoneJeu.getPrefWidth();
                double hauteur = zoneJeu.getHeight() > 0 ? zoneJeu.getHeight() : zoneJeu.getPrefHeight();

                if (b.getForme().getLayoutX() <= 0) {
                    b.getForme().setLayoutX(0);
                    b.setDx(Math.abs(b.getDx()));
                }
                if (b.getForme().getLayoutX() >= largeur - b.getForme().getFitWidth()) {
                    b.getForme().setLayoutX(largeur - b.getForme().getFitWidth());
                    b.setDx(-Math.abs(b.getDx()));
                }
                if (b.getForme().getLayoutY() <= 0) {
                    b.getForme().setLayoutY(0);
                    b.setDy(Math.abs(b.getDy()));
                }
                if (b.getForme().getLayoutY() >= hauteur - b.getForme().getFitHeight()) {
                    b.getForme().setLayoutY(hauteur - b.getForme().getFitHeight());
                    b.setDy(-Math.abs(b.getDy()));
                }
            }

            gererCollisions();

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void ajouterDesBalles(ActionEvent event) {
        for (int i = 0; i < 3; i++) {
            TypeBalle type = TypeBalle.values()[indexOrdre];
            indexOrdre = (indexOrdre + 1) % 3;
            creerBalleAleatoirePosition(type);
        }
    }

    @FXML
    private void ajouterUneBalle(ActionEvent event) {
        TypeBalle type = TypeBalle.values()[random.nextInt(3)];
        creerBalleAleatoirePosition(type);
    }

    @FXML
    private void retirerUneBalle(ActionEvent event) {
        if (balles.isEmpty()) return;

        int index = random.nextInt(balles.size());
        Balle b = balles.remove(index);
        zoneJeu.getChildren().remove(b.getForme());
    }

    private void creerBalleAleatoirePosition(TypeBalle type) {

        double largeur = zoneJeu.getWidth() > 0 ? zoneJeu.getWidth() : zoneJeu.getPrefWidth();
        double hauteur = zoneJeu.getHeight() > 0 ? zoneJeu.getHeight() : zoneJeu.getPrefHeight();

        double x = random.nextDouble() * (largeur - 40);
        double y = random.nextDouble() * (hauteur - 40);

        double dx = random.nextBoolean() ? 3 : -3;
        double dy = random.nextBoolean() ? 3 : -3;

        Balle b = new Balle(x, y, type, dx, dy);

        zoneJeu.getChildren().add(b.getForme());
        balles.add(b);
    }

    private void gererCollisions() {

        for (int i = 0; i < balles.size(); i++) {
            for (int j = i + 1; j < balles.size(); j++) {

                Balle b1 = balles.get(i);
                Balle b2 = balles.get(j);

                double dx = b1.getForme().getLayoutX() - b2.getForme().getLayoutX();
                double dy = b1.getForme().getLayoutY() - b2.getForme().getLayoutY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                double rayon1 = b1.getForme().getFitWidth() / 2;
                double rayon2 = b2.getForme().getFitWidth() / 2;

                if (distance < rayon1 + rayon2) {

                    TypeBalle gagnant = determinerGagnant(b1.getType(), b2.getType());

                    if (gagnant == b1.getType()) {
                        b2.transformerEn(b1.getType());
                    } else if (gagnant == b2.getType()) {
                        b1.transformerEn(b2.getType());
                    }
                }
            }
        }
    }

    private TypeBalle determinerGagnant(TypeBalle t1, TypeBalle t2) {

        if (t1 == t2) return t1;

        return switch (t1) {
            case PIERRE -> (t2 == TypeBalle.CISEAU) ? t1 : t2;
            case CISEAU -> (t2 == TypeBalle.PAPIER) ? t1 : t2;
            case PAPIER -> (t2 == TypeBalle.PIERRE) ? t1 : t2;
        };
    }

    @FXML
    public void retourMenu(ActionEvent event) {
        principal.afficherPage("Reglements.fxml");
    }
}