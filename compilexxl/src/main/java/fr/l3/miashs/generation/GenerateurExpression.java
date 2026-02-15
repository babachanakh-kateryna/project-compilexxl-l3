package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;

/**
 * Génère le code assembleur pour une expression
 */

/*
pseudo code vu en cours de compilation :

generer_expression:
    -> a : arbre
    <- code : string
debut
    cas CONST

    cas IDF
        si a.valeur = global :  ld a.valeur
        sinon si a.valeur = param :
            offset = 1 + nombre de paramètres + rang du paramètre
            GETFRAME("offset-4), R0)
            PUSH(R0)
        sinon si a.valeur = local :

    cas +
    ....

    cas LECTURE

    cas APPEL
fin

   SP
[ var local m ]
    |
    *
    |
[ local 1 ]
[ local 0 ]
--
[ saved BP ] ← BP
[ saved LP ]
--
[ param n ]
    |
    *
    |
[ param 1 ]
[ param 0 ]
[ result ]

*/
    public class GenerateurExpression {

    //foncction courante
    private final String scopeFonction;

    /**
     * Constructeur de la classe
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurExpression(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code assembleur pour une expression
     * @param expr l'expression à générer
     * @param tds la table des symboles
     * @return le code assembleur généré
     */
    public String generer(Noeud expr, Tds tds) {
        if (expr == null) return "";
        StringBuilder code = new StringBuilder();

        switch (expr.getCat()) {

            case CONST -> genererConst((Const) expr, code);

            case IDF -> genererIdf((Idf) expr, tds, code);

            case LIRE -> genererLire(code);

            case APPEL -> code.append(new GenerateurAppel(scopeFonction).generer((Appel) expr, tds, true));

            case PLUS -> genererBinaire(((Plus) expr).getFilsGauche(), ((Plus) expr).getFilsDroit(), "ADD", tds, code);

            case MOINS -> genererBinaire(((Moins) expr).getFilsGauche(), ((Moins) expr).getFilsDroit(), "SUB", tds, code);

            case MUL -> genererBinaire(((Multiplication) expr).getFilsGauche(), ((Multiplication) expr).getFilsDroit(), "MUL", tds, code);

            case DIV -> genererBinaire(((Division) expr).getFilsGauche(), ((Division) expr).getFilsDroit(), "DIV", tds, code);

            default -> throw new UnsupportedOperationException("Expression non gérée: " + expr.getCat());
        }

        return code.toString();
    }


    /**
     * Génère le code pour une constante
     * @param c la constante à générer
     */
    private void genererConst(Const c, StringBuilder code) {
        code.append("\tCMOVE(").append(c.getValeur()).append(", R0)\n");
        code.append("\tPUSH(R0)\n");
    }

    /**
     * Génère le code pour un identifiant en fonction de sa catégorie (global, local ou paramètre)
     * @param idf l'identifiant à générer
     * @param tds la table des symboles
     */
    private void genererIdf(Idf idf, Tds tds, StringBuilder code) {
        String nom = idf.getValeur().toString();

        // rechercher d'abord dans la TDS avec le scope de la fonction courante (pour gérer les variables locales)
        Item item = null;
        if (scopeFonction != null && !scopeFonction.isBlank()) {
            item = tds.rechercher(nom + "@" + scopeFonction);// nom@scope
        }

        //si pas trouvé, rechercher dans la TDS sans scope (pour gérer les variables globales)
        if (item == null) {
            item = tds.rechercher(nom);
        }

        if (item == null) {
            throw new IllegalArgumentException("Identifiant non trouvé dans la TDS: " + nom);
        }

        switch (item.getCategorie()) {
            // global: on charge directement en mémoire
            case GLOBAL -> code.append("\tLD(").append(nom).append(", R0)\n").append("\tPUSH(R0)\n");

            // local: on utilise l'offset pour accéder à la variable dans la pile
            case LOCAL -> {
                int offset = item.getRang(); // local0=0, local1=1 a partir de BP
                code.append("\tGETFRAME(").append(offset*4).append(", R0)\n").append("\tPUSH(R0)\n");
            }

            // paramètre: on utilise l'offset pour accéder au paramètre dans la pile (offset = rang + 4)
            case PARAM -> {
                int nbParam = tds.getNbParamFonction(scopeFonction);
                int offset = -4 * (2 + nbParam - item.getRang());
                code.append("\tGETFRAME(").append(offset).append(", R0)\n").append("\tPUSH(R0)\n");
            }

            default -> throw new IllegalArgumentException("Identifiant de catégorie non gérée: " + item.getCategorie());
        }
    }

    /**
     * Génère le code pour une lecture
     */
    private void genererLire(StringBuilder code) {
        code.append("\tRDINT()\n").append("\tPUSH(R0)\n");
    }

    /**
     * Génère le code pour une opération binaire (addition, soustraction, multiplication, division)
     * @param gauche le noeud de gauche de l'opération
     * @param droit le noeud de droit de l'opération
     * @param operation le nom de l'opération à générer (ADD, SUB, MUL, DIV)
     * @param tds la table des symboles
     */
    private void genererBinaire(Noeud gauche, Noeud droit, String operation, Tds tds, StringBuilder code) {
        // chaque sous-expression génère son code dans la pile
        code.append(generer(gauche, tds));
        code.append(generer(droit, tds));

        code.append("\tPOP(R1)\n"); // droit dans R1
        code.append("\tPOP(R0)\n"); // gauche dans R0
        code.append("\t").append(operation).append("(R0, R1, R0)\n"); // R0 = R0 op R1
        code.append("\tPUSH(R0)\n"); // résultat dans R0 et poussé sur la pile
    }

}
