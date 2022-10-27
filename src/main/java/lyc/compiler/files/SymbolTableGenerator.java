package lyc.compiler.files;

import java_cup.runtime.Symbol;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SymbolTableGenerator implements FileGenerator{

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("TODO");
    }
    private ArrayList<Symbol> symbols;
    public SymbolTableGenerator() {
        symbols = new ArrayList<Symbol>();
    }

    public void add(Symbol s) {
        symbols.add(s);
    }

    public void printSymbols() {
        for(Symbol sym : symbols) {
            if(sym.sym == 14) {
                System.out.println("Tipo de dato: IntegerConstant, Nombre: " + sym.value);
            }
            if(sym.sym == 15) {
                System.out.println("Tipo de dato: FloatConstant, Nombre: " + sym.value);
            }
            if(sym.sym == 16) {
                System.out.println("Tipo de dato: StringConstant, Nombre: " + sym.value);
            }
            if(sym.sym == 17) {
                System.out.println("Tipo de dato: Identifier, Nombre: " + sym.value);
            }
        }
    }
}
