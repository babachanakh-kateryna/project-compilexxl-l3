package fr.l3.miashs.tds;

/**
 * Classe utilitaire pour construire progressivement la table des symboles
 * pendant l'analyse syntaxique
 */
public class ConstructeurTds {

    private final Tds tds;

    public ConstructeurTds() {
        this.tds = new Tds();
    }

    public Tds getTds() {
        return tds;
    }

    //declarations globales
    public void ajouterGlobale(String nom) {
        Item item = new Item(nom, "int", CategorieSymbole.GLOBAL);
        tds.ajouter(item);
    }

    public void ajouterGlobale(String nom, Integer val) {
        Item item = new Item(nom, "int", CategorieSymbole.GLOBAL);
        item.setVal(val);
        tds.ajouter(item);
    }

    //fonctions
    public void ajouterFonction(String nom, String type) {
        Item item = new Item(nom, type, CategorieSymbole.FONCTION);
        tds.ajouter(item);
    }

    public void setNbParamFonction(String nomFonction, int nbParam) {
        if (nbParam == 0) return;
        Item item = tds.rechercher(nomFonction);
        verifierFonction(item, nomFonction);
        item.setNbParam(nbParam);
    }

    public void setNbVarFonction(String nomFonction, int nbVar) {
        if (nbVar == 0) return;
        Item item = tds.rechercher(nomFonction);
        verifierFonction(item, nomFonction);
        item.setNbVar(nbVar);
    }

    //param

    public void ajouterParam(String nomFonction, String nomParam, int rang) {
        String nomComplet = nomParam + "@" + nomFonction;

        Item item = new Item(nomComplet, "int", CategorieSymbole.PARAM);
        item.setScope(nomFonction);
        item.setRang(rang);

        tds.ajouter(item);
    }

    //variables locales

    public void ajouterLocal(String nomFonction, String nomLocal, int rang) {
        String nomComplet = nomLocal + "@" + nomFonction;

        Item item = new Item(nomComplet, "int", CategorieSymbole.LOCAL);
        item.setScope(nomFonction);
        item.setRang(rang);

        tds.ajouter(item);
    }

    //verifications
    private void verifierFonction(Item item, String nomFonction) {
        if (item == null) {
            throw new IllegalArgumentException("Fonction non trouvée dans la TDS : " + nomFonction);
        }
        if (item.getCategorie() != CategorieSymbole.FONCTION) {
            throw new IllegalArgumentException("Le symbole '" + nomFonction + "' n'est pas une fonction");
        }
    }
}