package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.CategorieSymbole;

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
    c1 <- generer_expression(a.fils[0])
    code.append(c1)
    code.append("POP(R0)\n")
    code.append("ST(R0, " + a.valeur.offset + ")\n")
    return code.toString()

fin
*/
public class GenerateurAffectation {

    private final GenerateurExpression generateurExpression;

    public GenerateurAffectation(GenerateurExpression generateurExpression) {
        this.generateurExpression = generateurExpression;
    }

    public String generer(Noeud a) {
        // Vérification que le noeud est une affectation
        if (!(a instanceof Affectation aff)) {
            throw new IllegalArgumentException("Le noeud doit être une affectation");
        }

        Noeud gauche = aff.getFils().get(0);
        Noeud droit = aff.getFils().get(1);

        // Vérification que le fils gauche est un identifiant
        if (!(gauche instanceof Idf idf)) {
            throw new IllegalArgumentException("Le fils gauche doit être un identifiant");
        }

        String nomVar = String.valueOf(idf.getValeur());

        StringBuilder code = new StringBuilder();
        //code.append(generateurExpression.generer(gauche));
        code.append("POP(R0)\n");

        //todo



        return null;

    }
}
