package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class AsmCodeGenerator implements FileGenerator {

    private static final Stack<String> pila = new Stack<>();
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("TODO");
    }

    public void generar_assembler(ArrayList<String> polaca)
    {
        
    }
}
