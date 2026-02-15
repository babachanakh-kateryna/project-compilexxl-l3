package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.Tds;

import java.util.List;

/**
 * Classe permettant de générer le code assembleur
 * pour une instruction SI
 */

/*
pseudo code vu en cours de compilation :

generer_si
    -> a : arbre
    <- code : ASM

debut
    code <- Si_a.valeur :
    code <- code + generer_condition(fils1(a))
    code <- code + POP(R0)
    code <- code + BF(R0, sinon_a.valeur)

    code <- code + generer_bloc(fils2(a))
    code <- code + BR(fsi_a.valeur)
    code <- sinon_a.valeur :
    code <- code + generer_bloc(fils3(a))
    code <- fsi_a.valeur :
fin
 */

public class GenerateurSi {

    private final String scopeFonction;
    private final GenerateurCondition genCond;
    private final GenerateurBloc genBloc;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurSi(String scopeFonction) {
        this.scopeFonction = scopeFonction;
        this.genCond = new GenerateurCondition(scopeFonction);
        this.genBloc = new GenerateurBloc(scopeFonction);
    }

    /**
     * Génère le code pour une instruction SI
     * @param si le noeud de l'instruction SI à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour l'instruction SI
     */
    public String generer(Si si, Tds tds) {
        if (si == null) return "";

        List<Noeud> fils = si.getFils();
        if (fils == null || fils.size() < 2) {
            throw new IllegalArgumentException("SI invalide : condition + bloc THEN attendus.");
        }

        int id = si.getValeur();

        String labelSi = "si_" + id;
        String labelSinon = "sinon_" + id;
        String labelFin = "fsi_" + id;

        StringBuilder code = new StringBuilder();

        // label début
        code.append(labelSi).append(":\n");

        // condition
        code.append(genCond.generer(si.getCondition(), tds));
        code.append("\tPOP(R0)\n");
        code.append("\tBF(R0, ").append(labelSinon).append(")\n");

        // bloc then
        code.append(genBloc.generer(si.getBlocAlors(), tds));
        code.append("\tBR(").append(labelFin).append(")\n");

        // bloc else
        code.append(labelSinon).append(":\n");
        code.append(genBloc.generer(si.getBlocSinon(), tds));

        // fin
        code.append(labelFin).append(":\n");

        return code.toString();
    }
}
