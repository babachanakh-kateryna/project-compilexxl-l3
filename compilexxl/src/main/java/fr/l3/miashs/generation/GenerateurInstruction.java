package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;


/**
 * Classe de génération d'instructions
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

            case AFF -> new GenerateurAffectation(scopeFonction).generer((Affectation) a, tds);

            case APPEL -> new GenerateurAppel(scopeFonction).generer((Appel) a, tds, false);

            case SI -> new GenerateurSi(scopeFonction).generer((Si) a, tds);

            case TQ -> new GenerateurTantQue(scopeFonction).generer((TantQue) a, tds);

            case ECR -> new GenerateurEcrire(scopeFonction).generer((Ecrire) a, tds);

            case RET -> new GenerateurRetour(scopeFonction).generer((Retour) a, tds);

            default -> throw new UnsupportedOperationException("Instruction non gérée: " + a.getCat());
        };
    }
}
