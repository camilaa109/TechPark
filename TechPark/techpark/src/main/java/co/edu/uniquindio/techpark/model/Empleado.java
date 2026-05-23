package co.edu.uniquindio.techpark.model;

public abstract class Empleado extends Persona {

    private boolean activo;

    public Empleado(String nombre, String documento, int edad, String contrasenia) {
        super(nombre, documento, edad, contrasenia);
        this.activo = true;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
