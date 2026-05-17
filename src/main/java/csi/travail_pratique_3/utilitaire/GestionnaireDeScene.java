package csi.travail_pratique_3.utilitaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Patron de conception : Singleton (Créationnel)
 *
 * Le GestionnaireDeScene est un Singleton car il ne doit exister
 * qu'une seule instance dans toute l'application. Une seule fenêtre
 * principale est gérée à la fois. Le Singleton garantit qu'on accède
 * toujours au même gestionnaire, peu importe d'où on l'appelle.
 *
 * Gain : évite d'avoir plusieurs gestionnaires qui contrôlent des
 * fenêtres différentes, ce qui causerait des conflits.
 */
public class GestionnaireDeScene {

    // Instance unique du gestionnaire
    private static GestionnaireDeScene instance;

    private static Stage fenetrePrincipale;
    private static final int LARGEUR = 1300;
    private static final int HAUTEUR = 700;

    // Constructeur privé — empêche la création d'instances externes
    private GestionnaireDeScene() {}

    /**
     * Retourne l'instance unique du GestionnaireDeScene.
     * Si elle n'existe pas encore, elle est créée ici.
     */
    public static GestionnaireDeScene getInstance() {
        if (instance == null) {
            instance = new GestionnaireDeScene();
        }
        return instance;
    }

    public static void initialiser(Stage fenetre) {
        fenetrePrincipale = fenetre;
        fenetrePrincipale.setResizable(false);
    }

    public static void changerScene(String nomFichierFXML, String titre) throws Exception {
        FXMLLoader chargeur = new FXMLLoader(
                GestionnaireDeScene.class.getResource("/csi/travail_pratique_3/" + nomFichierFXML)
        );

        Scene scene = new Scene(chargeur.load(), LARGEUR, HAUTEUR);
        fenetrePrincipale.setTitle(titre);
        fenetrePrincipale.setScene(scene);
        fenetrePrincipale.show();
    }
}