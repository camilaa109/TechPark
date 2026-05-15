package co.edu.uniquindio.techpark.model;

public class Operador extends Empleado {

    private String nombreZonaAsignada;
    private String nombreAtraccionAsignada;

    public Operador(String nombre, String documento, int edad, String contrasenia) {
        super(nombre, documento, edad, contrasenia);
    }

    //getters y setters
    public String getNombreZonaAsignada() {
        return nombreZonaAsignada;
    }

    public void setNombreZonaAsignada(String nombreZonaAsignada) {
        this.nombreZonaAsignada = nombreZonaAsignada;
    }

    public String getNombreAtraccionAsignada() {
        return nombreAtraccionAsignada;
    }

    public void setNombreAtraccionAsignada(String nombreAtraccionAsignada) {
        this.nombreAtraccionAsignada = nombreAtraccionAsignada;
    }
    
}
