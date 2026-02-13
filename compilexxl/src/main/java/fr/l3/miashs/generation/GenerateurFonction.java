package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;


/**
 * Classe de génération de fonctions
 */

/*
pseudo code vu en cours de compilation :

generer_fonction:
    -> a : arbre
    <- code : string
debut
    code <- a.valeur.nom
    code +<- PUSH(LP)
             PUSH(BP)
             MOVE(SP, BP)
             ALLOCATE(a.valeur.nb_param)
    pour chaque f ∈ fils(a) faire
        code +<- generer_instruction(f)
    fpour
    code +<- RET_a.valeur.nom
    code +<- DEALLOCATE(a.valeur.nb_param)
             POP(BP)
             POP(LP)
             RTN()
fin

 */
public class GenerateurFonction {

    /**
     * Génère le code pour une fonction donnée
     * @param fonction le noeud de la fonction à générer
     * @param tds la table des symboles
     * @return le code assembleur généré pour la fonction
     */
    public String generer(Fonction fonction, Tds tds) {
        if (fonction == null) return "";

        StringBuilder code = new StringBuilder();

        String nomFonction = fonction.getValeur().toString();

        // recuperer l'item fonction
        Item itemF = tds.rechercher(nomFonction);

        // verifier que c bien une fonction
        if (itemF == null || itemF.getCategorie() != CategorieSymbole.FONCTION) {
            throw new IllegalArgumentException("Fonction non trouvée dans la TDS: " + nomFonction);
        }

        int nbLoc = (itemF.getNbVar() == null) ? 0 : itemF.getNbVar();

        //fonction
        code.append(nomFonction).append(":\n");

        code.append("PUSH(LP)\n");
        code.append("PUSH(BP)\n");
        code.append("MOVE(SP, BP)\n");
        code.append("ALLOCATE(").append(nbLoc).append(")\n");

        // genérer le code pour les instructions de la fonction
        GenerateurInstruction genInst = new GenerateurInstruction(nomFonction);

        // on génère le code pour chaque instruction de la fonction
        if (fonction.getFils() != null) {
            for (Noeud instr : fonction.getFils()) {
                code.append(genInst.generer(instr, tds));
            }
        }

        //retour
        code.append("ret_").append(nomFonction).append(":\n");

        //fin
        code.append("DEALLOCATE(").append(nbLoc).append(")\n");
        code.append("POP(BP)\n");
        code.append("POP(LP)\n");
        code.append("RTN()\n");

        return code.toString();
    }
}
