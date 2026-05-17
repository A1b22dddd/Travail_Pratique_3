/**
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe ApplicationTravailTrois
 *
 * Cette classe est le point d'entree principal de l'application JavaFX.
 * Elle est responsable du demarrage de l'application, de l'initialisation
 * du gestionnaire de scene, du chargement de la mise en page principale
 * (MainLayout.fxml) et de l'affichage de la fenetre principale.
 * La fenetre est fixe et ne peut pas etre redimensionnee par l'utilisateur.
 */



package csi.travail_pratique_3;

import csi.travail_pratique_3.utilitaire.GestionnaireDeScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class ApplicationTravailTrois extends Application {

    /**
     * Point d'entree JavaFX — demarre l'application et affiche la fenetre principale.
     * Initialise le gestionnaire de scene, charge le layout principal et
     * configure la fenetre avec un titre et des dimensions fixes.
     *
     * @param fenetre la fenetre principale fournie par JavaFX
     * @throws Exception si le fichier FXML est introuvable ou invalide
     */
    @Override
    public void start(Stage fenetre) throws Exception {

        // Initialiser le gestionnaire de scene avec la fenetre principale
        GestionnaireDeScene.initialiser(fenetre);

        // Charger la mise en page principale qui contient le menu et la zone de contenu
        FXMLLoader chargeur = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Scene scene = new Scene(chargeur.load(), 1300, 700);

        fenetre.setTitle("Menu");
        fenetre.setScene(scene);
        fenetre.show();
    }

    /**
     * Methode principale — lance l'application JavaFX.
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch();
    }
}