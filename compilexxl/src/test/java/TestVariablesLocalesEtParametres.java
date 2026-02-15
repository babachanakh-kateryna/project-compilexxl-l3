import fr.l3.miashs.generation.GenerateurProgramme;
import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * Test de génération de code pour un programme avec variables locales et paramètres (exemple 6)
 */
public class TestVariablesLocalesEtParametres {
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

        // FONCTION f
        Fonction f = new Fonction("f");
        prog.ajouterUnFils(f);

        // res = a*2 + (b-5)/3
        Affectation aff = new Affectation();
        f.ajouterUnFils(aff);

        // gauche : IDF res
        Idf idResGauche = new Idf("res");
        aff.setFilsGauche(idResGauche);

        // droite : PLUS( MUL(IDF a, CONST 2), DIV(MOINS(IDF b, CONST 5), CONST 3) )

        // MUL(IDF a, 2)
        Idf idA = new Idf("a");
        Const c2 = new Const(2);
        Multiplication mul = new Multiplication();
        mul.setFilsGauche(idA);
        mul.setFilsDroit(c2);

        // MOINS(IDF b, 5)
        Idf idB = new Idf("b");
        Const c5 = new Const(5);
        Moins moins = new Moins();
        moins.setFilsGauche(idB);
        moins.setFilsDroit(c5);

        // DIV( MOINS(...), 3 )
        Const c3 = new Const(3);
        Division div = new Division();
        div.setFilsGauche(moins);
        div.setFilsDroit(c3);

        // PLUS(mul, div)
        Plus plus = new Plus();
        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);

        aff.setFilsDroit(plus);

        // return res
        Retour ret = new Retour("f");          // valeur portée par Retour : on met "f" (nom de la fonction)
        ret.setLeFils(new Idf("res"));         // fils = IDF res
        f.ajouterUnFils(ret);


        // FONCTION main
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);

        // ecrire( appel f(a,c) )
        Ecrire ecr = new Ecrire();
        main.ajouterUnFils(ecr);

        Appel appelF = new Appel("f");
        // args : IDF a, IDF c (dans cet ordre)
        appelF.ajouterUnFils(new Idf("a"));
        appelF.ajouterUnFils(new Idf("c"));
        ecr.setLeFils(appelF);


        // Construction de tds

        //fonction main
        Tds tds = new Tds();
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        tds.ajouter(itemMain);

        // f : int, nb_param=2, nb_var=1
        Item itemF = new Item("f", "int", CategorieSymbole.FONCTION);
        itemF.setNbParam(2);
        itemF.setNbVar(1);
        tds.ajouter(itemF);

        // globals : a=100, c=170
        Item gA = new Item("a", "int", CategorieSymbole.GLOBAL);
        gA.setVal(100);
        tds.ajouter(gA);

        Item gC = new Item("c", "int", CategorieSymbole.GLOBAL);
        gC.setVal(170);
        tds.ajouter(gC);

        // params de f : a (rang 0), b (rang 1)
        Item pA = new Item("a@f", "int", CategorieSymbole.PARAM);
        pA.setScope("f");
        pA.setRang(0);
        tds.ajouter(pA);

        Item pB = new Item("b@f", "int", CategorieSymbole.PARAM);
        pB.setScope("f");
        pB.setRang(1);
        tds.ajouter(pB);

        // local de f : res (rang 0)
        Item lRes = new Item("res@f", "int", CategorieSymbole.LOCAL);
        lRes.setScope("f");
        lRes.setRang(0);
        tds.ajouter(lRes);

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
