package fr.l3.miashs.generation;

import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;

/**
 * Classe principale du projet, elle utilise les autres classes pour générer un programme complet
 */
public class GenerateurProgramme {

    /**
     * Génère le code pour un programme complet
     * @param a le noeud racine du programme à générer
     * @param tds la table des symboles
     * @return le code assembleur complet généré pour le programme
     */
    public String generer(Noeud a, Tds tds) {

        // verifications
        if (a == null) {
            throw new IllegalArgumentException("Arbre programme null");
        }
        if (a.getCat() != Noeud.Categories.PROG) {
            throw new IllegalArgumentException("Le noeud racine doit être PROG, trouvé: " + a.getCat());
        }
        if (tds == null) {
            throw new IllegalArgumentException("TDS null");
        }

        StringBuilder code = new StringBuilder();

        // entete du programme
        code.append(".include beta.uasm\n");
        code.append(".options tty\n");
        code.append(".include intio.uasm\n");
        code.append("\tCMOVE(pile, SP)\n");
        code.append("\tBR(debut)\n");

        // section DATA
        code.append(new GenerateurData().generer(tds));

        //point d'entrée
        code.append("debut:\n");
        code.append("\tCALL(main)\n");
        code.append("\tHALT()\n");

        // génération de toutes les fonctions
        GenerateurFonction genFct = new GenerateurFonction();
        if (a.getFils() != null) {
            for (Noeud f : a.getFils()) {
                if (f == null) continue;
                if (f.getCat() != Noeud.Categories.FONCTION) {
                    throw new IllegalArgumentException("Fils de PROG non FONCTION: " + f.getCat());
                }
                code.append(genFct.generer((Fonction) f, tds));
            }
        }


        // pile
        code.append("pile:\n");
        code.append("\tSTORAGE(200)\n");

        return code.toString();
    }
}
