package fr.l3.miashs.generation;

import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

import java.util.List;

/**
 * Génère le code assembleur pour un bloc d'instructions
 */
public class GenerateurBloc {

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
    public String generer(Bloc bloc, Tds tds) {
        if (bloc == null) return "";

        StringBuilder code = new StringBuilder();
        List<Noeud> insts = bloc.getFils();
        if (insts == null) return "";

        GenerateurInstruction genInst = new GenerateurInstruction(scopeFonction);

        for (Noeud inst : insts) {
            code.append(genInst.generer(inst, tds));
        }
        return code.toString();
    }
}
