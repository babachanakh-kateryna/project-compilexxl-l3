package fr.l3.miashs.generation;

import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

/**
 * Classe de génération de code pour les retours de fonctions
 */

/*
pseudo code vu en cours de compilation :

generer_retour:
    -> a : arbre
    <- code : string
debut
    code <- generer_expression(fils(a))
    offset <- 2 + a.valeur.nb_param
    code +<- POP(R0)
             PUTFRAME(R0, offset*4)
             BR(ret_a.valeur.nom)
fin

 */
public class GenerateurRetour {

    private final StringBuilder out = new StringBuilder();
    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurRetour(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code pour un retour de fonction
     * @param a le noeud de retour à générer
     * @param tds la table des symboles pour récupérer les informations sur la fonction courante
     * @return le code assembleur généré pour le retour de fonction
     */
    public String generer(Noeud a, Tds tds) {
        out.setLength(0);
        // Vérification que le noeud est un retour
        if (!(a instanceof Retour ret)) {
            throw new IllegalArgumentException("Le noeud doit être un retour");
        }

        Noeud expression = ret.getLeFils();

        if (expression != null) {
            // create un générateur d'expression pour la fonction courante
            GenerateurExpression genExpr = new GenerateurExpression(scopeFonction);
            out.append(genExpr.generer(expression, tds));

            //offset <- 2 + nb_param
            int nbParam = getNbParamFonction(tds, scopeFonction);
            int offset = 2 + nbParam;

            out.append("\tPOP(R0)\n");
            out.append("\tPUTFRAME(R0, ").append(offset * 4).append(")\n");
        }
        out.append("\tBR(ret_").append(scopeFonction).append(")\n");

        return out.toString();
    }

    /**
     * Récupère le nombre de paramètres d'une fonction à partir de la TDS
     * @param tds la table des symboles
     * @param nomFonction le nom de la fonction dont on veut connaître le nombre de paramètres
     * @return le nombre de paramètres de la fonction
     */
    private int getNbParamFonction(Tds tds, String nomFonction) {
        Item f = tds.rechercher(nomFonction);
        if (f == null) {
            throw new IllegalArgumentException("Fonction non trouvée dans la TDS: " + nomFonction);
        }
        if (f.getCategorie() != CategorieSymbole.FONCTION) {
            throw new IllegalArgumentException("Le symbole '" + nomFonction + "' n'est pas une fonction");
        }
        if (f.getNbParam() == null) {
            throw new IllegalArgumentException("nbParam non défini pour la fonction: " + nomFonction);
        }
        return f.getNbParam();
    }
}
