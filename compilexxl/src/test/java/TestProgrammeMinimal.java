import fr.l3.miashs.generation.*;
import fr.l3.miashs.tds.*;
import fr.ul.miashs.compil.arbre.*;

import java.util.ArrayList;

/**
 * Test de génération d'un programme minimal
 */
public class TestProgrammeMinimal {

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

        // Construction de tds

        Tds tds = new Tds();
        Item itemMain = new Item("main", "void", CategorieSymbole.FONCTION);
        itemMain.setNbParam(0);
        itemMain.setNbVar(0);
        tds.ajouter(itemMain);

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
