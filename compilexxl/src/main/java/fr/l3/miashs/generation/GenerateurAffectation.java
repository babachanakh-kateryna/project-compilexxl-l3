package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;

/**
 * Genere le code assembleur pour une affectation
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

    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurAffectation(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code pour une affectation donnée
     * @param n le noeud d'affectation à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour l'affectation
     */
    public String generer(Affectation n, Tds tds) {
        StringBuilder code = new StringBuilder();

        // generer l'expression du cote droit
        GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);
        code.append(genExpr.generer(n.getFilsDroit(), tds));

        // recuper la valeur calculee
        code.append("\tPOP(R0)\n");

        // identif la variable à gauche
        Idf idf = (Idf) n.getFilsGauche();
        String nom = idf.getValeur().toString();

        //verifier que la variable existe dans la TDS
        Item item = tds.rechercher(nom);
        if (item == null) {
            throw new IllegalArgumentException("Variable non trouvée dans la TDS: " + nom);
        }

        switch (item.getCategorie()) {

            case GLOBAL -> code.append("\tST(R0, ").append(nom).append(")\n");

            case LOCAL -> {
                int offset = item.getRang() * 4;
                code.append("\tPUTFRAME(R0, ").append(offset).append(")\n");
            }

            case PARAM -> {
                int offset = (1 + item.getNbParam() + item.getRang()) * (-4);
                code.append("\tPUTFRAME(R0, ").append(offset).append(")\n");
            }

            default -> throw new IllegalArgumentException("Catégorie non gérée pour affectation: " + item.getCategorie());
        }

        return code.toString();

    }
}
