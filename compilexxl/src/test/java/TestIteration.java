import fr.l3.miashs.generation.GenerateurProgramme;
import fr.l3.miashs.tds.CategorieSymbole;
import fr.l3.miashs.tds.Item;
import fr.l3.miashs.tds.Tds;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * Test de genération de code pour iteration (exemple 8)
 */
public class TestIteration {
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

        //i = 0
        Affectation affInit = new Affectation();
        Idf idfI1 = new Idf("i");
        Const c0 = new Const(0);
        affInit.setFilsGauche(idfI1);
        affInit.setFilsDroit(c0);

        // tantque (i < 6) { ecrire(i); i = i + 1; }
        TantQue tq = new TantQue(); // valeur auto (compteur) dans ARBRE
        // condition : i < 6
        Inferieur cond = new Inferieur();
        cond.setFilsGauche(new Idf("i"));
        cond.setFilsDroit(new Const(6));
        tq.setCondition(cond);

        // bloc
        Bloc bloc = new Bloc();

        // ecrire(i)
        Ecrire ecr = new Ecrire();
        ecr.setLeFils(new Idf("i"));
        bloc.ajouterUnFils(ecr);

        // i = i + 1
        Affectation affInc = new Affectation();
        affInc.setFilsGauche(new Idf("i"));
        Plus plus = new Plus();
        plus.setFilsGauche(new Idf("i"));
        plus.setFilsDroit(new Const(1));
        affInc.setFilsDroit(plus);
        bloc.ajouterUnFils(affInc);

        tq.setBloc(bloc);

        // ajouter les instructions dans main (dans l'ordre)
        main.ajouterUnFils(affInit);
        main.ajouterUnFils(tq);


        // Construction de tds

        //fonction main
        Tds tds = new Tds();
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        tds.ajouter(itemMain);

        // Variables globales
        Item itemI = new Item("i", "int", CategorieSymbole.GLOBAL);
        tds.ajouter(itemI);


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
