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

    private final StringBuilder out = new StringBuilder();

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
        out.setLength(0);
        genererExpression(expr, tds);
        return out.toString();
    }

    /**
     * Méthode interne récursive qui parcourt l’arbre de l’expression et génère le code assembleur
     * @param n le noeud de l'expression à générer
     * @param tds la table des symboles
     */
    private void genererExpression(Noeud n, Tds tds) {
        if (n == null) return;

        switch (n.getCat()) {

            case CONST -> genererConst((Const) n);

            case IDF -> genererIdf((Idf) n, tds);

            case LIRE -> genererLire();

            case APPEL -> out.append(new GenerateurAppel(scopeFonction).generer((Appel) n, tds, true));

            case PLUS -> genererBinaire(((Plus) n).getFilsGauche(), ((Plus) n).getFilsDroit(), "ADD", tds);

            case MOINS -> genererBinaire(((Moins) n).getFilsGauche(), ((Moins) n).getFilsDroit(), "SUB", tds);

            case MUL -> genererBinaire(((Multiplication) n).getFilsGauche(), ((Multiplication) n).getFilsDroit(), "MUL", tds);

            case DIV -> genererBinaire(((Division) n).getFilsGauche(), ((Division) n).getFilsDroit(), "DIV", tds);

            default -> throw new UnsupportedOperationException("Expression non gérée: " + n.getCat());
        }

    }

    /**
     * Génère le code pour une constante
     * @param c la constante à générer
     */
    private void genererConst(Const c) {
        out.append("\tCMOVE(").append(c.getValeur()).append(", R0)\n");
        out.append("\tPUSH(R0)\n");
    }

    /**
     * Génère le code pour un identifiant en fonction de sa catégorie (global, local ou paramètre)
     * @param idf l'identifiant à générer
     * @param tds la table des symboles
     */
        private void genererIdf(Idf idf, Tds tds) {
        String nom = idf.getValeur().toString();
        Item item = tds.rechercher(nom);

        if (item == null) {
            throw new IllegalArgumentException("Identifiant non trouvé dans la TDS: " + nom);
        }

        switch (item.getCategorie()) {
            // global: on charge directement en mémoire
            case GLOBAL -> out.append("\tLD(").append(nom).append(", R0)\n").append("\tPUSH(R0)\n");

            // local: on utilise l'offset pour accéder à la variable dans la pile
            case LOCAL -> {
                int offset = item.getRang(); // local0=0, local1=1 a partir de BP
                out.append("\tGETFRAME(").append(offset*4).append(", R0)\n").append("\tPUSH(R0)\n");
            }

            // paramètre: on utilise l'offset pour accéder au paramètre dans la pile (offset = rang + 4)
            case PARAM -> {
                int offset = 1 + item.getNbParam() + item.getRang(); // param0=-3, param1=-4, a partir de BP
                out.append("\tGETFRAME(").append(offset*(-4)).append(", R0)\n").append("\tPUSH(R0)\n");
            }

            default -> throw new IllegalArgumentException("Identifiant de catégorie non gérée: " + item.getCategorie());
        }
    }

    /**
     * Génère le code pour une lecture
     */
    private void genererLire() {
        out.append("\tRDINT()\n").append("\tPUSH(R0)\n");
    }

    /**
     * Génère le code pour une opération binaire (addition, soustraction, multiplication, division)
     * @param gauche le noeud de gauche de l'opération
     * @param droit le noeud de droit de l'opération
     * @param operation le nom de l'opération à générer (ADD, SUB, MUL, DIV)
     * @param tds la table des symboles
     */
    private void genererBinaire(Noeud gauche, Noeud droit, String operation, Tds tds) {
        genererExpression(gauche, tds); // résultat de gauche sur la pile
        genererExpression(droit, tds); // résultat de droit sur la pile

        out.append("\tPOP(R1)\n"); // droit dans R1
        out.append("\tPOP(R0)\n"); // gauche dans R0
        out.append("\t").append(operation).append("(R0, R1, R0)\n"); // R0 = R0 op R1
        out.append("\tPUSH(R0)\n"); // résultat dans R0 et poussé sur la pile
    }

}
