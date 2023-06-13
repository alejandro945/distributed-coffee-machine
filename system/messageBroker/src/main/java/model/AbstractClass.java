package model;

import java.io.Serializable;

public abstract class AbstractClass implements Serializable {
    private int code;
    private int type;

    // For Alarms
    public AbstractClass(int code, int type) {
        this.code = code;
        this.type = type;
    }

    // For Ventas
    public AbstractClass(String id, int valor) {
        this.code = Integer.parseInt(id);
        this.type = valor;
    }

    public int getCode(){
        return code;
    }

    public int getType(){
        return type;
    }
}
