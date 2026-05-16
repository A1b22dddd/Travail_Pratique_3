package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControleurMenu {

    private ControleurPrincipal principal;

    @FXML
    private Button boutonTheme;

    // Indique si le thème sombre est actif
    private boolean themeSombreActif = false;

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

    /**
     * Alterne entre le thème clair et le thème sombre.
     * Délègue le changement au ControleurPrincipal qui gère toute la scène.
     */
    @FXML
    private void changerTheme() {
        if (themeSombreActif) {
            principal.changerTheme("/csi/travail_pratique_3/style.css");
            boutonTheme.setText("Theme sombre");
            themeSombreActif = false;
        } else {
            principal.changerTheme("/csi/travail_pratique_3/style-sombre.css");
            boutonTheme.setText("Theme clair");
            themeSombreActif = true;
        }
    }
}