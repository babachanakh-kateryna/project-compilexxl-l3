package fr.l3.miashs.generation;

/**
 * Classe principale du projet, elle utilise les autres classes pour générer un programme complet
 */

/*
pseudo code vu en cours de compilation :

generer_programme:
    -> a : arbre
    -> tds : table des symboles
    <- code : string
debut
    code <- .include beta.asm
             CMOVE(pile,SP)
             BR(debut)
    code +<- generer_data(tds)
    code +<- debut:
                CALL(main)
                HALT()
    pour chaque f ∈ fils(a) faire
        code +<- generer_fonction(f)
    fpour
    code +<- pile:
fin

 */
public class GenerateurProgramme {
    //TODO: à compléter
}
