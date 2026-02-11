package fr.l3.miashs.generation;

/**
 * Classe de génération de fonctions
 */

/*
pseudo code vu en cours de compilation :

generer_fonction:
    -> a : arbre
    <- code : string
debut
    code <- a.valeur.nom
    code +<- PUSH(LP)
             PUSH(BP)
             MOVE(SP, BP)
             ALLOCATE(a.valeur.nb_param)
    pour chaque f ∈ fils(a) faire
        code +<- generer_instruction(f)
    fpour
    code +<- RET_a.valeur.nom
    code +<- DEALLOCATE(a.valeur.nb_param)
             POP(BP)
             POP(LP)
             RTN()
fin

 */
public class GenerateurFonction {
    //TODO: à compléter
}
