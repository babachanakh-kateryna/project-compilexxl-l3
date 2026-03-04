package fr.l3.miashs;
import java.io.FileReader;
import generated.fr.l3.miashs.ParserCup;
import generated.fr.l3.miashs.Yylex;
public class Main {


    public static void main(String[] args) throws Exception {


       Yylex scanner = new Yylex(new FileReader(args[0]));

        ParserCup parser = new ParserCup(scanner);

        parser.parse();

        System.out.println("OK !");
    }
}