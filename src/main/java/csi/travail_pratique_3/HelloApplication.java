package csi.travail_pratique_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}



/**
 * Patron de conception utilise : Singleton
 * Categorie : Creationnel
 *
 * Ou : GestionnaireDeScene.java
 *
 * Pourquoi : Le GestionnaireDeScene doit exister en une seule instance
 * dans toute l'application car il controle la fenetre principale unique.
 * Le Singleton garantit qu'on accede toujours au meme gestionnaire
 * peu importe d'ou on l'appelle dans le programme.
 *
 * Gains :
 * - Evite les conflits entre plusieurs gestionnaires
 * - Acces global et centralise a la fenetre principale
 * - Controle total sur le cycle de vie de la fenetre
 */