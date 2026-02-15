package fr.l3.miashs.generation;

import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

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

    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurRetour(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code pour un retour de fonction
     * @param ret le noeud de retour à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour le retour de fonction
     */
    public String generer(Retour ret, Tds tds) {
        if (ret == null) return "";
        StringBuilder code = new StringBuilder();

        Noeud expression = ret.getLeFils();

        if (expression != null) {
            // create un générateur d'expression pour la fonction courante
            GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);
            code.append(genExpr.generer(expression, tds));

            //offset <- 2 + nb_param
            int nbParam = tds.getNbParamFonction(scopeFonction);
            int offset = -4 * (2 + nbParam + 1);

            code.append("\tPOP(R0)\n");
            code.append("\tPUTFRAME(R0, ").append(offset).append(")\n");
        }
        code.append("\tBR(ret_").append(scopeFonction).append(")\n");

        return code.toString();
    }

}
