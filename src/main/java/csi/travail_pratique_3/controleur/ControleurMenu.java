
/**
 *
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe ControleurMenu
 *
 * Ce controleur gere le menu principal de l'application situe sur la partie gauche
 * de l'ecran. Il est responsable de la navigation entre les differentes pages
 * (jeu de balles rebondissantes, jeu de memoire, reglements) et du changement
 * de theme visuel entre le theme clair et le theme sombre. Le menu reste
 * toujours visible pendant toute la duree de l'application.
 */



package csi.travail_pratique_3.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class ControleurMenu {

    /** Reference au controleur principal pour la navigation et le changement de theme. */
    private ControleurPrincipal principal;

    /** Bouton permettant d'alterner entre le theme clair et le theme sombre. */
    @FXML
    private Button boutonTheme;

    /** Indique si le theme sombre est actuellement actif. */
    private boolean themeSombreActif = false;

    /**
     * Injecte la reference du controleur principal.
     *
     * @param p le controleur principal de l'application
     */
    public void setPrincipal(ControleurPrincipal p) {
        this.principal = p;
    }

    /**
     * Ouvre le jeu de balles rebondissantes dans la zone de contenu.
     */
    @FXML
    private void ouvrirJeu() {
        principal.afficherPage("Jeu.fxml");
    }

    /**
     * Ouvre le jeu de memoire dans la zone de contenu.
     */
    @FXML
    private void ouvrirMonJeu() {
        principal.afficherPage("MonJeu.fxml");
    }

    /**
     * Ouvre la page des reglements dans la zone de contenu.
     */
    @FXML
    private void ouvrirReglements() {
        principal.afficherPage("Reglements.fxml");
    }

    /**
     * Ferme l'application completement.
     */
    @FXML
    private void quitter() {
        System.exit(0);
    }

    /**
     * Alterne entre le theme clair et le theme sombre.
     * Delegue le changement au ControleurPrincipal qui applique
     * la nouvelle feuille de style CSS a toute la scene.
     */
    @FXML
    private void changerTheme() {
        if (themeSombreActif) {
            // Revenir au theme clair
            principal.changerTheme("/csi/travail_pratique_3/style.css");
            boutonTheme.setText("Theme sombre");
            themeSombreActif = false;
        } else {
            // Appliquer le theme sombre
            principal.changerTheme("/csi/travail_pratique_3/style-sombre.css");
            boutonTheme.setText("Theme clair");
            themeSombreActif = true;
        }
    }
}