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

    //getters
    public String getNom() { return nom; }
    public String getType() { return type; }
    public CategorieSymbole getCategorie() { return categorie; }
    public String getScope() { return scope; }
    public Integer getRang() { return rang; }
    public Integer getNbParam() { return nbParam; }
    public Integer getNbVar() { return nbVar; }
    public Integer getVal() { return val; }

    //setters pour les champs optionnels
    public void setScope(String scope) { this.scope = scope; }
    public void setRang(Integer rang) { this.rang = rang; }
    public void setNbParam(Integer nbParam) { this.nbParam = nbParam; }
    public void setNbVar(Integer nbVar) { this.nbVar = nbVar; }
    public void setVal(Integer val) { this.val = val; }

    /**
     * Représentation textuelle d'un item de la table des symboles,
     * affichant les champs pertinents selon la catégorie du symbole
     *
     * @return une chaîne de caractères représentant l'item
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        sb.append("nom=").append(nom)
                .append(", type=").append(type)
                .append(", cat=").append(categorie);

        if (nbParam != null) sb.append(", nb_param=").append(nbParam);
        if (nbVar != null) sb.append(", nb_var=").append(nbVar);
        if (scope != null) sb.append(", scope=").append(scope);
        if (rang != null) sb.append(", rang=").append(rang);
        if (val != null) sb.append(", val=").append(val);

        sb.append(" }");
        return sb.toString();
    }
}
