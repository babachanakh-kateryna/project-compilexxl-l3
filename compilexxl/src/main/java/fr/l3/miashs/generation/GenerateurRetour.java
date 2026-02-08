package fr.l3.miashs.generation;

/**
 * Classe de génération de code pour les retours de fonctions
 */

/*
pseudo code vu en cours de compilation :

generer_retour:
    -> a : arbre
    <- code : string
debut
    code <- generer_expression(fils(a))
    offset <- 2 + a.valeur.nb_param
    code +<- POP(R0)
             PUTFRAME(R0, offset*4)
             BR(ret_a.valeur.nom)
fin

 */
public class GenerateurRetour {
}
