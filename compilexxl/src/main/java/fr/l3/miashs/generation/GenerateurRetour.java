package fr.l3.miashs.generation;

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

    public String generer(Noeud a) {
        //TODO: à compléter
        /*
        String code = genererExpression(a.getFils().get(0));
        int offset = 2 + a.getValeur().getNbParam();
        code += "POP(R0)\n";
        code += "PUTFRAME(R0, " + (offset * 4) + ")\n";
        code += "BR(ret_" + a.getValeur().getNom() + ")\n";
        return code;*/
        return null;
    }
}
