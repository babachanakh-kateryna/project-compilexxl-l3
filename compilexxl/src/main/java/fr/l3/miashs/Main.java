package fr.l3.miashs;

import java.io.FileReader;

import fr.l3.miashs.generation.GenerateurProgramme;
import generated.fr.l3.miashs.ParserCup;
import generated.fr.l3.miashs.Yylex;
import fr.ul.miashs.compil.arbre.Noeud;
import fr.ul.miashs.compil.arbre.TxtAfficheur;
import fr.l3.miashs.tds.*;


public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage : java fr.l3.miashs.Main <fichier_source>");
            System.exit(1);
        }

        try {
            Yylex scanner = new Yylex(new FileReader(args[0]));
            ParserCup parser = new ParserCup(scanner);

            Object resultat = parser.parse().value;

            System.out.println("OK !");
            System.out.println(args[0] + " analysé avec succès.\n");

            Noeud arbre = null;

            System.out.println("========== ARBRE ABSTRAIT ==========");
            if (resultat instanceof Noeud) {
                arbre = (Noeud) resultat;
                TxtAfficheur.afficher(arbre);
            } else {
                System.out.println("Aucun arbre abstrait produit.");
            }

            Tds tds = parser.getTds();

            System.out.println("========== TABLE DES SYMBOLES ==========");
            System.out.println(tds);

            if (arbre != null) {
                GenerateurProgramme genProg = new GenerateurProgramme();
                String code = genProg.generer(arbre, tds);

                System.out.println("========== CODE GENERE ==========");
                System.out.println(code);
            } else {
                System.out.println("========== CODE GENERE ==========");
                System.out.println("Impossible de générer le code : arbre abstrait absent.");
            }


        } catch (Exception e) {
            System.err.println("Erreur pendant l'analyse du fichier : " + args[0]);
            e.printStackTrace();
        }

        System.out.println("Terminé !");
    }
}