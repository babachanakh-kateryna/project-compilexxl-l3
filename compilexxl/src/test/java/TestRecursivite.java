import fr.l3.miashs.generation.GenerateurProgramme;
import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * test de genération de code pour une fonction récursive (exemple 9)
 */
public class TestRecursivite {
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


        // Fonction f
        Fonction f = new Fonction("f");
        prog.ajouterUnFils(f);

        // SI : (a <= 0) alors return 0
        Si si = new Si();
        f.ajouterUnFils(si);

        // condition : INFERIEUR_EGAL( IDF a, CONST 0 )
        InferieurEgal cond = new InferieurEgal();
        cond.setFilsGauche(new Idf("a"));
        cond.setFilsDroit(new Const(0));
        si.setCondition(cond);

        // bloc THEN : return 0
        Bloc blocAlors = si.getBlocAlors();
        Retour ret0 = new Retour("f");
        ret0.setLeFils(new Const(0));
        blocAlors.ajouterUnFils(ret0);

        // return a + f(a - 1)
        Retour retRec = new Retour("f");

        // (a - 1)
        Moins aMoins1 = new Moins();
        aMoins1.setFilsGauche(new Idf("a"));
        aMoins1.setFilsDroit(new Const(1));

        // appel f(a - 1)
        Appel appelF = new Appel("f");
        appelF.ajouterUnFils(aMoins1);

        // a + appelF
        Plus plus = new Plus();
        plus.setFilsGauche(new Idf("a"));
        plus.setFilsDroit(appelF);

        retRec.setLeFils(plus);
        f.ajouterUnFils(retRec);


        // Fonction main
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);

        // ecrire( f(6) )
        Ecrire ecr = new Ecrire();
        main.ajouterUnFils(ecr);

        Appel appelFmain = new Appel("f");
        appelFmain.ajouterUnFils(new Const(6));
        ecr.setLeFils(appelFmain);


        // Construction de tds
        Tds tds = new Tds();

        // main : void
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        tds.ajouter(itemMain);

        // f : int, 1 param, 0 local
        Item itemF = new Item("f", "int", CategorieSymbole.FONCTION);
        itemF.setNbParam(1);
        tds.ajouter(itemF);

        // paramètre a de f : a@f rang 0
        Item pA = new Item("a@f", "int", CategorieSymbole.PARAM);
        pA.setScope("f");
        pA.setRang(0);
        tds.ajouter(pA);


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
