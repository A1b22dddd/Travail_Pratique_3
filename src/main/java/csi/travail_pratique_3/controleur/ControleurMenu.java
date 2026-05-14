package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import csi.travail_pratique_3.utilitaire.GestionnaireDeScene;

public class ControleurMenu {

    @FXML
    private Button btnJouer;

    @FXML
    private Button btnInstructions;

    @FXML
    private Button btnQuitter;

    @FXML
    public void initialize() {
    }

    @FXML
    private void actionJouer() throws Exception {
        GestionnaireDeScene.changerScene("jeu.fxml", "Jeu");
    }

    @FXML
    private void actionInstructions() throws Exception {
        GestionnaireDeScene.changerScene("instructions.fxml", "Instructions");
    }

    @FXML
    private void actionQuitter() {
        System.exit(0);
    }
}
