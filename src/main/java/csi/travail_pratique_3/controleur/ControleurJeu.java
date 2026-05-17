

/**
 *
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe ControleurJeu
 *
 * Ce controleur gere le jeu de balles rebondissantes de type Pierre-Papier-Ciseaux.
 * Il est responsable de l'animation des balles, de la detection des collisions,
 * de la gestion des rebonds sur les murs, et des interactions avec les boutons
 * d'ajout et de retrait de balles. Il implementer l'interface ControleurAvecPrincipal
 * pour recevoir la reference du controleur principal et naviguer entre les pages.
 *
 * @author Ton nom
 * @version 1.0
 */


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

    /** Zone d'affichage des balles dans l'interface. */
    @FXML
    private Pane zoneJeu;

    /** Reference au controleur principal pour la navigation entre les pages. */
    private ControleurPrincipal principal;

    /** Liste de toutes les balles actives dans la zone de jeu. */
    private final List<Balle> balles = new ArrayList<>();

    /** Generateur de nombres aleatoires pour les positions et vitesses des balles. */
    private final Random random = new Random();

    /** Index pour alterner l'ordre des types de balles lors de l'ajout de plusieurs balles. */
    private int indexOrdre = 0;

    /** Rayon fixe de toutes les balles en pixels. */
    private static final double RAYON = 25;

    /** Timeline principale qui anime les balles a chaque 16ms (environ 60 fps). */
    private Timeline timeline;

    /**
     * Injecte la reference du controleur principal.
     *
     * @param principal le controleur principal de l'application
     */
    @Override
    public void setPrincipal(ControleurPrincipal principal) {
        this.principal = principal;
    }

    /**
     * Initialise le jeu au chargement de la page.
     * Configure le clip pour empecher les balles de deborder,
     * met en place la pause avec le clic de souris,
     * et demarre l'animation des balles.
     */
    public void initialize() {

        // Clip pour empecher les balles de deborder hors de la zone de jeu
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(zoneJeu.widthProperty());
        clip.heightProperty().bind(zoneJeu.heightProperty());
        zoneJeu.setClip(clip);

        // --- PAUSE AVEC CLIC DE SOURIS ---
        // Lorsque le bouton de la souris est presse et maintenu,
        // on met la timeline en pause : les balles arretent de bouger
        zoneJeu.setOnMousePressed(event -> timeline.pause());

        // Lorsque le bouton de la souris est relache,
        // on reprend la timeline : les balles recommencent a bouger
        zoneJeu.setOnMouseReleased(event -> timeline.play());
        // --- FIN PAUSE ---

        // Creation de la timeline qui gere le mouvement des balles
        // Un KeyFrame s'execute toutes les 16 millisecondes
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {

            for (Balle b : balles) {

                // Deplacer la balle selon sa vitesse (dx, dy)
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

            // Verifier les collisions entre toutes les balles
            gererCollisions();

        }));

        // La timeline tourne en boucle indefiniment
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Ajoute 3 balles en alternant les types Pierre, Papier, Ciseaux.
     *
     * @param event l'evenement de clic sur le bouton
     */
    @FXML
    private void ajouterDesBalles(ActionEvent event) {
        for (int i = 0; i < 3; i++) {
            TypeBalle type = TypeBalle.values()[indexOrdre];
            indexOrdre = (indexOrdre + 1) % 3;
            creerBalleAleatoirePosition(type);
        }
    }

    /**
     * Ajoute une seule balle de type aleatoire dans la zone de jeu.
     *
     * @param event l'evenement de clic sur le bouton
     */
    @FXML
    private void ajouterUneBalle(ActionEvent event) {
        TypeBalle type = TypeBalle.values()[random.nextInt(3)];
        creerBalleAleatoirePosition(type);
    }

    /**
     * Retire une balle aleatoire de la zone de jeu.
     *
     * @param event l'evenement de clic sur le bouton
     */
    @FXML
    private void retirerUneBalle(ActionEvent event) {
        if (balles.isEmpty()) return;

        int index = random.nextInt(balles.size());
        Balle b = balles.remove(index);
        zoneJeu.getChildren().remove(b.getForme());
    }

    /**
     * Cree une balle a une position aleatoire dans la zone de jeu.
     *
     * @param type le type de la balle a creer (PIERRE, PAPIER ou CISEAU)
     */
    private void creerBalleAleatoirePosition(TypeBalle type) {

        double largeur = zoneJeu.getWidth() > 0 ? zoneJeu.getWidth() : zoneJeu.getPrefWidth();
        double hauteur = zoneJeu.getHeight() > 0 ? zoneJeu.getHeight() : zoneJeu.getPrefHeight();

        // Position aleatoire en tenant compte du rayon
        double x = RAYON + random.nextDouble() * (largeur - 2 * RAYON);
        double y = RAYON + random.nextDouble() * (hauteur - 2 * RAYON);

        double dx = random.nextBoolean() ? 3 : -3;
        double dy = random.nextBoolean() ? 3 : -3;

        Balle b = new Balle(x, y, type, dx, dy);

        zoneJeu.getChildren().add(b.getForme());
        balles.add(b);
    }

    /**
     * Verifie et gere les collisions entre toutes les balles.
     * Lorsque deux balles se touchent, applique les regles Pierre-Papier-Ciseaux.
     */
    private void gererCollisions() {

        for (int i = 0; i < balles.size(); i++) {
            for (int j = i + 1; j < balles.size(); j++) {

                Balle b1 = balles.get(i);
                Balle b2 = balles.get(j);

                double dx = b1.getForme().getCenterX() - b2.getForme().getCenterX();
                double dy = b1.getForme().getCenterY() - b2.getForme().getCenterY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                // Collision si la distance est inferieure a la somme des rayons
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

    /**
     * Determine le gagnant entre deux types de balles selon les regles Pierre-Papier-Ciseaux.
     *
     * @param t1 le type de la premiere balle
     * @param t2 le type de la deuxieme balle
     * @return le type gagnant
     */
    private TypeBalle determinerGagnant(TypeBalle t1, TypeBalle t2) {

        if (t1 == t2) return t1;

        return switch (t1) {
            case PIERRE -> (t2 == TypeBalle.CISEAU) ? t1 : t2;
            case CISEAU -> (t2 == TypeBalle.PAPIER) ? t1 : t2;
            case PAPIER -> (t2 == TypeBalle.PIERRE) ? t1 : t2;
        };
    }

    /**
     * Retourne a la page des reglements.
     *
     * @param event l'evenement de clic sur le bouton
     */
    @FXML
    public void retourMenu(ActionEvent event) {
        principal.afficherPage("Reglements.fxml");
    }
}