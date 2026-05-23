package co.edu.uniquindio.techpark.model;

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
        this.parque.cargarDatos();
        return this.parque;
    }

    public void guardarParque (){
        this.parque.guardarDatos();
    }

}
