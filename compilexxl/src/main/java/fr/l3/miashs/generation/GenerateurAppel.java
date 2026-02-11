package fr.l3.miashs.generation;

/**
 * Classe de génération d'appels de fonctions
 */

/*
pseudo code vu en cours de compilation :

generer_appel:
    -> a : arbre
    <- code : string
debut
    code <- Ø
    si a.valeur.type != void
        alors
            code +<- ALLOCATE(1)
    fsi

    pour chaque f ∈ fils(a) faire
        code +<- generer_expression(f)
    fpour

    code +<- CALL(a.valeur.nom)
    code +<- DEALLOCATE(a.valeur.nb_param)
fin

 */

public class GenerateurAppel {

    //TODO: à compléter
}
