

/**
 *
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe ControleurPrincipal
 *
 * Ce controleur est le point central de l'application. Il gere la structure
 * principale de l'interface en deux zones : le menu a gauche qui reste toujours
 * visible, et la zone de contenu a droite qui change selon la page selectionnee.
 * Il est responsable du chargement dynamique des pages FXML, de l'injection
 * de sa reference dans les controleurs des pages, de l'arret des medias
 * lors des changements de page, et de l'application du theme CSS choisi
 * par l'utilisateur sur toute la scene.
 *
 */




package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;





public class ControleurPrincipal {

    /** Zone de contenu a droite qui affiche les differentes pages de l'application. */
    @FXML
    private StackPane zoneContenu;

    /** Zone de menu a gauche qui contient les boutons de navigation. */
    @FXML
    private VBox zoneMenu;

    /** Reference au controleur du menu principal. */
    private ControleurMenu menuController;

    /** Chemin de la feuille de style CSS actuellement active. */
    private String cssActif = "/csi/travail_pratique_3/style.css";

    /** Reference au controleur de la page actuellement affichee. */
    private Object controleurActuel;

    /**
     * Initialise le controleur principal au chargement de l'application.
     * Charge le menu dans la zone de gauche et injecte la reference
     * du controleur principal dans le controleur du menu.
     */
    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/csi/travail_pratique_3/Menu.fxml")
            );

            Parent menu = loader.load();
            menuController = loader.getController();
            menuController.setPrincipal(this);
            zoneMenu.getChildren().add(menu);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge et affiche une page FXML dans la zone de contenu a droite.
     * Arrete les medias si la page precedente etait le jeu de memoire,
     * injecte la reference du controleur principal dans la nouvelle page,
     * et applique le theme actif a toute la scene.
     *
     * @param fxml le nom du fichier FXML a charger
     */
    public void afficherPage(String fxml) {
        try {
            // Arreter le media si la page actuelle est le jeu de memoire
            if (controleurActuel instanceof ControleurMemoire c) {
                c.arreter();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/csi/travail_pratique_3/" + fxml)
            );
            Node page = loader.load();

            Object ctrl = loader.getController();
            if (ctrl instanceof ControleurAvecPrincipal c) {
                c.setPrincipal(this);
            }

            // Garder une reference au controleur de la page actuelle
            controleurActuel = ctrl;

            zoneContenu.getChildren().setAll(page);

            // Appliquer le theme actif a toute la scene
            appliquerTheme();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change le theme CSS de toute l'application.
     * Sauvegarde le chemin du nouveau CSS et l'applique immediatement.
     *
     * @param cheminCss le chemin de la nouvelle feuille de style CSS
     */
    public void changerTheme(String cheminCss) {
        this.cssActif = cheminCss;
        appliquerTheme();
    }

    /**
     * Applique la feuille de style active a toute la scene.
     * Remplace l'ancienne feuille de style par la nouvelle.
     */
    private void appliquerTheme() {
        Scene scene = zoneContenu.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(
                    getClass().getResource(cssActif).toExternalForm()
            );
        }
    }

    /**
     * Retourne le chemin de la feuille de style CSS actuellement active.
     *
     * @return le chemin du CSS actif
     */
    public String getCssActif() {
        return cssActif;
    }
}