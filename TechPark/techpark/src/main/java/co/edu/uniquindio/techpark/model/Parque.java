package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Parque{

    private String nombre;
    private int capacidadMaxima;
    private List<Zona> listaZonas; 
    private List<Visitante> listaVisitantes; 
    private List<Empleado> listaEmpleados;

    //Constructor
    public Parque(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.listaZonas = new ArrayList<Zona>();
        this.listaVisitantes = new ArrayList<Visitante>();
        this.listaEmpleados = new ArrayList<Empleado>();
    }


    //funciones para visitantes
    public boolean agregarVisitante (Visitante visitante){
        listaVisitantes.add(visitante);
        return true;
    }

    public List<Visitante> obtenerListaVisitantes (){
        return listaVisitantes;
    } 
    
    public Visitante obtenerVisitante (String documento){
        for (Visitante v: listaVisitantes){
            if (documento.equals(v.getDocumento())){
                return v;
            }
        }
        return null;
    }

    public void agregarZona (Zona zona){
        listaZonas.add(zona);
    }

    public void agregarEmpleado (Empleado empleado){
        listaEmpleados.add(empleado);
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<Zona> getListaIdZonas() {
        return listaZonas;
    }

    public List<Visitante> getListaIdVisitantes() {
        return listaVisitantes;
    }

    public List<Empleado> getListaIdEmpleados() {
        return listaEmpleados;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

}


