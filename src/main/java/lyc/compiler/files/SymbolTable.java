package lyc.compiler.files;

public class SymbolTable {
    private String name;
    private String dataType;
    private String value;
    private int length;

    public SymbolTable(String name){ this.name=name;}

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
