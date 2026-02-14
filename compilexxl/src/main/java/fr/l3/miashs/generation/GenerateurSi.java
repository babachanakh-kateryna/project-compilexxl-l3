package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.Tds;

/**
 * Classe permettant de générer le code assembleur
 * pour une instruction SI
 */

/*
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

    private final StringBuilder out = new StringBuilder();
    private final GenerateurCondition genCond;
    private final GenerateurBloc genBloc;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurSi(String scopeFonction) {
        this.genCond = new GenerateurCondition(scopeFonction);
        this.genBloc = new GenerateurBloc(scopeFonction);
    }

    public String generer(Noeud siNoeud, Tds tds) {
        out.setLength(0);
        genererSi((Si) siNoeud, tds);
        return out.toString();
    }

    private void genererSi(Si si, Tds tds) {

        int id = si.getValeur();

        String labelSi = "si_" + id;
        String labelSinon = "sinon_" + id;
        String labelFin = "fsi_" + id;

        // label début
        out.append(labelSi).append(":\n");

        // condition
        out.append(genCond.generer(si.getFils().get(0), tds));
        out.append("\tPOP(R0)\n");
        out.append("\tBF(R0, ").append(labelSinon).append(")\n");

        // bloc then
        out.append(genBloc.generer(si.getFils().get(1), tds));
        out.append("\tBR(").append(labelFin).append(")\n");

        // bloc else
        out.append(labelSinon).append(":\n");

        if (si.getFils().size() > 2) {
            out.append(genBloc.generer(si.getFils().get(2), tds));
        }

        // fin
        out.append(labelFin).append(":\n");
    }
}
