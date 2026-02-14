import fr.l3.miashs.generation.*;
import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * Test de génération de code pour une expression dans une instruction d'écriture (exemple 5)
 */
public class TestEcritureExpression {
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

        // ecrire( ... )
        Ecrire ecr = new Ecrire();
        main.ajouterUnFils(ecr);

        // Construction de l'expression : a*2 + (b-5)/3

        // a * 2
        Idf a = new Idf("a");
        Const c2 = new Const(2);
        Multiplication mul = new Multiplication();
        mul.setFilsGauche(a);
        mul.setFilsDroit(c2);

        // (b - 5) / 3
        Idf b = new Idf("b");
        Const c5 = new Const(5);
        Moins moins = new Moins();
        moins.setFilsGauche(b);
        moins.setFilsDroit(c5);

        Const c3 = new Const(3);
        Division div = new Division();
        div.setFilsGauche(moins);
        div.setFilsDroit(c3);

        // a*2 + ((b-5)/3)
        Plus plus = new Plus();
        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);

        // fils unique de ECR = l'expression
        ecr.setLeFils(plus);

        // Construction de tds

        //fonction main
        Tds tds = new Tds();
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        tds.ajouter(itemMain);

        // Variables globales
        Item itemA = new Item("a", "int", CategorieSymbole.GLOBAL);
        itemA.setVal(100);
        tds.ajouter(itemA);

        Item itemB = new Item("b", "int", CategorieSymbole.GLOBAL);
        itemB.setVal(170);
        tds.ajouter(itemB);

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
