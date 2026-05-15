package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;

public class ControleurMenu {

    private ControleurPrincipal principal;

    public void setPrincipal(ControleurPrincipal p) {
        this.principal = p;
    }

    @FXML
    private void ouvrirJeu() {
        principal.afficherPage("Jeu.fxml");
    }

    @FXML
    private void ouvrirMonJeu() {
        principal.afficherPage("MonJeu.fxml");
    }

    @FXML
    private void ouvrirReglements() {
        principal.afficherPage("Reglements.fxml");
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }
}
