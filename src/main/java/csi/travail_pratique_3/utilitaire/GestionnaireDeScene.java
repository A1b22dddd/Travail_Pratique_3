
/**
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Classe GestionnaireDeScene
 *
 * Cette classe utilitaire gere la fenetre principale de l'application.
 * Elle est responsable de l'initialisation de la fenetre et du changement
 * de scene lors de la navigation entre les pages. Elle applique le patron
 * de conception Singleton pour garantir qu'une seule instance existe
 * dans toute l'application.
 *
 * Patron de conception : Singleton (Creationnel)
 * Emplacement : GestionnaireDeScene.java
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


package csi.travail_pratique_3.utilitaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class GestionnaireDeScene {

    /** Instance unique du gestionnaire — creee une seule fois. */
    private static GestionnaireDeScene instance;

    /** Fenetre principale de l'application. */
    private static Stage fenetrePrincipale;

    /** Largeur fixe de la fenetre principale en pixels. */
    private static final int LARGEUR = 1300;

    /** Hauteur fixe de la fenetre principale en pixels. */
    private static final int HAUTEUR = 700;

    /**
     * Constructeur prive — empeche la creation d'instances externes.
     * Fait partie du patron Singleton.
     */
    private GestionnaireDeScene() {}

    /**
     * Retourne l'instance unique du GestionnaireDeScene.
     * Si elle n'existe pas encore, elle est creee ici.
     * Fait partie du patron Singleton.
     *
     * @return l'instance unique du GestionnaireDeScene
     */
    public static GestionnaireDeScene getInstance() {
        if (instance == null) {
            instance = new GestionnaireDeScene();
        }
        return instance;
    }

    /**
     * Initialise la fenetre principale de l'application.
     * Desactive le redimensionnement de la fenetre.
     *
     * @param fenetre la fenetre principale de l'application
     */
    public static void initialiser(Stage fenetre) {
        fenetrePrincipale = fenetre;
        fenetrePrincipale.setResizable(false);
    }

    /**
     * Charge et affiche une nouvelle scene dans la fenetre principale.
     *
     * @param nomFichierFXML le nom du fichier FXML a charger
     * @param titre          le titre de la fenetre a afficher
     * @throws Exception si le fichier FXML est introuvable ou invalide
     */
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