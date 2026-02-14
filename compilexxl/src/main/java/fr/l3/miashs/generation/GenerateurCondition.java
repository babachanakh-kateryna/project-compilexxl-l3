package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.Tds;

/**
 * Classe permettant de générer le code assembleur
 * pour une condition (comparaison)
 */

/*
generer_condition(a)
    -> a : arbre
    <- code : ASM
début
    code <- generer_expression(fils1(a))
    code += generer_expression(fils2(a))
    code += POP(R1)
    code += POP(R2)
    cas a.categorie de
        SUP :
            code += CMPLT(R2, R1, R3)
        INF :
            code += CMPLT(R1, R2, R3)
        SUPE :
            code += CMPLE(R2, R1, R3)
        INFE :
            code += CMPLE(R1, R2, R3)
        EG :
            code += CMPEQ(R2, R1, R3)
        DIF :
            code += CMPNE(R2, R1, R3)
    fin cas
    code += PUSH(R3)
fin

 */
public class GenerateurCondition {

    private final StringBuilder out = new StringBuilder();
    private final String scopeFonction;

    /**
     * Constructeur de la classe
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurCondition(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code assembleur d'une condition
     * @param cond le noeud condition
     * @param tds la table des symboles
     * @return le code assembleur généré
     */
    public String generer(Noeud cond, Tds tds) {
        out.setLength(0);
        genererCondition(cond, tds);
        return out.toString();
    }

    /**
     * Méthode interne de génération de condition
     */
    private void genererCondition(Noeud n, Tds tds) {

        GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);

        // générer opérande gauche
        out.append(genExpr.generer(n.getFils().get(0), tds));

        // générer opérande droite
        out.append(genExpr.generer(n.getFils().get(1), tds));

        // récupérer les deux valeurs
        out.append("\tPOP(R1)\n");
        out.append("\tPOP(R2)\n");

        switch (n.getCat()) {

            //supérieur
            case SUP ->
                    out.append("\tCMPLT(R2, R1, R3)\n"); // R2 > R1
            //inférieur
            case INF ->
                    out.append("\tCMPLT(R1, R2, R3)\n"); // R2 < R1
            //supérieur ou égal
            case SUPE -> {
                out.append("\tCMPLT(R2, R1, R3)\n");
                out.append("\tCMPEQ(R2, R1, R4)\n");
                out.append("\tADD(R3, R4, R3)\n");
            }
            //inférieur ou égal
            case INFE -> {
                out.append("\tCMPLT(R1, R2, R3)\n");
                out.append("\tCMPEQ(R2, R1, R4)\n");
                out.append("\tADD(R3, R4, R3)\n");
            }
            //égal
            case EG ->
                    out.append("\tCMPEQ(R2, R1, R3)\n");
            //différent
            case DIF ->
                    out.append("\tCMPNE(R2, R1, R3)\n");

            default ->
                    throw new UnsupportedOperationException(
                            "Condition non gérée : " + n.getCat());
        }

        out.append("\tPUSH(R3)\n");
    }
}

