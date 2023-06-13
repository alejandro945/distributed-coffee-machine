package repository;

import java.io.*;
import java.util.*;

import model.AbstractClass;

public abstract class IRepositorio<T extends AbstractClass> implements Serializable {

    private List<T> list;
    private String name;

    public IRepositorio(String name) {
        this.name = name;
        this.list = new ArrayList<>();
        loadData();
    }

    public void add(T element) {
        list.add(element);
        this.saveData();
    }

    public void remove(T element) {
        list.remove(element);
        saveData();
    }

    public void setElements(List<T> elements) {
        this.list = elements;
    }

    public List<T> getElements() {
        return list;
    }

    public T getElement(int codMaquina, int externalType) {
        for (T element : list) {
            if (element.getCode() == codMaquina && element.getType() == externalType) {
                return element;
            }
        }
        return null;
    }

    public void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);
            oos.close();
        } catch (FileNotFoundException ffe) {
            System.out.println("error al guardar FileNotFound");
            ffe.printStackTrace();
        } catch (Exception e) {
            System.out.println("error al guardar");

            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        FileInputStream fis;
        try {
            File f = new File(name);
            if (!f.exists()) {
                System.out.println("archivo no encontrado");
                loadDataP();
                return;
            }
            fis = new FileInputStream(f);
            ObjectInputStream oos = new ObjectInputStream(fis);
            Object ois = oos.readObject();
            IRepositorio<T> md = (IRepositorio<T>) ois;
            this.setElements(md.getElements());
            oos.close();
        } catch (Exception e) {
            System.err.println("Error al cargar AlarmaRepositorio");
            e.printStackTrace();
        }
    }

    public abstract void loadDataP();

}
