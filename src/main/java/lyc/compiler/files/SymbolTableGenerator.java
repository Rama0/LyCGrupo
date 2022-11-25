package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SymbolTableGenerator implements FileGenerator{

    private static final ArrayList<SymbolTable> ts = new ArrayList<>();

    private static final HashMap<String,SymbolTable> mapTS = new HashMap<String,SymbolTable>();

    private static final Stack<String> pilaTipos = new Stack<>();

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("NOMBRE TIPODATO VALOR LONGITUD\n");
        for (SymbolTable value : mapTS.values()) {
            String print = value.getName()+" "+value.getDataType()+" "+value.getValue()+" "+value.getLength()+"\n";
            fileWriter.write(print);
        }
        //fileWriter.write("TODO");
    }
    public ArrayList<SymbolTable> getTs(){
        return ts;
    }

    public void asignarTipodeDato(String typeDato){
        for (SymbolTable elem: ts)
        {
            elem.setDataType(typeDato);
            if(typeDato.equals("Int") || typeDato.equals("Float") || typeDato.equals("String"))
            {
                elem.setValue("?");
                elem.setLength("?");
            }
            else
            {
                if(typeDato.equals("CTE_String"))
                {
                    String subString = elem.getName().substring(1,elem.getName().length()-1);
                    elem.setName("_"+subString);
                    elem.setValue(subString);
                    elem.setLength(subString.length()+"");
                }
                else
                {
                    elem.setValue(elem.getName());
                    elem.setName("_"+elem.getName());
                    elem.setLength("?");
                }
            }

            if(!mapTS.containsKey(elem.getName()))
            {
                mapTS.put(elem.getName(), elem);
            }
            else
            {
                if(typeDato.equals("Int") || typeDato.equals("Float") || typeDato.equals("String"))
                {
                    throw new  NumberFormatException("ID "+ "\""+elem.getName()+"\""+" declarado repetidamente");
                }
            }
        }
        ts.clear();
    }


    public void validarTipos(String li,String ld)
    {
        if(!li.equals(ld))
        {
            throw new IllegalArgumentException("Asignacion entre tipos No Compatibles");
        }
    }

    public void validarIdDeclarado(String id)
    {
        if(!mapTS.containsKey(id))
        {
            throw new IllegalArgumentException("ID " +"\""+id+"\"" +" No declarado");
        }
    }

    public Stack<String> getPilaTipos (){
        return pilaTipos;
    }

    public String getTipo(String key)
    {
        if( mapTS.get(key).getValue().equals("?"))
        {
            return mapTS.get(key).getDataType();
        }
        else
        {
            return mapTS.get(key).getDataType().split("_")[1];
        }

    }

    public void recargarTipo(String id)
    {
        if(getTipo(id).equals("Float"))
        {
            pilaTipos.pop();
            pilaTipos.push("Float");
        }
    }

    public void compararOperandos(String li, String ld)
    {
        if(!li.equals(ld))
        {
            throw new IllegalArgumentException("Comparacion entre tipos No Compatibles");
        }
    }
}