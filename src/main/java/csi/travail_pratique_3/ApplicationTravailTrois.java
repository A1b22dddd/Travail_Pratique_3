package csi.travail_pratique_3;

import csi.travail_pratique_3.utilitaire.GestionnaireDeScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationTravailTrois extends Application {

    @Override
    public void start(Stage fenetre) throws Exception {

        GestionnaireDeScene.initialiser(fenetre);

        FXMLLoader chargeur = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Scene scene = new Scene(chargeur.load(), 1300, 700);

        fenetre.setTitle("Menu");
        fenetre.setScene(scene);
        fenetre.show();
    }

    public static void main(String[] args) {
        launch();
    }
}