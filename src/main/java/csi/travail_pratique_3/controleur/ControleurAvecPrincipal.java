

/**
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Interface ControleurAvecPrincipal
 *
 * Cette interface est utilisée dans le projet pour permettre au contrôleur principal
 * d'injecter sa référence dans les contrôleurs des pages chargées dynamiquement.
 * Tout contrôleur de page qui a besoin de communiquer avec le ControleurPrincipal
 * doit implémenter cette interface et définir la méthode setPrincipal.
 */



package csi.travail_pratique_3.controleur;
public interface ControleurAvecPrincipal {

    /**
     * Injecte la référence du contrôleur principal dans le contrôleur de la page.
     *
     * @param principal le contrôleur principal de l'application
     */
    void setPrincipal(ControleurPrincipal principal);
}