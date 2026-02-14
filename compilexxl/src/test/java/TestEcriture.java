import fr.l3.miashs.generation.*;
import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * Test de génération de code pour une écriture (exemple 4)
 */
public class TestEcriture {
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

        //Instruction 1 : x = lire()*2 + (lire()-5)/3

        Affectation aff = new Affectation();
        main.ajouterUnFils(aff);

        // gauche : IDF x
        Idf x = new Idf("x");
        aff.setFilsGauche(x);

        // droite : PLUS( MUL(LIRE, CONST 2), DIV(MOINS(LIRE, CONST 5), CONST 3) )

        // MUL(LIRE, 2)
        Lire lire1 = new Lire();
        Const c2 = new Const(2);
        Multiplication mul = new Multiplication();
        mul.setFilsGauche(lire1);
        mul.setFilsDroit(c2);

        // MOINS(LIRE, 5)
        Lire lire2 = new Lire();
        Const c5 = new Const(5);
        Moins moins = new Moins();
        moins.setFilsGauche(lire2);
        moins.setFilsDroit(c5);

        // DIV( MOINS(...), 3 )
        Const c3 = new Const(3);
        Division div = new Division();
        div.setFilsGauche(moins);
        div.setFilsDroit(c3);

        // PLUS( mul, div )
        Plus plus = new Plus();
        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);

        aff.setFilsDroit(plus);

        //Instruction 2 : ecrire(x)

        Ecrire ecr = new Ecrire();
        ecr.setLeFils(new Idf("x"));
        main.ajouterUnFils(ecr);

        // Construction de tds

        //fonction main
        Tds tds = new Tds();
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        tds.ajouter(itemMain);

        // Variable globale
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
