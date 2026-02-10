package fr.l3.miashs.generation;

/**
 * Classe de génération d'instructions
 */

/*
pseudo code vu en cours de compilation :

generer_instruction:
    -> a : arbre
    <- code : string
debut
    cas ou a.cat = affectation:
        code <- generer_affectation(a)
    cas ou a.cat = appel:
        code <- generer_appel(a)
    cas ou a.cat = fonction:
        code <- generer_fonction(a)
    cas ou a.cat = return:
        code <- generer_return(a)
    cas ou a.cat = if:
        code <- generer_if(a)
    cas ou a.cat = while:
        code <- generer_while(a)
fin

 */
public class GenerateurInstruction {
}
