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
    public boolean agregarVisitante (String nombre, String documento, int edad, String contrasenia, double estatura){
        Visitante visitante = new Visitante(nombre, documento, edad, contrasenia, estatura);
        listaVisitantes.add(visitante);
        return true;
    }
    
    public Visitante obtenerVisitante (String documento){
        for (Visitante v: listaVisitantes){
            if (documento.equals(v.getDocumento())){
                return v;
            }
        }
        return null;
    }

    public void actualizarVisitante (String nombre, String documento, int edad, double estatura){
        Visitante visitante = obtenerVisitante(documento);
        visitante.setNombre(nombre);
        visitante.setEdad(edad);
        visitante.setEstatura(estatura);
    }

    public void eliminarVisitante (String documento){
        Visitante v = obtenerVisitante(documento);
        listaVisitantes.remove(v);
    }

    public void comprarTicket (String documento, double descuento, TipoTicket tipoticket){
        Visitante v = obtenerVisitante(documento);
        Ticket ticket = new Ticket(descuento, tipoticket);
        v.restarSaldoVirtual(ticket.getPrecioTicket());
        v.agregarTicket(ticket);
    }

    public void agregarFavorito (String documento, String nombreAtraccion){
        Visitante v = obtenerVisitante(documento);
        v.agregarFavorito(nombreAtraccion);
    }
    

    //Funciones para atracciones y zonas
    public void agregarZona (String nombre){
        Zona zona = new Zona(nombre);
        listaZonas.add(zona);
    }

    public Zona obtenerZona (String nombre){
        for (Zona z : listaZonas){
            if (z.getNombreZona().equals(nombre)){
                return z;
            }
        }
        return null;
    }

    public Atraccion obtenerAtraccion (String nombreZona, String nombreAtraccion){
        Zona zona = obtenerZona(nombreZona);
        return zona.obtenerAtraccion(nombreAtraccion);
    }

    public void agregarAtraccion (String nombreAtraccion, int capacidadMaxima, double alturaMinima, double costoAdicional,
         int tiempoEspera, TipoAtraccion tipoAtraccion, String nombreZona){
        Zona zona = obtenerZona(nombreZona);
        Atraccion atraccion = new Atraccion(nombreAtraccion, capacidadMaxima, alturaMinima, capacidadMaxima, 
            costoAdicional, tiempoEspera, tipoAtraccion);
        zona.agregarAtraccion(atraccion);
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

    public List<Zona> getListaZonas() {
        return listaZonas;
    }

    public List<Visitante> getListaVisitantes() {
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


