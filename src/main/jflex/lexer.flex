package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.model.*;
import static lyc.compiler.constants.Constants.*;
import lyc.compiler.files.SymbolTableGenerator;

%%

%public
%class Lexer
%unicode
%cup
%line
%column
%throws CompilerException
%eofval{
  return symbol(ParserSym.EOF);
%eofval}


%{
  SymbolTableGenerator symbolTable = new SymbolTableGenerator();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Identation =  [ \t\f]

Plus = "+"
Mult = "*"
Sub = "-"
Div = "/"
Assig = "="
OpenBracket = "("
CloseBracket = ")"
Letter = [a-zA-Z]
Digit = [0-9]

And = "&"
Or = "||"
Not = "not"
GreaterThan = ">"
GreaterAndEqual = ">="
LowerThan = "<"
LowerAndEqual = "<="
Equal = "=="

If = "if"
Else = "else"
While = "while"

WhiteSpace = {LineTerminator} | {Identation}
Identifier = {Letter} ({Letter}|{Digit})*
IntegerConstant = {Digit}+
FloatConstant = {Digit}*"."{Digit}+|{Digit}+"."{Digit}*
StringConstant = \".+\"

CaracterCom	= @|\?|\"|\.|\,|\+|\t|\n|\/|\_|\:|\;|¿|\*|{Letter}|{Digit}|{WhiteSpace}
Comentario = "/"(\*){CaracterCom}*(\*)"/"

LlaveOpen = "{"
LlaveClose = "}"
Init = "init"

Float = "Float"
Integer = "Int"
String = "String"

DosPuntos = ":"
Coma = ","
PyC = ";"

Read = "read"
Write = "write"
Iguales = "iguales"

CorcheteOpen = "["
CorcheteClose = "]"
Do = "do"
Case = "case"
Default = "default"
Enddo = "enddo"
%%


/* keywords */

<YYINITIAL> {
   /* Caracteres especiales */
   {LlaveOpen}                             { return symbol(ParserSym.LLAVE_OPEN); }
   {LlaveClose}                            { return symbol(ParserSym.LLAVE_CLOSE); }
   {DosPuntos}                             { return symbol(ParserSym.DOS_PUNTOS); }
   {Coma}                                  { return symbol(ParserSym.COMA); }
   {PyC}                                   { return symbol(ParserSym.PYC); }
   {OpenBracket}                           { return symbol(ParserSym.OPEN_BRACKET); }
   {CloseBracket}                          { return symbol(ParserSym.CLOSE_BRACKET); }
   {CorcheteOpen}                          { return symbol(ParserSym.CORCHETE_OPEN); }
   {CorcheteClose}                          { return symbol(ParserSym.CORCHETE_CLOSE); }

   /* Palabras Reservadas */
   {Init}                                  {return symbol(ParserSym.INIT);}
   {Float}                                 {return symbol(ParserSym.FLOAT);}
   {Integer}                               {return symbol(ParserSym.INTEGER);}
   {String}                                {return symbol(ParserSym.STRING);}
   {If}                                    {return symbol(ParserSym.IF);}
   {Else}                                  {return symbol(ParserSym.ELSE);}
   {While}                                 {return symbol(ParserSym.WHILE);}
   {Do}                                    {return symbol(ParserSym.DO);}
   {Case}                                  {return symbol(ParserSym.CASE);}
   {Default}                               {return symbol(ParserSym.DEFAULT);}
   {Enddo}                                 {return symbol(ParserSym.ENDDO);}

    /* operators */
     {Plus}                                    { return symbol(ParserSym.PLUS); }
     {Sub}                                     { return symbol(ParserSym.SUB); }
     {Mult}                                    { return symbol(ParserSym.MULT); }
     {Div}                                     { return symbol(ParserSym.DIV); }
     {Assig}                                   { return symbol(ParserSym.ASSIG); }
     {And}                                     { return symbol(ParserSym.AND); }
     {Or}                                      { return symbol(ParserSym.OR); }
     {Not}                                     { return symbol(ParserSym.NOT); }
     {GreaterThan}                             { return symbol(ParserSym.GREATER_THAN); }
     {GreaterAndEqual}                         { return symbol(ParserSym.GREATER_AND_EQUAL); }
     {LowerThan}                               { return symbol(ParserSym.LOWER_THAN); }
     {LowerAndEqual}                           { return symbol(ParserSym.LOWER_AND_EQUAL); }
     {Equal}                                   { return symbol(ParserSym.EQUAL); }

   /* funciones */
   {Read}                                { return symbol(ParserSym.READ); }
   {Write}                               { return symbol(ParserSym.WRITE); }
   {Iguales}                             { return symbol(ParserSym.IGUALES); }

  /* identifiers */
  {Identifier}                             { symbolTable.add(symbol(ParserSym.IDENTIFIER, yytext())); return symbol(ParserSym.IDENTIFIER, yytext()); }

  /* Constants */
  {IntegerConstant}                        { if(yytext().length()>5 || Integer.parseInt(yytext())>32767){System.out.println("Constante Integer Fuera de Rango");throw new InvalidIntegerException(yytext());}else{symbolTable.add(symbol(ParserSym.INTEGER_CONSTANT, yytext()));return symbol(ParserSym.INTEGER_CONSTANT, yytext()); }}
  {FloatConstant}                          { if(Float.parseFloat(yytext())>3.40282347e+38F){System.out.println("Constante Float Fuera de Rango");throw new NumberFormatException(yytext());}else{symbolTable.add(symbol(ParserSym.FLOAT_CONSTANT, yytext()));return symbol(ParserSym.FLOAT_CONSTANT, yytext()); }}
  {StringConstant}                         { if((yytext().length()-2)>40){System.out.println("Constante String No Puede Superar los 40 Caracteres");throw new InvalidLengthException(yytext());}else{symbolTable.add(symbol(ParserSym.STRING_CONSTANT, yytext()));return symbol(ParserSym.STRING_CONSTANT, yytext()); }}


  /* whitespace */
  {WhiteSpace}                              { /* ignore */ }

   /* Comments */
   {Comentario}                            { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
