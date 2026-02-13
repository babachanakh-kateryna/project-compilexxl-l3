package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;
/**
 * Génère le code assembleur pour une instruction d'écriture
 */
public class GenerateurEcrire {
    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurEcrire(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code pour une instruction d'écriture donnée
     * @param ecr le noeud d'écriture à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour l'instruction d'écriture
     */
    public String generer(Ecrire ecr, Tds tds) {
        if (ecr == null) return "";

        StringBuilder code = new StringBuilder();

        Noeud expr = null;
        if (ecr.getFils() != null && !ecr.getFils().isEmpty()) {
            expr = ecr.getFils().get(0);
        }

        if (expr == null) {
            throw new IllegalArgumentException("ECR doit avoir une expression en fils");
        }

        // generer le code de l'expression à afficher
        GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);
        code.append(genExpr.generer(expr, tds));

        //afficher le résultat de l'expression
        code.append("\tPOP(R0)\n");
        code.append("\tWRINT()\n");//affiche l'entier dans R0

        return code.toString();
    }
}
