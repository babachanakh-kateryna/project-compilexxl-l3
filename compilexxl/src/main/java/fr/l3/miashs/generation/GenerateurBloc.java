package fr.l3.miashs.generation;

import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

/**
 * Génère le code assembleur pour un bloc d'instructions
 */

/*
generer_bloc
    -> a : arbre
    <- code : ASM

debut
    code ← ∅
    pour chaque f ∈ fils(a)
     faire
        code ← code + generer_instruction(f)
    fin
fin

 */
public class GenerateurBloc {

    private final StringBuilder out = new StringBuilder();

    // fonction courante
    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurBloc(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code assembleur pour un bloc
     * @param bloc le noeud bloc
     * @param tds la table des symboles
     * @return le code généré
     */
    public String generer(Noeud bloc, Tds tds) {
        out.setLength(0);
        genererBloc(bloc, tds);
        return out.toString();
    }

    /**
     * Méthode interne qui parcourt les instructions du bloc
     * @param n le noeud bloc
     * @param tds la table des symboles
     */
    private void genererBloc(Noeud n, Tds tds) {

        if (n == null || n.getFils() == null) return;

        GenerateurInstruction genInst = new GenerateurInstruction(scopeFonction);

        for (Noeud instr : n.getFils()) {
            out.append(genInst.generer(instr, tds));
        }
    }
}
