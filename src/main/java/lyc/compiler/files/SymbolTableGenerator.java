package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SymbolTableGenerator implements FileGenerator{


    private static final ArrayList<String> listLexer = new ArrayList<>();
    private static final ArrayList<SymbolTable> ts = new ArrayList<>();
    private static final ArrayList<SymbolTable> lsInteger = new ArrayList<>();
    private static final ArrayList<SymbolTable> lsFloat = new ArrayList<>();
    private static final ArrayList<SymbolTable> lsString = new ArrayList<>();
    private static final ArrayList<SymbolTable> lsCte = new ArrayList<>();
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("NOMBRE TIPODATO VALOR LONGITUD\n");
        for(SymbolTable e : lsInteger)
        {
            fileWriter.write(e.getName() + " "+ e.getDataType()+"   \n");
        }
        for(SymbolTable e : lsFloat )
        {
            fileWriter.write(e.getName() + " "+ e.getDataType()+"   \n");
        }
        for(SymbolTable e : lsString)
        {
            fileWriter.write(e.getName() + " "+ e.getDataType()+"  \n");
        }

        for(SymbolTable e : lsCte)
        {
            if(e.getName().startsWith("\""))
            {
                e.setValue(e.getName().substring(1,e.getName().length()-1));
                e.setName("_"+e.getValue());
                e.setLength(e.getValue().length());

            }
            else
            {
                e.setValue(e.getName());
                e.setLength(e.getName().length());
                e.setName("_"+e.getName());
            }

            fileWriter.write(e.getName()+"       "+e.getValue()+" "+e.getLength()+"\n");
        }
        //fileWriter.write("TODO");
    }

    public ArrayList<String> getListLexer(){
        return listLexer;
    }

    public ArrayList<SymbolTable> getLsCte(){
        return lsCte;
    }

    public ArrayList<SymbolTable> getTs(){
        return ts;
    }

    public  void hello(String name){
        System.out.println("Mi Identificador es "+name);
    }


    public void asignarTipodeDato(int typeDato){
        for (SymbolTable elem: ts) {
            if(typeDato == 0)
            {
                elem.setDataType("Int");
                lsInteger.add(elem);
            } else if (typeDato == 1 ) {

                elem.setDataType("Float");
                lsFloat.add(elem);
            }
            else {
                elem.setDataType("String");
                lsString.add(elem);
            }
        }
        ts.clear();
    }
}
