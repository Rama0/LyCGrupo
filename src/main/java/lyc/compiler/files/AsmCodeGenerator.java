package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AsmCodeGenerator implements FileGenerator {

    private static final Stack<String> pila = new Stack<>();
    private static final ArrayList<String> listaCoodigoAssembler = new ArrayList<>();
    private static final ArrayList<String> listaVariablesAuxiliares = new ArrayList<>();

    private static final HashMap<String,String> mapaSaltos = new HashMap<String,String>();

    private ArrayList<String> polacaInversa;
    private static int indice =1;
    private static int indiceEtiquetas =1;

    private static int i =0;
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write(".MODEL LARGE"+" \n");
        fileWriter.write(".386"+" \n");
        fileWriter.write(".STACK 200h"+" \n");
        fileWriter.write(".DATA"+" \n");
        //-----------------------------------------//
        escribirData(fileWriter);
        //----------------------------------------//
        fileWriter.write(".CODE"+" \n");
        fileWriter.write("MOV ax, @DATA"+" \n");
        fileWriter.write("MOV ds,ax"+" \n");
        fileWriter.write("MOV es,ax "+" \n");

        for(int i=0; i<listaCoodigoAssembler.size(); i++)
        {
            fileWriter.write(listaCoodigoAssembler.get(i)+" \n");
        }
        fileWriter.write("FFREE "+" \n");
        fileWriter.write("MOV ax,4C00h "+" \n");
        fileWriter.write("int 21h "+" \n");
        fileWriter.write("END "+" \n");
        //fileWriter.write("TODO");
    }

    public void generar_assembler(ArrayList<String> polaca)
    {
        polacaInversa = polaca;
        for(i=0; i< polaca.size(); i++)
        {
            validarEscrituradeEtiqueta(i+"");
            if( isOperador(polaca.get(i)) || isSalto(polaca.get(i)) || polaca.get(i).equals("CMP") || polaca.get(i).startsWith("ET") )
            {
                traducirOperacionAassembler(polaca.get(i));
            }
            else
            {
                pila.push(polaca.get(i));
            }
        }
        if(mapaSaltos.containsKey(polaca.size()+""))//si mi programa es solo un if tiene que escribirse la etiqueta de fin de programa
        {
            listaCoodigoAssembler.add(mapaSaltos.get(polaca.size()+""));
            mapaSaltos.remove(polaca.size()+"");
        }
    }

    private boolean isOperador(String value)
    {
        if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") || value.equals("WRITE") || value.equals("READ") || value.equals(":=") )
        {
            return true;
        }
        return false;
    }

    private void traducirOperacionAassembler(String operador)
    {
        String operando1;
        String operando2;
        String nombreOperacion = getNombreOperacion(operador);
        if(operador.equals("CMP") || isSalto(operador) || operador.startsWith("ET"))
        {
            if(operador.startsWith("ET"))
            {
                listaCoodigoAssembler.add("ETIQUETA"+indiceEtiquetas+":");
                mapaSaltos.put(i+"", "ETIQUETA"+indiceEtiquetas);
                indiceEtiquetas++;
            }
            else if(operador.equals("CMP"))
            {
                operando2 = pila.pop();
                operando1 = pila.pop();
                listaCoodigoAssembler.add("FLD "+operando1);
                listaCoodigoAssembler.add("FCOMP "+operando2);
                listaCoodigoAssembler.add("FSTSW ax");
                listaCoodigoAssembler.add("SAHF");
            }
            else
            {
                String jump = traduzcoSaltoToAssembler(operador);
                if(Integer.parseInt(polacaInversa.get(i+1)) > i)
                {
                    String etiquetaName;
                    if(mapaSaltos.containsKey(polacaInversa.get(i+1)))
                    {
                        etiquetaName = mapaSaltos.get(polacaInversa.get(i+1));
                    }
                    else
                    {
                        etiquetaName = "ETIQUETA"+indiceEtiquetas;
                        mapaSaltos.put(polacaInversa.get(i+1), etiquetaName);
                        indiceEtiquetas++;
                    }
                    listaCoodigoAssembler.add(jump+" "+etiquetaName);
                }
                else // si el salto es hacia atras significa que ya escribi la etiqueta
                {
                    String etiqueta = mapaSaltos.get(polacaInversa.get(i+1));
                    mapaSaltos.remove(polacaInversa.get(i+1));
                    listaCoodigoAssembler.add(jump+" "+etiqueta);
                }
                i++; //la iteracion siguiente se ignora la celda que contiene el numero al saltar porque ya la escribi

            }
        }
        else if(isOperadorBinario(operador))
        {
            operando2 = pila.pop();
            operando1 = pila.pop();
            listaCoodigoAssembler.add("FLD "+operando1);
            listaCoodigoAssembler.add(nombreOperacion+" "+operando2);
            if(!nombreOperacion.equals("FSTP"))
            {
                listaCoodigoAssembler.add("FSTP "+"@auxiliar"+indice);
                listaVariablesAuxiliares.add("@auxiliar"+indice);
                pila.push("@auxiliar"+indice);
                indice++;
            }
        }
        else //aca van el read y el write
        {
            pila.pop();
        }
    }

    private String getNombreOperacion(String operador)
    {
        if(operador.equals("+"))
        {
            return "FSUM";
        }
        else if(operador.equals("-"))
        {
            return "FSUB";
        }
        else if(operador.equals("*"))
        {
            return "FMUL";
        }
        else if(operador.equals("/"))
        {
            return "FDIV";
        }
        else
        {
            return "FSTP";
        }
    }

    private boolean isOperadorBinario(String operador)
    {
        if(operador.equals("WRITE") || operador.equals("READ"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isSalto(String operador)
    {
        if(operador.equals("BLT") || operador.equals("BLE") || operador.equals("BGT") || operador.equals("BGE")|| operador.equals("BEQ")|| operador.equals("BNE")|| operador.equals("BI"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private String traduzcoSaltoToAssembler(String salto)
    {
        if(salto.equals("BLT"))
        {
            return "JB";
        }
        else if(salto.equals("BLE"))
        {
            return "JBE";
        }
        else if(salto.equals("BGT"))
        {
            return "JA";
        }
        else if(salto.equals("BGE"))
        {
            return "JAE";
        }
        else if(salto.equals("BEQ"))
        {
            return "JE";
        }
        else if(salto.equals("BNE"))
        {
            return "JNE";
        }
        return "JMP";
    }

    private void validarEscrituradeEtiqueta(String i)
    {
        if(mapaSaltos.containsKey(i))
        {
            listaCoodigoAssembler.add(mapaSaltos.get(i)+":");
            mapaSaltos.remove(i);
        }
    }

    private void escribirData(FileWriter fileWriter) throws IOException {
        SymbolTableGenerator stg = new SymbolTableGenerator();
        for(SymbolTable elem : stg.getMapTS().values())
        {
            fileWriter.write(elem.getName()+" dd "+elem.getValue()+" \n");
        }
        for(int j=0; j<listaVariablesAuxiliares.size() ;j++)
        {
            fileWriter.write(listaVariablesAuxiliares.get(j)+" dd "+"?"+" \n");
        }
    }
}
