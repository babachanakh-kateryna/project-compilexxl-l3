package generated.fr.l3.miashs;
import java_cup.runtime.Symbol;

%%

%public
%cupsym Sym
%cup

SEP = [ \t\r\n]
NUM = [0-9]+

%%

"+" { return new Symbol(Sym.ADD); }
"*" { return new Symbol(Sym.MUL); }
"(" { return new Symbol(Sym.PO); }
")" { return new Symbol(Sym.PF); }

{NUM} { return new Symbol(Sym.NUM); }

{SEP} {;}

<<EOF>> { return new Symbol(Sym.EOF); }