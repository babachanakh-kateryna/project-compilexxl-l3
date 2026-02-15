package fr.l3.miashs.generation;

import fr.ul.miashs.compil.arbre.*;
import fr.l3.miashs.tds.Tds;

import java.util.List;

/**
 * Classe permettant de générer le code assembleur
 * pour une boucle TantQue
 */
public class GenerateurTantQue {

    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurTantQue(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }


    /**
     * Génère le code pour une boucle TantQue
     * @param tq le noeud de la boucle TantQue à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour la boucle TantQue
     */
    public String generer(TantQue tq, Tds tds) {
        if (tq == null) return "";

        List<Noeud> fils = tq.getFils();
        if (fils == null || fils.size() < 2) {
            throw new IllegalArgumentException("TANTQUE invalide : condition + bloc attendus.");
        }

        Noeud condition = fils.get(0);
        Noeud bloc = fils.get(1);

        int id = tq.getValeur();
        String labelDebut = "tq_" + id;
        String labelFin = "ftq_" + id;

        StringBuilder code = new StringBuilder();

        //debut
        code.append(labelDebut).append(":\n");

        // condition
        code.append(new GenerateurCondition(scopeFonction).generer(condition, tds));
        code.append("\tPOP(R0)\n");
        code.append("\tBF(R0, ").append(labelFin).append(")\n");

        // bloc
        code.append(new GenerateurBloc(scopeFonction).generer((Bloc) bloc, tds));

        // retour au début
        code.append("\tBR(").append(labelDebut).append(")\n");

        //fin
        code.append(labelFin).append(":\n");

        return code.toString();
    }
}
