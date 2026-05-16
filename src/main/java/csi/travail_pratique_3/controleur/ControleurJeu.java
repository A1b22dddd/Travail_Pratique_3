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

    // Rayon fixe des balles
    private static final double RAYON = 25;

    // Timeline principale qui anime les balles à chaque 16ms (environ 60 fps)
    private Timeline timeline;

    @Override
    public void setPrincipal(ControleurPrincipal principal) {
        this.principal = principal;
    }

    public void initialize() {

        // Clip pour empêcher les balles de déborder hors de la zone de jeu
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(zoneJeu.widthProperty());
        clip.heightProperty().bind(zoneJeu.heightProperty());
        zoneJeu.setClip(clip);

        // --- PAUSE AVEC CLIC DE SOURIS ---
        // Lorsque le bouton de la souris est pressé et maintenu,
        // on met la timeline en pause : les balles arrêtent de bouger
        zoneJeu.setOnMousePressed(event -> timeline.pause());

        // Lorsque le bouton de la souris est relâché,
        // on reprend la timeline : les balles recommencent à bouger
        zoneJeu.setOnMouseReleased(event -> timeline.play());
        // --- FIN PAUSE ---

        // Création de la timeline qui gère le mouvement des balles
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {

            for (Balle b : balles) {

                // Déplacer la balle selon sa vitesse (dx, dy)
                b.getForme().setCenterX(b.getForme().getCenterX() + b.getDx());
                b.getForme().setCenterY(b.getForme().getCenterY() + b.getDy());

                // Obtenir les dimensions actuelles de la zone de jeu
                double largeur = zoneJeu.getWidth() > 0 ? zoneJeu.getWidth() : zoneJeu.getPrefWidth();
                double hauteur = zoneJeu.getHeight() > 0 ? zoneJeu.getHeight() : zoneJeu.getPrefHeight();

                // Rebond sur le mur gauche
                if (b.getForme().getCenterX() - RAYON <= 0) {
                    b.getForme().setCenterX(RAYON);
                    b.setDx(Math.abs(b.getDx()));
                }
                // Rebond sur le mur droit
                if (b.getForme().getCenterX() + RAYON >= largeur) {
                    b.getForme().setCenterX(largeur - RAYON);
                    b.setDx(-Math.abs(b.getDx()));
                }
                // Rebond sur le mur du haut
                if (b.getForme().getCenterY() - RAYON <= 0) {
                    b.getForme().setCenterY(RAYON);
                    b.setDy(Math.abs(b.getDy()));
                }
                // Rebond sur le mur du bas
                if (b.getForme().getCenterY() + RAYON >= hauteur) {
                    b.getForme().setCenterY(hauteur - RAYON);
                    b.setDy(-Math.abs(b.getDy()));
                }
            }

            // Vérifier les collisions entre toutes les balles
            gererCollisions();

        }));

        // La timeline tourne en boucle indéfiniment
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

        // Position aléatoire en tenant compte du rayon
        double x = RAYON + random.nextDouble() * (largeur - 2 * RAYON);
        double y = RAYON + random.nextDouble() * (hauteur - 2 * RAYON);

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

                double dx = b1.getForme().getCenterX() - b2.getForme().getCenterX();
                double dy = b1.getForme().getCenterY() - b2.getForme().getCenterY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                // Collision si la distance est inférieure à la somme des rayons
                if (distance < RAYON * 2) {

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