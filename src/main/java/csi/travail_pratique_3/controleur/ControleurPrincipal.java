package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ControleurPrincipal {

    @FXML
    private StackPane zoneContenu;

    @FXML
    private VBox zoneMenu;

    private ControleurMenu menuController;

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
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/csi/travail_pratique_3/" + fxml)
            );
            Node page = loader.load();

            Object ctrl = loader.getController();
            if (ctrl instanceof ControleurAvecPrincipal c) {
                c.setPrincipal(this);
            }

            zoneContenu.getChildren().setAll(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}