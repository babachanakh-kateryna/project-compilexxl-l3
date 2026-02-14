package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.Tds;

/**
 * Classe permettant de générer le code assembleur
 * pour une boucle TantQue
 */

/*
generer_tq
    -> a : arbre
    <- code : ASM

debut
    code <- tq_a.valeur :
    code <- code + generer_condition(fg(a))
    code <- code + POP(R0)
    code <- code + BF(ftq_a.valeur)

    code <- code + generer_bloc(fd(a))
    code <- code + BR(tq_a.valeur)
    ftq_a.valeur :
fin
 */
public class GenerateurTantQue {

    private final StringBuilder out = new StringBuilder();
    private final GenerateurCondition genCond;
    private final GenerateurBloc genBloc;

    public GenerateurTantQue(String scopeFonction) {
        this.genCond = new GenerateurCondition(scopeFonction);
        this.genBloc = new GenerateurBloc(scopeFonction);
    }

    public String generer(Noeud tqNoeud, Tds tds) {
        out.setLength(0);
        genererTq((TantQue) tqNoeud, tds);
        return out.toString();
    }

    private void genererTq(TantQue tq, Tds tds) {

        int id = tq.getValeur();

        String labelDebut = "tq_" + id;
        String labelFin = "ftq_" + id;

        out.append(labelDebut).append(":\n");

        // condition
        out.append(genCond.generer(tq.getFils().get(0), tds));
        out.append("\tPOP(R0)\n");
        out.append("\tBF(R0, ").append(labelFin).append(")\n");

        // bloc
        out.append(genBloc.generer(tq.getFils().get(1), tds));

        out.append("\tBR(").append(labelDebut).append(")\n");

        out.append(labelFin).append(":\n");
    }
}

