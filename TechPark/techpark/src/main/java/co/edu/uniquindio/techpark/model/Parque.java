package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Parque{

    private String nombre;
    private int capacidadMaxima;
    private List<String> listaIdZonas; 
    private List<String> listaIdVisitantes; 
    private List<String> listaIdEmpleados;

    //Constructor
    public Parque(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.listaIdZonas = new ArrayList<String>();
        this.listaIdVisitantes = new ArrayList<String>();
        this.listaIdEmpleados = new ArrayList<String>();
    }

    public void agregarZona (String idZona){
        listaIdZonas.add(idZona);
    }

    public void agregarVisitante (String idVisitante){
        listaIdVisitantes.add(idVisitante);
    }

    public void agregarEmpleado (String idEmpleado){
        listaIdEmpleados.add(idEmpleado);
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<String> getListaIdZonas() {
        return listaIdZonas;
    }

    public List<String> getListaIdVisitantes() {
        return listaIdVisitantes;
    }

    public List<String> getListaIdEmpleados() {
        return listaIdEmpleados;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setListaIdZonas(List<String> listaIdZonas) {
        this.listaIdZonas = listaIdZonas;
    }

    public void setListaIdVisitantes(List<String> listaIdVisitantes) {
        this.listaIdVisitantes = listaIdVisitantes;
    }

    public void setListaIdEmpleados(List<String> listaIdEmpleados) {
        this.listaIdEmpleados = listaIdEmpleados;
    } 
    

}


