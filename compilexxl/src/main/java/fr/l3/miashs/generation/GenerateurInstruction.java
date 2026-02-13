package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;


/**
 * Classe de génération d'instructions
 */

/*
pseudo code vu en cours de compilation :

generer_instruction:
    -> a : arbre
    <- code : string
debut
    cas ou a.cat = affectation:
        code <- generer_affectation(a)
    cas ou a.cat = appel:
        code <- generer_appel(a)
    cas ou a.cat = fonction:
        code <- generer_fonction(a)
    cas ou a.cat = return:
        code <- generer_return(a)
    cas ou a.cat = if:
        code <- generer_if(a)
    cas ou a.cat = while:
        code <- generer_while(a)
fin

 */
public class GenerateurInstruction {

    private final String scopeFonction;

    /**
     * Constructeur
     * @param scopeFonction le nom de la fonction courante
     */
    public GenerateurInstruction(String scopeFonction) {
        this.scopeFonction = scopeFonction;
    }

    /**
     * Génère le code pour une instruction donnée
     * @param a le noeud de l'instruction à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour l'instruction
     */
    public String generer(Noeud a, Tds tds) {
        if (a == null) return "";

        return switch (a.getCat()) {

            //case AFF -> new GenerateurAffectation(scopeFonction).generer((Affectation) a, tds);

            case APPEL -> new GenerateurAppel(scopeFonction).generer((Appel) a, tds, false);

            //case SI -> new GenerateurSi(scopeFonction).generer((Si) a, tds);

            //case TQ -> new GenerateurTantQue(scopeFonction).generer((TantQue) a, tds);

            //case ECR -> new GenerateurEcrire(scopeFonction).generer((Ecrire) a, tds);

            case RET -> new GenerateurRetour(scopeFonction).generer(a, tds);

            default -> throw new UnsupportedOperationException("Instruction non gérée: " + a.getCat());
        };
    }
}
