package co.edu.uniquindio.techpark.model;

public abstract class Persona {

    protected String id;
    protected String nombre;
    protected String documento;
    protected int edad;
    private int contador = 0;

    public Persona(String nombre, String documento, int edad) {
        this.id = nombre + contador++;
        this.nombre = nombre;
        this.documento = documento;
        this.edad = edad;
    }

    //getters y setters
    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
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
