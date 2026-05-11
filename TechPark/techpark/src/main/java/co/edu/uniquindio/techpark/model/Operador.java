package co.edu.uniquindio.techpark.model;

public class Operador extends Empleado {

    private String idZonaAsignada;
    private String idAtraccionAsignada;

    public Operador(String nombre, String documento, int edad, String contrasenia) {
        super(nombre, documento, edad, contrasenia);
    }

    //getters y setters
    public String getIdZonaAsignada() {
        return idZonaAsignada;
    }

    public void setIdZonaAsignada(String idZonaAsignada) {
        this.idZonaAsignada = idZonaAsignada;
    }

    public String getIdAtraccionAsignada() {
        return idAtraccionAsignada;
    }

    public void setIdAtraccionAsignada(String idAtraccionAsignada) {
        this.idAtraccionAsignada = idAtraccionAsignada;
    }
    
}
