package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ControleurPrincipal {

    @FXML
    private StackPane zoneContenu;

    @FXML
    private VBox zoneMenu;

    private ControleurMenu menuController;

    // Feuille de style active
    private String cssActif = "/csi/travail_pratique_3/style.css";

    // Contrôleur de la page actuellement affichée
    private Object controleurActuel;

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

    public void afficherPage(String fxml) {
        try {
            // Arrêter le média si la page actuelle est ControleurMemoire
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

            // Garder une référence au contrôleur actuel
            controleurActuel = ctrl;

            zoneContenu.getChildren().setAll(page);

            // Appliquer le thème actif à la scène entière
            appliquerTheme();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change le thème CSS de toute l'application.
     */
    public void changerTheme(String cheminCss) {
        this.cssActif = cheminCss;
        appliquerTheme();
    }

    /**
     * Applique la feuille de style active à toute la scène.
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

    public String getCssActif() {
        return cssActif;
    }
}