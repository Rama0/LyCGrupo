package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class IntermediateCodeGenerator implements FileGenerator {

    private static final ArrayList<String> polaca = new ArrayList<>();
    private static final Stack<String> pila = new Stack<>();

    private static final Stack<Integer> pilaInt = new Stack<>();
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        for (int i = 0; i < polaca.size(); i ++)
        {
            fileWriter.write(i + "- " + polaca.get(i) + "\n");
        }
    }
    public ArrayList<String> getPolaca (){
        return polaca;
    }

    public Stack<String> getPila (){
        return pila;
    }

    public Stack<Integer> getPilaInt (){
        return pilaInt;
    }
}
