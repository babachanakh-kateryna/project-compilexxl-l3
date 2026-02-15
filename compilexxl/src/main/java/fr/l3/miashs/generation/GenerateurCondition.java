package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.Tds;

import java.util.List;

/**
 * Classe permettant de générer le code assembleur
 * pour une condition (comparaison)
 */

/*
pseudo code vu en cours de compilation :

generer_condition(a)
    -> a : arbre
    <- code : ASM
début
    cas a.categorie de
        superieur :
            code <- generer_expression(fils1(a))
            code += generer_expression(fils2(a))
            code += POP(R1)
            code += POP(R2)
            code += CMPLT(R2, R1, R3)
            сode += PUSH(R3)

       ... autres comparateurs
fin

 */
public class GenerateurCondition {

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
        if (cond == null) return "";

        // vérifier que le noeud est bien une condition
        List<Noeud> fils = cond.getFils();
        if (fils == null || fils.size() < 2) {
            throw new IllegalArgumentException("Condition invalide : 2 fils attendus.");
        }

        StringBuilder code = new StringBuilder();
        GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);

        // générer opérande gauche
        code.append(genExpr.generer(fils.get(0), tds));

        // générer opérande droite
        code.append(genExpr.generer(fils.get(1), tds));

        // récupérer les deux valeurs
        code.append("\tPOP(R1)\n");
        code.append("\tPOP(R2)\n");

        switch (cond.getCat()) {

            //supérieur (fils_gauche > fils_droite <=> fils_droite < fils_gauche)
            case SUP -> code.append("\tCMPLT(R1, R2, R3)\n");

            //inférieur (fils_gauche < fils_droite)
            case INF -> code.append("\tCMPLT(R2, R1, R3)\n");

            //supérieur ou égal (fils_gauche >= fils_droite <=> NOT(fils_gauche < fils_droite))
            case SUPE -> {
                code.append("\tCMPLT(R2, R1, R3)\n");// fils_gauche < fils_droite ?
                code.append("\tCMPEQ(R3, R31, R3)\n");// inverse => fils_gauche >= fils_droite
            }

            //inférieur ou égal (fils_gauche <= fils_droite <=> NOT(fils_gauche > fils_droite) <=> NOT(fils_droite < fils_gauche))
            case INFE -> {
                code.append("\tCMPLT(R1, R2, R3)\n");   // fils_droite < fils_gauche ? (donc fils_gauche > fils_droite)
                code.append("\tCMPEQ(R3, R31, R3)\n");  // inverse => fils_gauche <= fils_droite
            }

            //égal (fils_gauche == fils_droite)
            case EG -> code.append("\tCMPEQ(R2, R1, R3)\n");

            //différent (fils_gauche != fils_droite <=>  NOT(fils_gauche == fils_droite))
            case DIF -> {
                code.append("\tCMPEQ(R2, R1, R3)\n"); // R3 = (R2==R1)
                code.append("\tCMPEQ(R3, R31, R3)\n"); //R3 = (R3==0) => inverse => (R2!=R1)
            }

            default -> throw new UnsupportedOperationException("Condition non gérée : " + cond.getCat());
        }

        code.append("\tPUSH(R3)\n");
        return code.toString();
    }
}

