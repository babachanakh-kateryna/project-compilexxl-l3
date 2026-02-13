package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;


/**
 * Génère le code assembleur pour un appel de fonction
 */

/*
pseudo code vu en cours de compilation :

    generer_appel:
        -> a : arbre
        <- code : string
    debut
        code <- Ø
        si a.valeur.type != void
            alors
                code +<- ALLOCATE(1)
        fsi

        pour chaque f ∈ fils(a) faire
            code +<- generer_expression(f)
        fpour

        code +<- CALL(a.valeur.nom)
        code +<- DEALLOCATE(a.valeur.nb_param)
    fin

    !!on va ajouter si une variante "instruction" :
        si on ne garde pas le résultat ET type != void -> DEALLOCATE(1)
 */
public class GenerateurAppel {

    private final StringBuilder out = new StringBuilder();
    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurAppel(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code pour un appel de fonction
     * @param appel le noeud d'appel à générer
     * @param tds la table des symboles
     * @param garderResultat indique si le résultat de l'appel doit être gardé (true) ou si c'est une instruction (false)
     * @return le code assembleur généré pour l'appel de fonction
     */
    public String generer(Appel appel, Tds tds, boolean garderResultat) {
        out.setLength(0);

        String nomFonction = appel.getValeur().toString();
        Item f = tds.rechercher(nomFonction);

        // vérifier que la fonction existe dans la TDS
        if (f == null || f.getCategorie() != CategorieSymbole.FONCTION) {
            throw new IllegalArgumentException("Fonction non trouvée dans la TDS: " + nomFonction);
        }

        String type = f.getType();
        int nbParam = (f.getNbParam() == null) ? 0 : f.getNbParam();

        if (type != null && !"void".equals(type)) {
            out.append("ALLOCATE(1)\n");
        }

        // générer le code pour les arguments de l'appel
        GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);
        if (appel.getFils() != null) {
            for (Noeud arg : appel.getFils()) {
                out.append(genExpr.generer(arg, tds)); // chaque argument est généré et son résultat est poussé sur la pile
            }
        }

        // générer le code pour l'appel de la fonction
        out.append("CALL(").append(nomFonction).append(")\n");
        out.append("DEALLOCATE(").append(nbParam).append(")\n");

        // si c une instruction (on ne garde pas le résultat) => on enlève le slot result
        if (type != null && !"void".equals(type) && !garderResultat) {
            out.append("DEALLOCATE(1)\n");
        }

        return out.toString();
    }
}
