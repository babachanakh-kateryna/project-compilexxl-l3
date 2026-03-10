package fr.l3.miashs;

import java.io.FileReader;
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

            if (resultat instanceof Noeud arbre) {
                System.out.println("===== ARBRE ABSTRAIT =====");
                TxtAfficheur.afficher(arbre);
            } else {
                System.out.println("Aucun arbre abstrait produit.");
            }


            Tds tds = parser.getTds();

            System.out.println("\n===== TABLE DES SYMBOLES =====");
            System.out.println(tds);


        } catch (Exception e) {
            System.err.println("Erreur pendant l'analyse du fichier : " + args[0]);
            e.printStackTrace();
        }

        System.out.println("Terminé !");
    }
}