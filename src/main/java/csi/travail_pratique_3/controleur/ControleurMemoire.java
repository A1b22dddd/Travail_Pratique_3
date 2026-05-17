
/**
 *
 *@author : HOUNSOU Césaire
 *Nom et prénom de l'enseignant : François Picard Légaré
 *Date de la derniere modification : 17-05-2026
 *Description :
 * Classe ControleurMemoire
 * Ce controleur gere le jeu de memoire avec des paires de cartes d'animaux.
 * Il est responsable de l'affichage des cartes dans une grille 4x4,
 * de la logique de retournement des cartes, de la detection des paires,
 * du suivi du score et des tentatives, ainsi que du lancement automatique
 * de la chanson et de la video au demarrage du jeu.
 * Il implemente l'interface ControleurAvecPrincipal pour recevoir la reference
 * du controleur principal et naviguer entre les pages.
 *
 * @author Ton nom
 * @version 1.0
 */




package csi.travail_pratique_3.controleur;

import csi.travail_pratique_3.modele.Carte;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ControleurMemoire implements ControleurAvecPrincipal {

    /** Grille d'affichage des cartes dans l'interface. */
    @FXML
    private GridPane grilleCartes;

    /** Etiquette affichant le score et le nombre de tentatives. */
    @FXML
    private Label etiquetteScore;

    /** Reference au controleur principal pour la navigation entre les pages. */
    private ControleurPrincipal principal;

    /** Liste de toutes les cartes du jeu. */
    private List<Carte> listeCartes;

    /** Premiere carte retournee par le joueur en attente de la deuxieme. */
    private Carte premiereCarteRetournee;

    /** Bouton associe a la premiere carte retournee. */
    private Button premierBouton;

    /** Nombre de paires trouvees par le joueur. */
    private int pairestrouvees;

    /** Nombre de tentatives effectuees par le joueur. */
    private int nombreTentatives;

    /** Indique si le joueur peut cliquer, bloque pendant la verification des paires. */
    private boolean clicAutorise;

    /** Lecteur audio pour la chanson de fond. */
    private MediaPlayer lecteurAudio;

    /** Lecteur video pour la video affichee dans une fenetre separee. */
    private MediaPlayer lecteurVideo;

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
     * Remet les compteurs a zero, cree et affiche les cartes,
     * puis lance automatiquement la chanson et la video.
     */
    @FXML
    public void initialize() {
        pairestrouvees = 0;
        nombreTentatives = 0;
        clicAutorise = true;
        premiereCarteRetournee = null;
        premierBouton = null;

        initialiserCartes();
        afficherCartes();

        // Lancer le son et la video automatiquement au demarrage du jeu
        lancerChanson();
        lancerVideo();
    }

    /**
     * Charge et joue la chanson son.mp3 en arriere-plan.
     * La chanson joue en boucle pendant toute la duree du jeu.
     */
    private void lancerChanson() {
        try {
            var urlSon = getClass().getResource("/csi/travail_pratique_3/son.mp3");
            if (urlSon != null) {
                Media son = new Media(urlSon.toExternalForm());
                lecteurAudio = new MediaPlayer(son);
                lecteurAudio.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge et joue la video vague.mp4 dans une nouvelle fenetre.
     * Le son de la video est coupe pour ne pas interferer avec la chanson.
     * La video joue en boucle indefiniment.
     */
    private void lancerVideo() {
        try {
            var urlVideo = getClass().getResource("/csi/travail_pratique_3/vague.mp4");
            if (urlVideo != null) {
                Media video = new Media(urlVideo.toExternalForm());
                lecteurVideo = new MediaPlayer(video);
                lecteurVideo.setCycleCount(MediaPlayer.INDEFINITE);
                lecteurVideo.setMute(true);

                MediaView vueVideo = new MediaView(lecteurVideo);
                vueVideo.setFitWidth(640);
                vueVideo.setFitHeight(360);

                StackPane racine = new StackPane(vueVideo);
                Scene sceneVideo = new Scene(racine, 640, 360);

                Stage fenetreVideo = new Stage();
                fenetreVideo.setTitle("Video");
                fenetreVideo.setScene(sceneVideo);
                fenetreVideo.setResizable(false);
                fenetreVideo.show();

                lecteurVideo.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Arrete la chanson et la video quand le joueur quitte la page du jeu.
     */
    public void arreter() {
        if (lecteurAudio != null) {
            lecteurAudio.stop();
        }
        if (lecteurVideo != null) {
            lecteurVideo.stop();
        }
    }

    /**
     * Cree les 16 cartes (8 paires) et les melange aleatoirement.
     */
    private void initialiserCartes() {
        listeCartes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            String nomImage = "carte" + i + ".jpg";
            listeCartes.add(new Carte(i, nomImage));
            listeCartes.add(new Carte(i, nomImage));
        }

        Collections.shuffle(listeCartes);
    }

    /**
     * Affiche les cartes melangees dans la grille 4x4.
     */
    private void afficherCartes() {
        grilleCartes.getChildren().clear();

        int colonne = 0;
        int rangee = 0;

        for (Carte carte : listeCartes) {
            Button bouton = creerBoutonCarte(carte);
            grilleCartes.add(bouton, colonne, rangee);

            colonne++;
            if (colonne == 4) {
                colonne = 0;
                rangee++;
            }
        }
    }

    /**
     * Cree un bouton representant une carte face cachee.
     *
     * @param carte la carte associee au bouton
     * @return le bouton cree avec le dos de la carte
     */
    private Button creerBoutonCarte(Carte carte) {
        Button bouton = new Button();
        bouton.getStyleClass().add("carte-bouton");

        afficherDosCarte(bouton);

        bouton.setOnAction(event -> gererClicCarte(carte, bouton));

        return bouton;
    }

    /**
     * Affiche le dos de la carte sur le bouton.
     *
     * @param bouton le bouton sur lequel afficher le dos
     */
    private void afficherDosCarte(Button bouton) {
        var url = getClass().getResource("/csi/travail_pratique_3/images/dos.jpg");
        if (url != null) {
            ImageView imageView = new ImageView(new Image(url.toExternalForm()));
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
            bouton.setGraphic(imageView);
        }
        bouton.setText("");
    }

    /**
     * Affiche la face de la carte sur le bouton.
     *
     * @param bouton le bouton sur lequel afficher la face
     * @param carte la carte dont on veut afficher l'image
     */
    private void afficherFaceCarte(Button bouton, Carte carte) {
        var url = getClass().getResource("/csi/travail_pratique_3/images/" + carte.getNomImage());
        if (url != null) {
            ImageView imageView = new ImageView(new Image(url.toExternalForm()));
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
            bouton.setGraphic(imageView);
        }
        bouton.setText("");
    }

    /**
     * Gere le clic sur une carte.
     * Si c'est la premiere carte, on la retourne et on attend la deuxieme.
     * Si c'est la deuxieme carte, on verifie si c'est une paire.
     * Si oui, les cartes restent visibles. Sinon, elles se retournent apres 1 seconde.
     *
     * @param carte la carte sur laquelle le joueur a clique
     * @param bouton le bouton associe a la carte
     */
    private void gererClicCarte(Carte carte, Button bouton) {

        if (!clicAutorise || carte.estTrouvee() || carte.estRetournee()) return;

        carte.setRetournee(true);
        afficherFaceCarte(bouton, carte);

        if (premiereCarteRetournee == null) {
            // Premiere carte retournee — on attend la deuxieme
            premiereCarteRetournee = carte;
            premierBouton = bouton;

        } else {
            // Deuxieme carte retournee — on verifie si c'est une paire
            nombreTentatives++;
            clicAutorise = false;

            if (premiereCarteRetournee.getIdPaire() == carte.getIdPaire()) {
                // Paire trouvee
                carte.setTrouvee(true);
                premiereCarteRetournee.setTrouvee(true);
                bouton.getStyleClass().setAll("carte-trouvee");
                premierBouton.getStyleClass().setAll("carte-trouvee");

                pairestrouvees++;
                mettreAJourScore();
                reinitialiserSelection();
                clicAutorise = true;

                if (pairestrouvees == 8) {
                    etiquetteScore.setText("Bravo ! Toutes les paires trouvees en "
                            + nombreTentatives + " tentatives !");
                    if (lecteurAudio != null) lecteurAudio.stop();
                }

            } else {
                // Pas une paire — retourner les cartes apres 1 seconde
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    carte.setRetournee(false);
                    premiereCarteRetournee.setRetournee(false);
                    afficherDosCarte(bouton);
                    afficherDosCarte(premierBouton);
                    reinitialiserSelection();
                    clicAutorise = true;
                });
                pause.play();
            }
        }
    }

    /**
     * Remet a zero la selection courante apres chaque tentative.
     */
    private void reinitialiserSelection() {
        premiereCarteRetournee = null;
        premierBouton = null;
    }

    /**
     * Met a jour l'etiquette affichant le score et le nombre de tentatives.
     */
    private void mettreAJourScore() {
        etiquetteScore.setText("Paires trouvees : " + pairestrouvees
                + " | Tentatives : " + nombreTentatives);
    }

    /**
     * Recommence une nouvelle partie en remettant tout a zero.
     * Remelange les cartes et relance la chanson.
     */
    @FXML
    private void rejouer() {
        pairestrouvees = 0;
        nombreTentatives = 0;
        clicAutorise = true;
        premiereCarteRetournee = null;
        premierBouton = null;
        initialiserCartes();
        afficherCartes();
        etiquetteScore.setText("Paires trouvees : 0 | Tentatives : 0");
        if (lecteurAudio != null) {
            lecteurAudio.stop();
            lecteurAudio.play();
        }
    }
}