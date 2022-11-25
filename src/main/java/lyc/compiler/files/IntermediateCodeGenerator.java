package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class IntermediateCodeGenerator implements FileGenerator {

    private static final ArrayList<String> polaca = new ArrayList<>();
    private static final Stack<String> pila = new Stack<>();

    private static final Stack<Integer> pilaInt = new Stack<>();

    private static Integer indx = 0;

    private static Integer index = 0;

    private static final Queue<String> cola = new LinkedList<String>();

    private static final Stack<Integer> pilaFlag = new Stack<>();

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



    public String opxch(String value){
        String ret;
        if(value.equals("BLE")){
            ret = "BGT";
        }
        else if(value.equals("BLT")){
            ret="BGE";
        }
        else if(value.equals("BGE")){
            ret= "BLT";
        }
        else if(value.equals("BGT")){
            ret = "BLE";
        }
        else
        {
            ret = "BEQ";
        }
        return ret;
    }


    public Integer getIndx(){
        indx ++;
        return indx;
    }

    public Integer getIndex(){
        index ++;
        return index;
    }

    public void cleanIndx(){
        indx =0;
    }



    public Queue<String> getCola (){
        return cola;
    }

    public Stack<Integer> getPilaFlag (){
        return pilaFlag;
    }


    public void actualizarCeldas(int typeCondition)
    {
        int w;
        int x;
        int y;
        int z;
        int size;
        polaca.add("BI");
        if(typeCondition == 0)
        {
            w = pilaInt.pop();
            x = pilaInt.pop();
            y = pilaInt.pop();
            z = pilaInt.pop();

            polaca.add(z+"");
            size = polaca.size();
            polaca.set(w,size+"");
            polaca.set(x,(w+1)+"");
            polaca.set(y,(x+1)+"");
        }
        else if(typeCondition == 1)
        {
            x = pilaInt.pop();
            y = pilaInt.pop();
            z = pilaInt.pop();
            polaca.add(z+"");
            size = polaca.size();
            polaca.set(x,size+"");
            polaca.set(y,size+"");
        }
        else
        {
            y = pilaInt.pop();
            z = pilaInt.pop();
            polaca.add(z+"");
            size = polaca.size();
            polaca.set(y,size+"");
        }
    }

    public void actualizarCeldasDelIf(int typeCondition,int haveELse)
    {
        int x;
        int y;
        int z;
        int size;
        if(haveELse==0)
        {
            if(typeCondition == 0)
            {
                x = pilaInt.pop();
                y = pilaInt.pop();
                z = pilaInt.pop();
                size = polaca.size();
                polaca.set(z,(z+3)+"");
                polaca.set(x,size+"");
                polaca.set(y,(x+1)+"");

            }
            else if(typeCondition == 1)
            {
                y = pilaInt.pop();
                z = pilaInt.pop();
                polaca.set(z,polaca.size()+"");
                polaca.set(y,polaca.size()+"");
            }
            else
            {
                z = pilaInt.pop();
                polaca.set(z,polaca.size()+"");
            }
        }
        else
        {
            if(typeCondition == 0)
            {
                int w = pilaInt.pop();
                x = pilaInt.pop();
                y = pilaInt.pop();
                z = pilaInt.pop();
                size = polaca.size();
                polaca.set(w,size+"");
                polaca.set(x,(w+1)+"");
                polaca.set(z,(y+1)+"");
                polaca.set(y,(x+1)+"");

            }
            else if(typeCondition == 1)
            {
                x = pilaInt.pop();
                polaca.set(x, polaca.size()+"");
                y = pilaInt.pop();
                z = pilaInt.pop();
                polaca.set(z,(x+1)+"");
                polaca.set(y,(x+1)+"");
            }
            else
            {
                y = pilaInt.pop();
                z = pilaInt.pop();
                polaca.set(z,(y+1)+"");
                polaca.set(y, polaca.size()+"");
            }
        }
    }
}