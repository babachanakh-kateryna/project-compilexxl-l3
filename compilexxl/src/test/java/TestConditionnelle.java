import fr.l3.miashs.generation.GenerateurProgramme;
import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * Test de genération de code pour une conditionnelle (exemple 7)
 */
public class TestConditionnelle {
    public static void main(String[] args) {

        // Construction de ast

        // PROG
        Noeud prog = new Noeud() {
            @Override
            public String getLabel() {
                return "PROG";
            }
        };
        prog.setCat(Noeud.Categories.PROG);
        prog.setFils(new ArrayList<>());

        // FONCTION main
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);

        // SI
        Si si = new Si();
        main.ajouterUnFils(si);

        // condition : SUPERIEUR( IDF a, IDF b )
        Superieur sup = new Superieur();
        sup.setFilsGauche(new Idf("a"));
        sup.setFilsDroit(new Idf("b"));
        si.setCondition(sup);

        // bloc THEN : x = 1000
        Bloc blocAlors = si.getBlocAlors();
        Affectation affAlors = new Affectation();
        affAlors.setFilsGauche(new Idf("x"));
        affAlors.setFilsDroit(new Const(1000));
        blocAlors.ajouterUnFils(affAlors);

        // bloc ELSE : x = 2000
        Bloc blocSinon = si.getBlocSinon();
        Affectation affSinon = new Affectation();
        affSinon.setFilsGauche(new Idf("x"));
        affSinon.setFilsDroit(new Const(2000));
        blocSinon.ajouterUnFils(affSinon);


        // Construction de tds

        //fonction main
        Tds tds = new Tds();
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        tds.ajouter(itemMain);

        // Variables globales
        Item itemA = new Item("a", "int", CategorieSymbole.GLOBAL);
        itemA.setVal(1);
        tds.ajouter(itemA);

        Item itemB = new Item("b", "int", CategorieSymbole.GLOBAL);
        itemB.setVal(2);
        tds.ajouter(itemB);

        Item itemX = new Item("x", "int", CategorieSymbole.GLOBAL);
        tds.ajouter(itemX);


        //Affichage ast
        System.out.println("========== ARBRE ABSTRAIT ==========");
        TxtAfficheur.afficher(prog);

        //Affichage tds
        System.out.println("========== TABLE DES SYMBOLES ==========");
        System.out.println(tds);

        // Génération de code
        GenerateurProgramme genProg = new GenerateurProgramme();
        String code = genProg.generer(prog, tds);

        System.out.println("========== CODE GENERE ==========");
        System.out.println(code);
    }
}
