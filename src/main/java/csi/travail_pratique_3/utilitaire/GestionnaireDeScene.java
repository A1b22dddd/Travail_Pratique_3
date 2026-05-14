package csi.travail_pratique_3.utilitaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionnaireDeScene {

    private static Stage fenetrePrincipale;
    private static final int LARGEUR = 1300;
    private static final int HAUTEUR = 700;

    public static void initialiser(Stage fenetre) {
        fenetrePrincipale = fenetre;
        fenetrePrincipale.setResizable(false);
    }

    public static void changerScene(String nomFichierFXML, String titre) throws Exception {
        FXMLLoader chargeur = new FXMLLoader(GestionnaireDeScene.class.getResource("/csi/travail_pratique_3/" + nomFichierFXML + ".fxml"));
        Scene scene = new Scene(chargeur.load(), LARGEUR, HAUTEUR);
        fenetrePrincipale.setTitle(titre);
        fenetrePrincipale.setScene(scene);
        fenetrePrincipale.show();
    }
}
