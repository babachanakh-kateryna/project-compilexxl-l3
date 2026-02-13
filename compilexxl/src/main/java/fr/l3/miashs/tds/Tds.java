package fr.l3.miashs.tds;

import java.util.LinkedHashMap;
import java.util.Map;

/** * Classe représentant la table des symboles (TDS) d'un programme
 **/
public class Tds {

    private final Map<String, Item> items; // items de la TDS, indexés par leur nom

    /** Constructeur de la TDS, initialisant la map d'items */
    public Tds() {
        this.items = new LinkedHashMap<>();
    }

    /**
     * Ajouter un item à la TDS
     * @param item l'item à ajouter
     */
    public void ajouter(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item ne peut pas être null");
        }
        if (items.containsKey(item.getNom())) {
            throw new IllegalArgumentException("Item déjà défini dans cette TDS : " + item.getNom());
        }
        items.put(item.getNom(), item);
    }

    /**
     * Rechercher un item dans la TDS puis remonte dans les parents
     * @param nom le nom de l'item à rechercher
     * @return l'item trouvé ou null si aucun item de ce nom n'est défini dans cette TDS
     */
    public Item rechercher(String nom) {
        Item item = items.get(nom);
        if (item != null) {
            return item;
        } else {
            return null; // symbole non trouvé
        }
    }

    // getter
    public Map<String, Item> getItems() { return items; }

    /**
     * Représentation textuelle de la TDS, affichant tous les items qu'elle contient
     *
     * @return une chaîne de caractères représentant la TDS
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TDS ").append("\n");
        for (Item item : items.values()) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
}
