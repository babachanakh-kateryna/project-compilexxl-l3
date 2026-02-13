package fr.l3.miashs.generation;

import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;

/**
 * Génération de la section DATA : variables globales
 */

/*
pseudo code vu en cours de compilation :

generer_data:
    -> tds
    <- code
debut
    code <- Ø
    pour chaque e de tds faire
        si e est globale
         alors
              code +<- e.nom : LONG(e.valeur)
        fsi
    fpour
    retourner code
fin

 */

public class GenerateurData {

    /**
     * Génère le code d'initialisation des variables globales à partir de la TDS
     * @param tds la table des symboles
     * @return le code d'initialisation des variables globales
     */
    public String generer(Tds tds) {
        StringBuffer sb = new StringBuffer();

        //pour chaque variable de la tds, on génère une ligne de code d'initialisation
        for (Item item : tds.getItems().values()) {

            //si c une variable GLOBALE
            if(item.getCategorie() == CategorieSymbole.GLOBAL){
                int valeur = (item.getVal() != null) ? item.getVal() : 0; //si la variable n'a pas de valeur, on l'initialise à 0
                sb.append("\t").append(item.getNom()).append(" : LONG(").append(valeur).append(")\n");
            }

        }
        return sb.toString();
    }
}
