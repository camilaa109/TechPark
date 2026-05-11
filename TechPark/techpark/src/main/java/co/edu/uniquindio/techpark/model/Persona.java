package co.edu.uniquindio.techpark.model;

public abstract class Persona {

    protected String nombre;
    protected String documento;
    protected int edad;
    protected String contrasenia;

    public Persona(String nombre, String documento, int edad, String contrasenia) {
        this.nombre = nombre;
        this.documento = documento;
        this.edad = edad;
        this.contrasenia = contrasenia;
    }

    //getters y setters
    public String getNombre() {
        return nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    } 
    
}
