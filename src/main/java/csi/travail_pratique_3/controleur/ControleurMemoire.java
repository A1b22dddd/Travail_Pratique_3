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

    @FXML
    private GridPane grilleCartes;

    @FXML
    private Label etiquetteScore;

    // Référence au contrôleur principal pour la navigation
    private ControleurPrincipal principal;

    // Liste de toutes les cartes du jeu
    private List<Carte> listeCartes;

    // Première carte retournée par le joueur
    private Carte premiereCarteRetournee;

    // Bouton associé à la première carte retournée
    private Button premierBouton;

    // Nombre de paires trouvées
    private int pairestrouvees;

    // Nombre de tentatives du joueur
    private int nombreTentatives;

    // Indique si le joueur peut cliquer (bloqué pendant la vérification)
    private boolean clicAutorise;

    // Lecteur audio pour la chanson
    private MediaPlayer lecteurAudio;

    // Lecteur vidéo pour la vidéo
    private MediaPlayer lecteurVideo;

    @Override
    public void setPrincipal(ControleurPrincipal principal) {
        this.principal = principal;
    }

    @FXML
    public void initialize() {
        pairestrouvees = 0;
        nombreTentatives = 0;
        clicAutorise = true;
        premiereCarteRetournee = null;
        premierBouton = null;

        initialiserCartes();
        afficherCartes();

        // Lancer le son et la vidéo automatiquement
        lancerChanson();
        lancerVideo();
    }

    /**
     * Charge et joue la chanson son.mp3 en arrière-plan.
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
     * Charge et joue la vidéo vague.mp4 dans une nouvelle fenêtre.
     */
    private void lancerVideo() {
        try {
            var urlVideo = getClass().getResource("/csi/travail_pratique_3/vague.mp4");
            if (urlVideo != null) {
                Media video = new Media(urlVideo.toExternalForm());
                lecteurVideo = new MediaPlayer(video);

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
     * Arrête la chanson et la vidéo quand on quitte la page.
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
     * Crée les 16 cartes (8 paires) et les mélange aléatoirement.
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
     * Affiche les cartes dans la grille 4x4.
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
     * Crée un bouton représentant une carte face cachée.
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
     * Gère le clic sur une carte.
     */
    private void gererClicCarte(Carte carte, Button bouton) {

        if (!clicAutorise || carte.estTrouvee() || carte.estRetournee()) return;

        carte.setRetournee(true);
        afficherFaceCarte(bouton, carte);

        if (premiereCarteRetournee == null) {
            premiereCarteRetournee = carte;
            premierBouton = bouton;

        } else {
            nombreTentatives++;
            clicAutorise = false;

            if (premiereCarteRetournee.getIdPaire() == carte.getIdPaire()) {
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
     * Remet a zero la selection courante.
     */
    private void reinitialiserSelection() {
        premiereCarteRetournee = null;
        premierBouton = null;
    }

    /**
     * Met a jour l'etiquette du score.
     */
    private void mettreAJourScore() {
        etiquetteScore.setText("Paires trouvees : " + pairestrouvees
                + " | Tentatives : " + nombreTentatives);
    }

    /**
     * Recommence une nouvelle partie.
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