package generated.fr.l3.miashs;
import java_cup.runtime.Symbol;

%%

%public
%unicode
%line
%column
%cupsym Sym
%cup
%{
    private void erreur() {
        System.out.println(
            "Caractère inattendu : " + yytext() +
            " (ligne " + (yyline + 1) +
            ", colonne " + (yycolumn + 1) + ")"
        );
    }

    private Symbol symbole(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbole(int type, Object valeur) {
        return new Symbol(type, yyline, yycolumn, valeur);
    }
%}

/* macros */
SEP = [ \t\r\n\f]+
CHIFFRE = [0-9]
LETTRE = [a-zA-Z_]
IDF = {LETTRE}({LETTRE}|{CHIFFRE})*
NOMBRE = {CHIFFRE}+

COMMENTAIRE_LIGNE = "//".*
COMMENTAIRE_BLOC = "/*"([^*]|\*+[^/*])*\*+"/"

%%
/* mots-clefs */
"entier"      { return symbole(Sym.ENTIER); }
"vide"        { return symbole(Sym.VIDE); }
"si"          { return symbole(Sym.SI); }
"sinon"       { return symbole(Sym.SINON); }
"tantque"     { return symbole(Sym.TANTQUE); }
"retourner"   { return symbole(Sym.RETOURNER); }
"ecrire"      { return symbole(Sym.ECRIRE); }
"lire"        { return symbole(Sym.LIRE); }

/* opérateurs de comparaison */
">="          { return symbole(Sym.SUPE); }
"<="          { return symbole(Sym.INFE); }
"=="          { return symbole(Sym.EG); }
"!="          { return symbole(Sym.DIF); }
">"           { return symbole(Sym.SUP); }
"<"           { return symbole(Sym.INF); }

/* opérateurs arithmétiques / affectation */
"+"           { return symbole(Sym.PLUS); }
"-"           { return symbole(Sym.MOINS); }
"*"           { return symbole(Sym.MUL); }
"/"           { return symbole(Sym.DIV); }
"="           { return symbole(Sym.AFF); }

/* délimiteurs */
"("           { return symbole(Sym.PO); }
")"           { return symbole(Sym.PF); }
"{"           { return symbole(Sym.AO); }
"}"           { return symbole(Sym.AF); }
";"           { return symbole(Sym.PV); }
","           { return symbole(Sym.VIRG); }

/* constantes et identificateurs */
{NOMBRE}      { return symbole(Sym.NOMBRE, Integer.valueOf(yytext())); }
{IDF}         { return symbole(Sym.IDF, yytext()); }

/* pseudo-terminaux : séparateurs et commentaires */
{SEP}                 { ; }
{COMMENTAIRE_LIGNE}   { ; }
{COMMENTAIRE_BLOC}    { ; }

/* fin de fichier */
<<EOF>>       { return new Symbol(Sym.EOF); }

/* erreur lexicale */
[^]           { erreur(); return new Symbol(Sym.error); }