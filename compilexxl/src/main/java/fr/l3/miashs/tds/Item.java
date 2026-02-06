package fr.l3.miashs.tds;

/** Un item de la table des symboles,
 * représentant une fonction, une variable globale,
 * un paramètre ou une variable locale.
 **/
public class Item {

    // champs communs à tous les symboles
    private final String nom;
    private final String type;// int ou void
    private final CategorieSymbole categorie;

    // champs optionnels selon la catégorie du symbole
    private String scope; //pour param/local de f (scope=f)
    private Integer rang; // rang du param (0,1,2...) ou rang local
    private Integer nbParam; //pour une fonction
    private Integer nbVar; // nb variables locales
    private Integer val; //pour une globale initialisée

    /**
     * Constructeur de base pour un item de la table des symboles
     * @param nom le nom du symbole
     * @param type le type du symbole (int ou void)
     * @param categorie la catégorie du symbole (FONCTION, GLOBAL, PARAM, LOCAL)
     */
    public Item(String nom, String type, CategorieSymbole categorie) {
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
    }
}
