package fr.l3.miashs.generation;

/**
 * Classe de génération d'affectations
 */

/*
pseudo code vu en cours de compilation :

generer_affectation:
    -> a : arbre
    <- code : string
debut
    StringBuffer code <- new StringBuffer()
    code +<- generer_expression(a.fils[0])
    code.append(C1)
    code.append("POP(R0)\n")
    code.append("ST(R0, " + a.valeur.offset + ")\n")
    return code.toString()

fin
*/
public class GenerateurAffectation {
}
