package co.edu.uniquindio.techpark.model;

import co.edu.uniquindio.techpark.util.ServicioEscritura;
import co.edu.uniquindio.techpark.util.ServicioLectura;

public class Administrador extends Empleado {

    private Parque parque;

    public Administrador(String nombre, String documento, int edad, String contrasenia) {
        super(nombre, documento, edad, contrasenia);
    }

    public Parque crearParque(String nombre, int capacidad) {
        this.parque = new Parque(nombre, capacidad);
        return this.parque;
    }

    public Parque cargarParque(String nombre, int capacidad) {
        this.parque = new Parque(nombre, capacidad);
        cargarJSON();
        return this.parque;
    }

    public void guardarParque (){
        guardarJSON();
    }

    //Persistenica

    //Persitencia
    private void cargarJSON(){
        this.parque.setListaVisitantes(ServicioLectura.leerDatosVisitantes());
        this.parque.getListaEmpleados().addAll(ServicioLectura.leerDatosOperadores());
        this.parque.setListaZonas(ServicioLectura.leerDatosZonas());
        this.parque.setAtracciones(ServicioLectura.leerDatosAtracciones());
    }

    private void guardarJSON(){
        ServicioEscritura.guardarVisitantes(this.parque.getListaVisitantes());
        ServicioEscritura.guardarOperadores(this.parque.obtenerListaOperadores());
        ServicioEscritura.guardarZonas(this.parque.getListaZonas());
    }

}
