

/**
 * @author : HOUNSOU Césaire
 * Nom et prénom de l'enseignant : François Picard Légaré
 * Date de la derniere modification : 17-05-2026
 * Description :
 * Enumeration TypeBalle
 *
 * Cette enumeration definit les trois types de balles possibles dans le jeu
 * de balles rebondissantes. Chaque type correspond a un element du jeu
 * Pierre-Papier-Ciseaux et est associe a une image differente.
 * Les regles de transformation lors des collisions sont les suivantes :
 * Pierre bat Ciseaux, Ciseaux bat Papier, Papier bat Pierre.
 *
 */



package csi.travail_pratique_3.modele;


public enum TypeBalle {

    /** Represente une balle de type Pierre — bat les Ciseaux. */
    PIERRE,

    /** Represente une balle de type Papier — bat la Pierre. */
    PAPIER,

    /** Represente une balle de type Ciseaux — bat le Papier. */
    CISEAU
}