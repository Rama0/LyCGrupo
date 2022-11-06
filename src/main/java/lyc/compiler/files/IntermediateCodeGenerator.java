package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class IntermediateCodeGenerator implements FileGenerator {

    private static final ArrayList<String> polaca = new ArrayList<>();
    private static final Stack<String> pila = new Stack<>();
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        for (String e: polaca)
        {
            fileWriter.write(e+" ");
        }
        //fileWriter.write("TODO");
    }

    public ArrayList<String> getPolaca (){
        return polaca;
    }
}
